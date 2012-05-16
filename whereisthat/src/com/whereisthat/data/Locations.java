package com.whereisthat.data;

import java.util.List;
import java.util.Random;

public class Locations {
	
	private List<City> cities;
	
	public void setCities(List<City> cities){
		this.cities = cities;
	}

	public List<City> getCities(){
		return cities;
	}
	
	public City getRandomCity(){
		Random random = new Random();
		int randomCity = random.nextInt(cities.size() - 1);
		City city = cities.get(randomCity);
		return city;
	}
}
