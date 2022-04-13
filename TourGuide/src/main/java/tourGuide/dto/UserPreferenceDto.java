package tourGuide.dto;

import lombok.Data;

@Data
public class UserPreferenceDto {
	private String userName;
	private int tripDuration = 1;
	private int ticketQuantity = 1;
	private int numberOfAdults = 1;
	private int numberOfChildren = 0;
}
