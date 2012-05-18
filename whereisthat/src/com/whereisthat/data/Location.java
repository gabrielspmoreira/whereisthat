package com.whereisthat.data;

import com.esri.core.geometry.Point;

public class Location {

	protected int id;
	protected String name;
	protected double latitude;
	protected double longitude;
	protected Point mapPoint;

	public Location() {
		super();
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLongitude() {
		return longitude;
	}
	
	public void setMapPoint(Point mapPoint) {
		this.mapPoint = mapPoint;
	}
	public Point getMapPoint() {
		return mapPoint;
	}
	
	public String toString(){
		return this.name;		
	}
}