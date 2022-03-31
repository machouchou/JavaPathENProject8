package tourGuide.user;

import java.util.UUID;

import gpsUtil.location.Attraction;

public class Position {
	private Attraction attraction;
	private Double distanceFromUser;
	
	public Attraction getAttraction() {
		return attraction;
	}
	public void setAttraction(Attraction attraction) {
		this.attraction = attraction;
	}
	public Double getDistanceFromUser() {
		return distanceFromUser;
	}
	public void setDistanceFromUser(Double distanceFromUser) {
		this.distanceFromUser = distanceFromUser;
	}
	
	
	

}
