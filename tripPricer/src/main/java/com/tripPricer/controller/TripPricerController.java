package com.tripPricer.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;
import com.tripPricer.service.TripPricerService;

@RestController
public class TripPricerController {
	
	@Autowired
	TripPricerService tripPricerService;
	
	@GetMapping("/getPrice") 
    public List<Provider> getPrice(@RequestParam String apiKey,
    		@RequestParam String attractionId,
    		@RequestParam int adults,
    		@RequestParam int children, 
    		@RequestParam int nightsStay,
    		@RequestParam int rewardsPoints ){
		return tripPricerService.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints);
	}

}
