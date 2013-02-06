package com.whereisthat.data;

public class Landmark extends Location {
	private String city;
	private String country;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String toString(){
		return this.getName() + " - " + this.country;		
	}
}
