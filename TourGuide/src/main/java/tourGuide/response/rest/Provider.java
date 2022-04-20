package tourGuide.response.rest;

import java.util.UUID;

public class Provider {
	private String name;
	  
	  private double price;
	  
	  private String tripId;
	  
	  
	  
	  public Provider() {
		  this.name = "";
			this.tripId = "";
			this.price = 0;
	}

	public Provider(String tripId, String name, double price) {
	    this.name = name;
	    this.tripId = tripId;
	    this.price = price;
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

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}


}
