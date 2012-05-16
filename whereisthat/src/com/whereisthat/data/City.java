package com.whereisthat.data;

import com.esri.core.geometry.Point;

public class City extends Location {
	private long population;
	private String country;
	private Point mapPoint;
	
	public void setPopulation(long population) {
		this.population = population;
	}
	public long getPopulation() {
		return population;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountry() {
		return country;
	}
	public void setMapPoint(Point mapPoint) {
		this.mapPoint = mapPoint;
	}
	public Point getMapPoint() {
		return mapPoint;
	}
}
