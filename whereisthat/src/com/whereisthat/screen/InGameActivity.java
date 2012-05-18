package com.whereisthat.screen;

import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;
import com.whereisthat.R;
import com.whereisthat.data.City;
import com.whereisthat.data.IScoreDialogListener;
import com.whereisthat.data.Location;
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

	private Location currentLocation;

	
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
		
		locationsLayer = new GraphicsLayer(map.getSpatialReference(),
				new Envelope(-19332033.11, -3516.27, -1720941.80, 11737211.28));		
		map.addLayer(locationsLayer);
		
		progressBar = aq.id(R.id.progressBar).getProgressBar();
		progressBar.setMax(10000);
		progressBar.setProgress(0);					
		
		map.setOnStatusChangedListener(new OnStatusChangedListener() {
			private static final long serialVersionUID = 1L;
			public void onStatusChanged(Object source, STATUS status) {
				if (source == map && status == STATUS.INITIALIZED) {
					gameStart();
				}
			}
		});

		map.setOnSingleTapListener(new OnSingleTapListener() {
			
			private static final long serialVersionUID = 1L;

			public void onSingleTap(float x, float y) {
				if (map.isLoaded()) {
					long elapsedTime = gameTiming.getElapsetTime();
					stopRoundTimer();

					Point pointClicked = map.toMapPoint(new Point(x, y));
					Point targetPoint = currentLocation.getMapPoint();
					showFlagPointInMap(pointClicked, R.drawable.flag_clicked);
					showFlagPointInMap(targetPoint, R.drawable.flag_target);
					
					double distanceKm = getKmDistanceFromTarget(pointClicked);			
					
					gameScore.addRound(new Round(distanceKm, elapsedTime));
					long score = gameScore.getScore();
					long roundScore = gameScore.getLastRoundScore();
					
					showRoundScore(distanceKm, elapsedTime, roundScore);
				
					updateScorePanel(score);										
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
	
	private void gameStart(){
		//showLocationsInMap();
		setTargetLocation();
		startRoundTimer();
	}

	private void readLocations() {
		InputStream is = getResources().openRawResource(R.raw.cities);
		List<City> cities = LocationsParser.parseCities(is);
		locations.setCities(cities);
	}	
	
	private void showRoundScore(double distanceKm, long elapsedTime, long roundScore){
		ScoreDialog dialog = new ScoreDialog(this, 
											 currentLocation.getCityName(), 
											 Math.round(elapsedTime/1000),
											 Math.round(distanceKm),
											 roundScore);
		
		dialog.addListener(new IScoreDialogListener() {			
			public void nextRound() {
				clearLocationsLayer();
				setTargetLocation();				
			}		
			public void stopGame() {
			
			}
		});		
		dialog.show();
	}

	
	private void showFlagPointInMap(Point point, int pictureId){
		Drawable drawable = getResources().getDrawable(pictureId);
		PictureMarkerSymbol markerSymbol = new PictureMarkerSymbol(drawable);
		markerSymbol.setOffsetX(10);
		markerSymbol.setOffsetY(13);
		
		Graphic graphic = new Graphic(point, markerSymbol);		
		locationsLayer.addGraphic(graphic);
	}
	
	private void clearLocationsLayer(){
		locationsLayer.removeAll();
	}	
	
	private void updateScorePanel(long totalScore){
		aq.id(R.id.points).text(totalScore + " pts");
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
	
	private void stopRoundTimer(){
		gameTiming.stopRound();
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
	
	private void setCustomFontStyle()
	{
		android.graphics.Typeface font = 
				android.graphics.Typeface.createFromAsset(getAssets(), "fonts/showers.ttf");
		((TextView) findViewById(R.id.points)).setTypeface(font);
	}	
	
	private void setTargetLocation(){				
		currentLocation = locations.getRandomCity(map);		
		aq.id(R.id.locationLabel).text(currentLocation.toString());		
		progressBar.setProgress(0);		
		startRoundTimer();
	}
	
	private double getKmDistanceFromTarget(Point pointClicked){	
		Point currentTarget = currentLocation.getMapPoint();
		double distance = GeometryEngine.distance(pointClicked,
				currentTarget, map.getSpatialReference());

		Unit mapUnit = map.getSpatialReference().getUnit();
		double distanceKm = Unit.convertUnits(distance,mapUnit,
											 Unit.create(LinearUnit.Code.KILOMETER));	
		
		return distanceKm;
	}
	
	private void showLocationsInMap() {
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
	}
}