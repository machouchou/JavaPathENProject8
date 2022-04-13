package tourGuide;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "reward", url = "localhost:9001")
public interface RewardProxy {
   @GetMapping(value = "/attractionRewardPoints")
   int getAttractionRewardPoints(@RequestParam("userId") UUID userId, @RequestParam("attractionId") UUID attractionId);

}