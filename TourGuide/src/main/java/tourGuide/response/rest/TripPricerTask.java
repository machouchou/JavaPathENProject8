package tourGuide.response.rest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import tourGuide.TripPricerProxy;

public class TripPricerTask implements Callable<List<Provider>> {
	private final UUID attractionId;
	  
	  private final String apiKey;
	  
	  private final int adults;
	  
	  private final int children;
	  
	  private final int nightsStay;
	  
	  //private final TripPricerProxy tripPricerProxy;
	  
	  public TripPricerTask(String apiKey, UUID attractionId, int adults, int children, int nightsStay) {
	    this.apiKey = apiKey;
	    this.attractionId = attractionId;
	    this.adults = adults;
	    this.children = children;
	    this.nightsStay = nightsStay;
	  }

	
	public class Tracker extends Thread {
		
	}


	@Override
	public List<Provider> call() throws Exception {
		return null;
	}
	  

}
