package com.whereisthat.data;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

import android.content.res.Resources;

import com.esri.android.map.MapView;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.whereisthat.R;

public class Locations {	
	
	private MapView map; //TODO: Remove this map dependency
	
	public Locations(MapView map){
		this.map = map;
	}
	
	private LocationsDataset cities;
	private LocationsDataset events;
	private LocationsDataset landmarks;

	public LocationsDataset getCities(){
		return cities;
	}
	
	public LocationsDataset getEvents(){
		return events;
	}
	
	public void loadFromXml(Resources resource)
	{
		InputStream isCities = resource.openRawResource(R.raw.cities);
		cities = LocationsParser.parseCities(isCities);
		
		InputStream isEvents = resource.openRawResource(R.raw.historicevents);
		events = LocationsParser.parseHistoricEvent(isEvents);
		
		InputStream isLandmarks = resource.openRawResource(R.raw.landmarks);
		landmarks = LocationsParser.parseLandmarks(isLandmarks);
	}
	
	public Location getNextLocation(Level level) {
		LocationsDataset dataset = null;
		//TODO: Change to enumerable
		if (level.getDataset().equals("cities")){
			dataset = cities;
		}
		else if (level.getDataset().equals("historicevents")){
			dataset = events;
		}
		else if (level.getDataset().equals("landmarks")){
			dataset = landmarks;
		}
		
		return getRandomLocation(dataset);
	}
	
	public Location getRandomLocation(LocationsDataset dataset){
		Random random = new Random();
		int randomLocation = random.nextInt(dataset.getLocations().size() - 1);
		Location location = dataset.getLocations().get(randomLocation);
		
		Point point = new Point(location.getLongitude(), location.getLatitude());
		SpatialReference originSpatialReference = null;
		if (dataset.getReproject()){ // Convert from WGS84 to WebMercador
			originSpatialReference = SpatialReference.create(4326);
		}
		else {
			originSpatialReference = map.getSpatialReference();
		}
		Point pointReproj = (Point) GeometryEngine.project(point, originSpatialReference, map.getSpatialReference());
		location.setMapPoint(pointReproj);
		
		return location;
	}
}
