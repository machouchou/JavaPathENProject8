package tourGuide.response.rest;

import java.util.UUID;

import lombok.Data;

@Data
public class Attraction extends Location {
	public String attractionName;
	  
	  public String city;
	  
	  public String state;
	  
	  public UUID attractionId;
	  
	  public Attraction() {
	    super();
	    
	  }
	  

}
