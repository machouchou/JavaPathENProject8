package tourGuide.response.rest;

import java.util.UUID;

public class CurrentLocation {
	
	private UUID userId;
	private Location location;
	
	public CurrentLocation() {
		super();
	}

	public CurrentLocation(UUID userId, Location location) {
		super();
		this.userId = userId;
		this.location = location;
	}



	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	

}
