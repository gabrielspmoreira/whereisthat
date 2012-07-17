package com.whereisthat.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

import com.androidquery.util.XmlDom;

public class LocationsParser {
	
	private static LocationsDataset parseLocationsDatasetSettings(XmlDom xmlRoot){
		//String description = xmlRoot.attr("Description");
		//boolean hasLongQuestions = Boolean.parseBoolean(xmlRoot.attr("HasLongQuestions"));
		boolean reproject = Boolean.parseBoolean(xmlRoot.attr("Reproject"));
		return new LocationsDataset(null, reproject);
	}
	
	public static LocationsDataset parseCities(InputStream is){		
		try {
			XmlDom xmlRoot = new XmlDom(is);
			
			List<XmlDom> citiesDom = xmlRoot.children("City");
			List<Location> cities = new ArrayList<Location>();
			
			for(XmlDom cityDom: citiesDom){
				City city = new City();
				city.setId(Integer.parseInt(cityDom.text("Id")));
				city.setName(cityDom.text("City"));
				city.setPopulation(Long.parseLong(cityDom.text("Population")));
				city.setLatitude(Double.parseDouble(cityDom.text("Lat")));
				city.setLongitude(Double.parseDouble(cityDom.text("Long")));
				city.setCountry(cityDom.text("Country"));
				cities.add(city);
			}
			
			LocationsDataset locationDataset = parseLocationsDatasetSettings(xmlRoot);
			locationDataset.setLocations(cities);
			
			return locationDataset;
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}
	
	public static LocationsDataset parseHistoricEvent(InputStream is){		
		try {
			XmlDom xmlRoot = new XmlDom(is);
			
			List<XmlDom> eventsDom = xmlRoot.children("HistoricEvent");
			List<Location> events = new ArrayList<Location>();
			
			for(XmlDom eventDom : eventsDom){
				HistoricEvent event = new HistoricEvent();
				event.setYear(Short.parseShort(eventDom.text("Year")));
				event.setName(eventDom.text("Place"));
				event.setLatitude(Double.parseDouble(eventDom.text("Lat")));
				event.setLongitude(Double.parseDouble(eventDom.text("Long")));
				event.setDescription(eventDom.text("Description"));
				events.add(event);
			}
			
			LocationsDataset locationDataset = parseLocationsDatasetSettings(xmlRoot);
			locationDataset.setLocations(events);
			return locationDataset;
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}
	
	public static LocationsDataset parseLandmarks(InputStream is){		
		try {
			XmlDom xmlRoot = new XmlDom(is);
			List<XmlDom> landmarkDom = xmlRoot.children("Landmark");
			List<Location> landmarks = new ArrayList<Location>();
			
			for(XmlDom eventDom : landmarkDom){
				Landmark landmark = new Landmark();
				landmark.setName(eventDom.text("Name"));
				landmark.setLatitude(Double.parseDouble(eventDom.text("Lat")));
				landmark.setLongitude(Double.parseDouble(eventDom.text("Long")));
				landmark.setCity(eventDom.text("Town"));
				landmark.setCountry(eventDom.text("Country"));
				landmarks.add(landmark);
			}
			
			LocationsDataset locationDataset = parseLocationsDatasetSettings(xmlRoot);
			locationDataset.setLocations(landmarks);
			return locationDataset;
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}
}
