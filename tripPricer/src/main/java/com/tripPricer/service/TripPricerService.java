package com.tripPricer.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tripPricer.Provider;
import tripPricer.TripPricer;

@Service
public class TripPricerService {
	
	private Logger logger = LoggerFactory.getLogger(TripPricerService.class);
	
	private final TripPricer tripPricer;
	
	public TripPricerService(TripPricer tripPricer) {
		this.tripPricer = tripPricer;

	}
	
	public List<Provider> getPrice(String apiKey, UUID attractionId, 
									int adults, int children, 
									int nightsStay, int rewardsPoints) {
		
		logger.debug("Trip Pricer Service");
		
	    return this.tripPricer.getPrice(apiKey, UUID.randomUUID(), adults, children, nightsStay, rewardsPoints);
	}
	
	public String getProviderName(String apiKey, int adults) {
		logger.debug("Trip Pricer Service");
		return this.tripPricer.getProviderName(apiKey, adults);
	}
	    
}
