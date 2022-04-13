package tourGuide.response.rest;

import java.util.Date;
import java.util.UUID;

public class VisitedLocation {
	private UUID userId;
	  
	  private Location location;
	  
	  private Date timeVisited;

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

	public Date getTimeVisited() {
		return timeVisited;
	}

	public void setTimeVisited(Date timeVisited) {
		this.timeVisited = timeVisited;
	}
	  
	  
}
