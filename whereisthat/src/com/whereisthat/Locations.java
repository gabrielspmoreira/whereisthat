package com.whereisthat;

import java.util.List;

public class Locations {
	
	private List<City> citiesList;
	
	public void setCities(List<City> cities){
		citiesList = cities;
	}

	public List<City> getCities(){
		return citiesList;
	}
}
