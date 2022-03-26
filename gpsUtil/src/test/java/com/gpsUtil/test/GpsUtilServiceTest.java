package com.gpsUtil.test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gpsUtil.service.GpsUtilService;

import gpsUtil.location.Attraction;

@SpringBootTest
public class GpsUtilServiceTest {
	
	@Autowired
	GpsUtilService gpsUtilService;
	
	@Test
	public void getAttractions() throws Exception {
		
		List<Attraction> attractions = gpsUtilService.getAttractions();
		
		assertEquals(26, attractions.size());
	}
	

}
