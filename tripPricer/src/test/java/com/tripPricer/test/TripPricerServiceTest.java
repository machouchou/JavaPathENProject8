package com.tripPricer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tripPricer.service.TripPricerService;

import tripPricer.Provider;
import tripPricer.TripPricer;

@SpringBootTest
public class TripPricerServiceTest {
	
	private TripPricerService tripPricerService;
	
	public TripPricerServiceTest() {
		this.tripPricerService = new TripPricerService(new TripPricer());

	}
	
	@Test
	public void getPrice() throws Exception {
		
		String apiKey = ("");
		UUID attractionId = UUID.randomUUID();
		Integer adults = 2;
		Integer children = 3;
		Integer nightsStay = 5 ;
		Integer rewardsPoints = 5;
		
		List<Provider> providers = tripPricerService.getPrice(apiKey, attractionId.toString(), 
				adults, children, nightsStay, rewardsPoints);
	
		assertNotEquals(Collections.EMPTY_LIST, providers.size());
		assertEquals(5, providers.size());
	}

}
