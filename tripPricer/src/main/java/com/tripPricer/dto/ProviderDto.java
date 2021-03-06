package com.tripPricer.dto;

import java.util.UUID;

public class ProviderDto {
	private String name;
	  
	private double price;
	  
	private UUID tripId;
	  
	public ProviderDto(UUID tripId, String name, double price) {
	    this.name = name;
	    this.tripId = tripId;
	    this.price = price;
	}

	public ProviderDto() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public UUID getTripId() {
		return tripId;
	}

	public void setTripId(UUID tripId) {
		this.tripId = tripId;
	}
	

}
