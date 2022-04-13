package tourGuide;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tourGuide.response.rest.Provider;


@FeignClient(name = "tripPricer", url = "localhost:9003")
public interface TripPricerProxy {
	@GetMapping(value = "/getPrice") 
    List<Provider> getPrice(@RequestParam("apiKey") String apiKey,
    		@RequestParam("attractionId") UUID attractionId,
    		@RequestParam("adults") int adults,
    		@RequestParam("children") int children, 
    		@RequestParam("nightStay") int nightsStay,
    		@RequestParam("rewardsPoints") int rewardsPoints );

}
