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
	
	private LocationsDataset citiesEasy;
	private LocationsDataset citiesHard;
	private LocationsDataset events;
	private LocationsDataset landmarks;

	public LocationsDataset getCitiesEasy(){
		return citiesEasy;
	}
	
	public LocationsDataset getCitiesHard(){
		return citiesHard;
	}
	
	public LocationsDataset getEvents(){
		return events;
	}
	
	public LocationsDataset getLandmarks(){
		return landmarks;
	}
	
	public void loadFromXml(Resources resource)
	{
		InputStream isCitiesEasy = resource.openRawResource(R.raw.citieseasy);
		citiesEasy = LocationsParser.parseCities(isCitiesEasy, LocationType.citiesEasy);
		
		InputStream isCitiesHard = resource.openRawResource(R.raw.citieshard);
		citiesHard = LocationsParser.parseCities(isCitiesHard, LocationType.citiesHard);
		
		InputStream isEvents = resource.openRawResource(R.raw.historicevents);
		events = LocationsParser.parseHistoricEvent(isEvents, LocationType.historicEvents);
		
		InputStream isLandmarks = resource.openRawResource(R.raw.landmarks);
		landmarks = LocationsParser.parseLandmarks(isLandmarks, LocationType.landmarks);
	}
	
	public Location getNextLocation(Level level) {
		LocationsDataset dataset = null;		
		
		switch (level.getLocationType()){
			case citiesEasy:
				dataset = citiesEasy;
				break;
			case landmarks:
				dataset = landmarks;
				break;
			case citiesHard:
				dataset = citiesHard;
				break;
			case historicEvents:
				dataset = events;
				break;
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
