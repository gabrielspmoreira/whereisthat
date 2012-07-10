package com.whereisthat.data;

public class HistoricEvent extends Location {
	private short year;
	private String description;	

	public short getYear() {
		return year;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString(){
		return this.year + " - " + this.name;		
	}
}
