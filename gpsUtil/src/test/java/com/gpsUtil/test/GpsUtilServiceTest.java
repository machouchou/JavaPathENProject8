package com.gpsUtil.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.gpsUtil.service.GpsUtilService;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;

@SpringBootTest
public class GpsUtilServiceTest {
	
	private GpsUtilService gpsUtilService;
	
	public GpsUtilServiceTest() {
		this.gpsUtilService = new GpsUtilService(new GpsUtil());

	}
	
	@Test
	public void getAttractions() throws Exception {
		
		List<Attraction> attractions = gpsUtilService.getAttractions();
		
		assertEquals(26, attractions.size());
	}
	

}
