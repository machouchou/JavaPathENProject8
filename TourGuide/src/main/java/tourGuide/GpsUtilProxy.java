package tourGuide;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tourGuide.response.rest.Attraction;
import tourGuide.response.rest.VisitedLocation;

@FeignClient(name = "gpsUtil", url = "gps-util:9002")
public interface GpsUtilProxy {
	
	@GetMapping(value = "/attractions")
	List<Attraction> getAttractions();

	@GetMapping(value = "/userLocation")
	VisitedLocation getUserLocation (@RequestParam("userId") UUID userId);

}
