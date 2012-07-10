package com.whereisthat.data.unittests;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.whereisthat.data.City;
import com.whereisthat.data.Location;
import com.whereisthat.data.LocationsParser;

public class LocationsParserTests {
	
	private static final String citiesPath = "testsdata/cities_tests.xml";

	@Test
	public void testParseCities() {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(citiesPath));
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
			return;
		}		
		List<Location> cities = LocationsParser.parseCities(in);
		
		assertEquals(2, cities.size());
		
		City city2 = (City) cities.get(1);
		assertEquals(2, city2.getId());
		assertEquals("Abidjan", city2.getName());
		assertEquals(4351086, city2.getPopulation());
		assertEquals(5.33, city2.getLatitude(),0);
		assertEquals(-4.03, city2.getLongitude(),0);
		assertEquals("Ivory Coast", city2.getCountry());
	}

}
