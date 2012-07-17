package com.whereisthat.data;

import java.util.List;

public class LocationsDataset {
	private List<Location> locations;
	private boolean reproject;
	
	public LocationsDataset(List<Location> locations, boolean reproject){
		this.locations = locations;
		this.setReproject(reproject);
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public boolean getReproject() {
		return reproject;
	}

	public void setReproject(boolean reproject) {
		this.reproject = reproject;
	}

}
