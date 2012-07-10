package com.whereisthat.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

import com.androidquery.util.XmlDom;

public class LocationsParser {
	
	public static List<Location> parseCities(InputStream is){		
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
			
			return cities;
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}
	
	public static List<Location> parseHistoricEvent(InputStream is){		
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
			
			return events;
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}
}
