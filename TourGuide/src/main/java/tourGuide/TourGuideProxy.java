package tourGuide;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "reward", url = "localhost:9001")
public interface TourGuideProxy {
   @GetMapping(value = "/attractionRewardPoints")
   int getAttractionRewardPoints(UUID userId, UUID attractionId);

}