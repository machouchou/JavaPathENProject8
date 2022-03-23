package com.tripPricer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;
import com.tripPricer.dto.UserPreferenceDto;
import com.tripPricer.service.TripPricerService;

@RestController
public class TripPricerController {
	
	@Autowired
	TripPricerService tripPricerService;
	
	@GetMapping("/getProvider") 
    public List<Provider> getProvider( UserPreferenceDto userPreferenceDto) {
		return tripPricerService.getProvider(userPreferenceDto);
    }

}
