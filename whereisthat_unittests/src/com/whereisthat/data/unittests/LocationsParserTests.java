package com.whereisthat.data.unittests;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.whereisthat.data.City;
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
		List<City> cities = LocationsParser.parseCities(in);
		
		assertEquals(2, cities.size());
		assertEquals(2, cities.get(1).getId());
		assertEquals("Abidjan", cities.get(1).getName());
		assertEquals(4351086, cities.get(1).getPopulation());
		assertEquals(5.33, cities.get(1).getLatitude(),0);
		assertEquals(-4.03, cities.get(1).getLongitude(),0);
		assertEquals("Ivory Coast", cities.get(1).getCountry());
	}

}
