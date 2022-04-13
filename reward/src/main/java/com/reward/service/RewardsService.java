package com.reward.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import rewardCentral.RewardCentral;

@Service
public class RewardsService {
	
	private Logger logger = LoggerFactory.getLogger(RewardsService.class);

	private final RewardCentral rewardCentral;
	
	public RewardsService(RewardCentral rewardCentral) {
		this.rewardCentral = rewardCentral;

	}
	
	public int getAttractionRewardPoints(UUID attractionId, UUID userId) {
		return this.rewardCentral.getAttractionRewardPoints(attractionId, userId);
	    
	  }
	
}
