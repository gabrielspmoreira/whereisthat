package com.whereisthat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

import com.androidquery.util.XmlDom;

public class LocationsParser {
	
	public static List<City> parseCities(InputStream is){		
		try {
			XmlDom xmlRoot = new XmlDom(is);
			List<XmlDom> citiesDom = xmlRoot.children("City");
			List<City> cities = new ArrayList<City>();
			
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
}
