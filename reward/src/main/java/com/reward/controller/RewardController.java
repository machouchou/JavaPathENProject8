package com.reward.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reward.service.RewardsService;

@RestController
public class RewardController {
	
	private Logger logger = LoggerFactory.getLogger(RewardController.class);
	
	@Autowired
	RewardsService rewardsService;
	
	@GetMapping("/attractionRewardPoints") 
    public int AttractionRewardPoints(@RequestParam UUID attractionId, @RequestParam UUID userId) {
		return rewardsService.getAttractionRewardPoints(attractionId, userId);
    }
	

}
