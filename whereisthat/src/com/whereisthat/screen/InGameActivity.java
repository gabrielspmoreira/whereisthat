package com.whereisthat.screen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.androidquery.AQuery;
import com.androidquery.util.XmlDom;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnStatusChangedListener.STATUS;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.Unit;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.MarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;
import com.whereisthat.City;
import com.whereisthat.Locations;
import com.whereisthat.LocationsParser;
import com.whereisthat.R;
import com.whereisthat.TimingTask;
import com.whereisthat.R.id;
import com.whereisthat.R.layout;
import com.whereisthat.R.raw;


public class InGameActivity extends Activity {
	
	private MapView map;
	private GraphicsLayer locationsLayer;
	
	private AQuery aq;
	
	private Locations locations = new Locations();
	
	private Point currentLocation;
	
	private Timer timer = new Timer();
	private boolean timing = false; 

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        aq = new AQuery(this);      
        
        readLocations();
        
        // Retrieve the map and initial extent from XML layout
		map = (MapView)findViewById(R.id.map);
		
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
					aq.id(R.id.locationLabel).text(city.getName() + "-" + city.getCountry());
					currentLocation = city.getMapPoint();
					
					setTimer();
				}
			}
		});
		
		map.setOnSingleTapListener(new OnSingleTapListener() {
			
			
			public void onSingleTap(float x, float y) {
				if (map.isLoaded()) {				
					Point pointClicked = map.toMapPoint(new Point(x,y));
					double distance = GeometryEngine.distance(pointClicked, currentLocation, map.getSpatialReference());
					
					Unit mapUnit = map.getSpatialReference().getUnit();
					double distanceKm = Unit.convertUnits(
							distance,Unit.create(LinearUnit.Code.KILOMETER),
							mapUnit);
					
					Toast.makeText(getApplicationContext(), "Distance(Km): "+(distance/1000),
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
	}

	protected void readLocations(){
		InputStream is = getResources().openRawResource(R.raw.cities);
		
		List<City> cities = LocationsParser.parseCities(is);
		locations.setCities(cities);		
	}
	
	protected void showLocationsInMap(){
		locationsLayer = new GraphicsLayer(map.getSpatialReference(), new Envelope(-19332033.11, -3516.27, -1720941.80, 11737211.28));
		
		for (City city : locations.getCities()) {
			Point point = new Point();
			point.setX(city.getLongitude());
			point.setY(city.getLatitude());									
			Point pointReproj = (Point)GeometryEngine.project(point, SpatialReference.create(4326), map.getSpatialReference());
			
			city.setMapPoint(pointReproj);
			
			Graphic graphic = new Graphic(pointReproj, new SimpleMarkerSymbol(Color.RED,8,STYLE.CIRCLE));
			
			locationsLayer.addGraphic(graphic);
		}
		
		map.addLayer(locationsLayer);
	}
	
	
	protected void setTimer() {
		if (!timing){
			TimingTask timingTask = new TimingTask(aq.id(R.id.progressBar).getProgressBar());
			timer.schedule(timingTask, 100, 500);
			timing = true;
		}		
	}
}