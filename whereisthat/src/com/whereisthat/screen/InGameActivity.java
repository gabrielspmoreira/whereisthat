package com.whereisthat.screen;

import java.io.InputStream;
import java.util.List;

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
import com.whereisthat.R;
import com.whereisthat.data.City;
import com.whereisthat.data.Locations;
import com.whereisthat.data.LocationsParser;
import com.whereisthat.game.rules.GameScore;
import com.whereisthat.game.rules.Round;

public class InGameActivity extends Activity {

	private MapView map;
	private GraphicsLayer locationsLayer;
	
	private ProgressBar progressBar;

	private AQuery aq;

	private Locations locations = new Locations();

	private Point currentLocation;

	
	private GameScore gameScore = new GameScore();
	private GameTiming gameTiming = new GameTiming();
	

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
				
		
		map.setOnStatusChangedListener(new OnStatusChangedListener() {

			private static final long serialVersionUID = 1L;

			public void onStatusChanged(Object source, STATUS status) {
				if (source == map && status == STATUS.INITIALIZED) {
					showLocationsInMap();

					setTargetLocation();

					startRoundTimer();
				}
			}
		});

		map.setOnSingleTapListener(new OnSingleTapListener() {
			
			private static final long serialVersionUID = 1L;

			public void onSingleTap(float x, float y) {
				if (map.isLoaded()) {
					double distanceKm = getKmDistanceFromTarget(x,y);									
												
					long elapsedTime = gameTiming.getElapsetTime();
					gameScore.addRound(new Round(distanceKm, elapsedTime));
					
					long score = gameScore.getScore();
					long roundScore = gameScore.getLastRoundScore();
					
					Toast.makeText(getApplicationContext(), "Distance(Km): "+distanceKm + 
															"\nElapsed Time(s): "+(elapsedTime/1000)+
															"\nRound Score: "+roundScore,
							Toast.LENGTH_LONG).show();
					
					aq.id(R.id.points).text(score + " pts");
					
					setTargetLocation();
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		gameTiming.stopRound();
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

	protected void startRoundTimer() {
		gameTiming.stopRound();
		gameTiming.startRound(new Runnable() {
			  public void run() {
				     try {			    	 
				    	 progressBar.setProgress((int) gameTiming.getElapsetTime());
				     } catch (Throwable t) {
				       // handle exceptions there
				     }
				  }
				});
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
	
	
	
	public void setTargetLocation(){				
		City city = locations.getRandomCity();
		aq.id(R.id.locationLabel).text(
				city.getName() + "-" + city.getCountry());
		currentLocation = city.getMapPoint();
		
		progressBar.setProgress(0);
		
		startRoundTimer();
	}
	
	private double getKmDistanceFromTarget(float x, float y){
		Point pointClicked = map.toMapPoint(new Point(x, y));
		double distance = GeometryEngine.distance(pointClicked,
				currentLocation, map.getSpatialReference());

		Unit mapUnit = map.getSpatialReference().getUnit();
		double distanceKm = Unit.convertUnits(distance,mapUnit,
											 Unit.create(LinearUnit.Code.KILOMETER));	
		
		return distanceKm;
	}
}