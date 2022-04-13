package tourGuide.dto;

import lombok.Data;

@Data
public class UserFiveNearAttracDto {
	private String attractionName;
	private double attractionLatitude;
	private double attractionLongitude;
	private double userLatitude;
	private double userLongitude;
	private Double distanceFromUser;
	private int rewardPoints;
}
