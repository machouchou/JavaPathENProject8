package com.reward.service;

import java.util.UUID;
import org.springframework.stereotype.Service;

import rewardCentral.RewardCentral;

@Service
public class RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	private final RewardCentral rewardsCentral;
	
	public RewardsService(RewardCentral rewardCentral) {
		this.rewardsCentral = rewardCentral;

	}
	
	public int getAttractionRewardPoints(UUID attractionId, UUID userId) {
		return this.rewardsCentral.getAttractionRewardPoints(attractionId, userId);
	    
	  }
	
}
