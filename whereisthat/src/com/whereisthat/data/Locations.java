package com.whereisthat.data;

import java.util.List;
import java.util.Random;

import com.esri.android.map.MapView;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;

public class Locations {
	
	private List<City> cities;
	
	public void setCities(List<City> cities){
		this.cities = cities;
	}

	public List<City> getCities(){
		return cities;
	}
	
	public City getRandomCity(MapView map){
		Random random = new Random();
		int randomCity = random.nextInt(cities.size() - 1);
		City city = cities.get(randomCity);
		
		Point point = new Point();
		point.setX(city.getLongitude());
		point.setY(city.getLatitude());
		Point pointReproj = (Point) GeometryEngine.project(point,
				SpatialReference.create(4326), map.getSpatialReference());
		city.setMapPoint(pointReproj);
		
		return city;
	}
}
