package com.reward.test;

import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Test;

import com.reward.service.RewardsService;

import rewardCentral.RewardCentral;

public class RewardserviceTest {
	
	private RewardsService rewardService;
	
	public RewardserviceTest() {
		this.rewardService = new RewardsService(new RewardCentral());

	}
	
	@Test
	public void getAttractionRewardPoints() throws Exception {
		UUID attractionId = UUID.randomUUID();
		UUID userId = UUID.randomUUID();
		
		int result = this.rewardService.getAttractionRewardPoints(attractionId, userId);
	
		assertNotEquals(0, result);
	}

}
