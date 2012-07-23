package com.whereisthat.data.unittests;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.junit.Test;

import com.whereisthat.data.City;
import com.whereisthat.data.LocationType;
import com.whereisthat.data.LocationsDataset;
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
		LocationsDataset cities = LocationsParser.parseCities(in, LocationType.citiesEasy);
		
		assertEquals(2, cities.getLocations().size());
		
		City city2 = (City) cities.getLocations().get(0);
		assertEquals(10, city2.getId());
		assertEquals("Alexandria", city2.getName());
		assertEquals(4532174, city2.getPopulation());
		assertEquals(31.22, city2.getLatitude(),0);
		assertEquals(29.95, city2.getLongitude(),0);
		assertEquals("Egypt", city2.getCountry());
	}

}
