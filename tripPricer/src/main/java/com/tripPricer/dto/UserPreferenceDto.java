package com.tripPricer.dto;

import java.util.UUID;


public class UserPreferenceDto {
	private String tripPricerApiKey;
	private UUID userId;
	private int numberOfAdults;
	private int numberOfChildren;
	private int tripDuration;
	private int cumulatativeRewardPoints;
	public String getTripPricerApiKey() {
		return tripPricerApiKey;
	}
	public void setTripPricerApiKey(String tripPricerApiKey) {
		this.tripPricerApiKey = tripPricerApiKey;
	}
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public int getNumberOfAdults() {
		return numberOfAdults;
	}
	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}
	public int getNumberOfChildren() {
		return numberOfChildren;
	}
	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	public int getTripDuration() {
		return tripDuration;
	}
	public void setTripDuration(int tripDuration) {
		this.tripDuration = tripDuration;
	}
	public int getCumulatativeRewardPoints() {
		return cumulatativeRewardPoints;
	}
	public void setCumulatativeRewardPoints(int cumulatativeRewardPoints) {
		this.cumulatativeRewardPoints = cumulatativeRewardPoints;
	}
	
}
