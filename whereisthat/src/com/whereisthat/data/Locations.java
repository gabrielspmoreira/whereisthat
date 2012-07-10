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
	
	private List<Location> cities;
	private List<Location> events;

	public List<Location> getCities(){
		return cities;
	}
	
	public List<Location> getEvents(){
		return events;
	}
	
	public void loadFromXml(Resources resource)
	{
		InputStream isCities = resource.openRawResource(R.raw.cities);
		cities = LocationsParser.parseCities(isCities);
		
		InputStream isEvents = resource.openRawResource(R.raw.historicevents);
		events = LocationsParser.parseHistoricEvent(isEvents);
	}
	
	public Location getNextLocation(Level level) {
		List<Location> dataset = null;
		//TODO: Change to enumerable
		if (level.getDataset().equals("cities")){
			dataset = cities;
		}
		else if (level.getDataset().equals("historicevents")){
			dataset = events;
		}
		
		return getRandomLocation(dataset);
	}
	
	public Location getRandomLocation(List<Location> dataset){
		Random random = new Random();
		int randomLocation = random.nextInt(dataset.size() - 1);
		Location location = dataset.get(randomLocation);
		
		Point point = new Point();
		point.setX(location.getLongitude());
		point.setY(location.getLatitude());
		Point pointReproj = (Point) GeometryEngine.project(point,
				SpatialReference.create(4326), map.getSpatialReference());
		location.setMapPoint(pointReproj);
		
		return location;
	}
}
