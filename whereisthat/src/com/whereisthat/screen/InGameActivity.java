package com.whereisthat.screen;

import java.io.InputStream;
import java.util.List;
import java.util.Timer;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.widget.Button;
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
import com.whereisthat.TimingTask;

public class InGameActivity extends Activity {

	private MapView map;
	private GraphicsLayer locationsLayer;

	private AQuery aq;

	private Locations locations = new Locations();

	private Point currentLocation;

	private Timer timer = new Timer();
	private boolean timing = false;

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

		aq.id(R.id.progressBar).getProgressBar().setMax(100);
		aq.id(R.id.progressBar).getProgressBar().setProgress(0);
		
		map.setOnStatusChangedListener(new OnStatusChangedListener() {

			private static final long serialVersionUID = 1L;

			public void onStatusChanged(Object source, STATUS status) {
				if (source == map && status == STATUS.INITIALIZED) {
					showLocationsInMap();

					City city = locations.getCities().get(0);
					aq.id(R.id.locationLabel).text(
							city.getName() + "-" + city.getCountry());
					currentLocation = city.getMapPoint();

					setTimer();
				}
			}
		});

		map.setOnSingleTapListener(new OnSingleTapListener() {

			public void onSingleTap(float x, float y) {
				if (map.isLoaded()) {
					Point pointClicked = map.toMapPoint(new Point(x, y));
					double distance = GeometryEngine.distance(pointClicked,
							currentLocation, map.getSpatialReference());

					Toast.makeText(getApplicationContext(),
							"Distance(Km): " + (distance / 1000),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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

	protected void setTimer() {
		if (!timing) {
			TimingTask timingTask = new TimingTask(aq.id(R.id.progressBar)
					.getProgressBar());
			timer.schedule(timingTask, 100, 500);
			timing = true;
		}
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
}