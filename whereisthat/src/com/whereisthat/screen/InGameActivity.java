package com.whereisthat.screen;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.Unit;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;
import com.whereisthat.City;
import com.whereisthat.Locations;
import com.whereisthat.LocationsParser;
import com.whereisthat.R;
import com.whereisthat.game.Game;
import com.whereisthat.game.Round;

public class InGameActivity extends Activity {

	private MapView map;
	private GraphicsLayer locationsLayer;
	
	private ProgressBar progressBar;

	private AQuery aq;

	private Locations locations = new Locations();

	private Point currentLocation;

	private ScheduledExecutorService scheduler;
	private long startTime;
	
	private Game game = new Game();

	private MediaPlayer clockTicking;
	private MediaPlayer backgroundSound;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingame);

		aq = new AQuery(this);
		
		//setCustomFontStyle();

		InitBackgroundSounds();

		readLocations();

		// Retrieve the map and initial extent from XML layout
		map = (MapView) findViewById(R.id.gameMap);

		ArcGISDynamicMapServiceLayer baseMap = new ArcGISDynamicMapServiceLayer(
				"http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
		map.addLayer(baseMap);

		progressBar = aq.id(R.id.progressBar).getProgressBar();
		progressBar.setMax(10000);
		progressBar.setProgress(0);	
		
		scheduler = Executors.newScheduledThreadPool(1);
		
		map.setOnStatusChangedListener(new OnStatusChangedListener() {

			private static final long serialVersionUID = 1L;

			public void onStatusChanged(Object source, STATUS status) {
				if (source == map && status == STATUS.INITIALIZED) {
					showLocationsInMap();

					setTargetLocation();

					startTimer();
				}
			}
		});

		map.setOnSingleTapListener(new OnSingleTapListener() {
			
			private static final long serialVersionUID = 1L;

			public void onSingleTap(float x, float y) {
				if (map.isLoaded()) {
					Point pointClicked = map.toMapPoint(new Point(x, y));
					double distance = GeometryEngine.distance(pointClicked,
							currentLocation, map.getSpatialReference());

					Unit mapUnit = map.getSpatialReference().getUnit();
					double distanceKm = Unit.convertUnits(distance,mapUnit,
														 Unit.create(LinearUnit.Code.KILOMETER));									
												
					long elapsedTime = getElapsetTime();
					game.addRound(new Round(distanceKm, elapsedTime));
					long gameScore = game.getScore();
					long roundScore = game.getLastRoundScore();
					
					Toast.makeText(getApplicationContext(), "Distance(Km): "+distanceKm + 
															"\nElapsed Time(s): "+(elapsedTime/1000)+
															"\nRound Score: "+roundScore,
							Toast.LENGTH_LONG).show();
					
					aq.id(R.id.points).text(gameScore + " pts");
					
					setTargetLocation();
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		scheduler.shutdown();
	}

	@Override
	protected void onPause() {
		super.onPause();
		map.pause();
		clockTicking.stop();
		backgroundSound.stop();
	}

	protected void readLocations() {
		InputStream is = getResources().openRawResource(R.raw.cities);

		List<City> cities = LocationsParser.parseCities(is);
		locations.setCities(cities);
	}

	protected void showLocationsInMap() {
		locationsLayer = new GraphicsLayer(map.getSpatialReference(),
				new Envelope(-19332033.11, -3516.27, -1720941.80, 11737211.28));

		for (City city : locations.getCities()) {
			Point point = new Point();
			point.setX(city.getLongitude());
			point.setY(city.getLatitude());
			Point pointReproj = (Point) GeometryEngine.project(point,
					SpatialReference.create(4326), map.getSpatialReference());

			city.setMapPoint(pointReproj);

			Graphic graphic = new Graphic(pointReproj, new SimpleMarkerSymbol(
					Color.RED, 8, STYLE.CIRCLE));

			locationsLayer.addGraphic(graphic);

		}

		map.addLayer(locationsLayer);
	}

	protected void startTimer() {
		scheduler.scheduleWithFixedDelay(new Runnable() {
			  public void run() {
			     try {			    	 
			    	 progressBar.setProgress((int)getElapsetTime());
			     } catch (Throwable t) {
			       // handle exceptions there
			     }
			  }
			}, 0, 200, TimeUnit.MILLISECONDS);
		
		startTime = new Date().getTime();
	}

	private void InitBackgroundSounds() {
		backgroundSound = MediaPlayer.create(this, R.raw.game_bg);
		backgroundSound.setVolume(0.3f, 0.3f);
		backgroundSound.setLooping(true);
		backgroundSound.start();

		clockTicking = MediaPlayer.create(this, R.raw.clock_ticking);
		clockTicking.setVolume(1.0f, 1.0f);
		clockTicking.setLooping(true);
		clockTicking.start();
	}
	
	public void setCustomFontStyle()
	{
		android.graphics.Typeface font = 
				android.graphics.Typeface.createFromAsset(getAssets(), "fonts/showers.ttf");		

		((TextView) findViewById(R.id.points)).setTypeface(font);
	}
	
	public long getElapsetTime(){
		return new Date().getTime() - startTime;
	}
	
	public void setTargetLocation(){
		List<City> cities = locations.getCities();
		
		Random random = new Random();
		int randomCity = random.nextInt(cities.size() - 1);
		City city = cities.get(randomCity);
		aq.id(R.id.locationLabel).text(
				city.getName() + "-" + city.getCountry());
		currentLocation = city.getMapPoint();
		
		progressBar.setProgress(0);
		startTime = new Date().getTime();
	}
}