package com.whereisthat.data;

public class City extends Location {
	private long population;
	private String country;	
	
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
	public String toString(){
		return this.getName() + " - " + this.country;		
	}
}
