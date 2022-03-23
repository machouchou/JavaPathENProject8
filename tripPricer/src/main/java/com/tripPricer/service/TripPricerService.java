package com.tripPricer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tripPricer.dto.UserPreferenceDto;
import tripPricer.Provider;
import tripPricer.TripPricer;

@Service
public class TripPricerService {
	private final TripPricer tripPricer = new TripPricer();

 public List<Provider> getProvider(UserPreferenceDto userPreferenceDto) {
		return tripPricer.getPrice(userPreferenceDto.getTripPricerApiKey(), userPreferenceDto.getUserId(), userPreferenceDto.getNumberOfAdults(), 
				userPreferenceDto.getNumberOfChildren(), userPreferenceDto.getTripDuration(), userPreferenceDto.getCumulatativeRewardPoints());
	}
}
