package com.tripPricer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tripPricer.service.TripPricerService;

import tripPricer.Provider;

@SpringBootTest
public class TripPricerServiceTest {
	
	@Autowired
	TripPricerService tripPricerService = new TripPricerService();
	
	@Test
	public void getPrice() throws Exception {
		
		String apiKey = ("");
		UUID attractionId = UUID.randomUUID();
		Integer adults = 2;
		Integer children = 3;
		Integer nightsStay = 5 ;
		Integer rewardsPoints = 5;
		
		List<Provider> providers = tripPricerService.getPrice("", UUID.randomUUID(), 
				2, 3, 5, 5);
	
		assertNotEquals(Collections.EMPTY_LIST, providers.size());
		assertEquals(5, providers.size());
	}

}
