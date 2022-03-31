package com.tripPricer.dto;

import java.util.UUID;


public class TripPricerDto {
	private String apiKey;
	private UUID attractionId;
	private int adults;
	private int children;
	private int nightsStay;
	private int rewardsPoints;
	
	public TripPricerDto() {
		super();
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public UUID getAttractionId() {
		return attractionId;
	}

	public void setAttractionId(UUID attractionId) {
		this.attractionId = attractionId;
	}

	public int getAdults() {
		return adults;
	}

	public void setAdults(int adults) {
		this.adults = adults;
	}

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public int getNightsStay() {
		return nightsStay;
	}

	public void setNightsStay(int nightsStay) {
		this.nightsStay = nightsStay;
	}

	public int getRewardsPoints() {
		return rewardsPoints;
	}

	public void setRewardsPoints(int rewardsPoints) {
		this.rewardsPoints = rewardsPoints;
	}
	
}
