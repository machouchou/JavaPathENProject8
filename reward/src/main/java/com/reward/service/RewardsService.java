package com.reward.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import rewardCentral.RewardCentral;

@Service
public class RewardsService {
	
	private Logger logger = LoggerFactory.getLogger(RewardsService.class);

	private final RewardCentral rewardsCentral;
	
	public RewardsService(RewardCentral rewardCentral) {
		this.rewardsCentral = rewardCentral;

	}
	
	public int getAttractionRewardPoints(UUID attractionId, UUID userId) {
		return this.rewardsCentral.getAttractionRewardPoints(attractionId, userId);
	    
	  }
	
}
