package tourGuide;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;

import tourGuide.dto.UserFiveNearAttracDto;
import tourGuide.dto.UserPreferenceDto;
import tourGuide.exception.UserPreferenceException;
import tourGuide.response.rest.Provider;
import tourGuide.response.rest.VisitedLocation;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;

@RestController
public class TourGuideController {

	@Autowired
	TourGuideService tourGuideService;
	
	@Autowired
	RewardsService rewardsService;
	
    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }
    
    @RequestMapping("/users") 
    public List<User> getAllUser() {
    	return tourGuideService.getAllUsers();
    }
    @RequestMapping("/getLocation") 
    public String getLocation(@RequestParam String userName) throws ExecutionException, InterruptedException {
    	VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
		return JsonStream.serialize(visitedLocation.getLocation());
    }
    
    //  TODO: Change this method to no longer return a List of Attractions.
 	//  Instead: Get the closest five tourist attractions to the user - no matter how far away they are.
 	//  Return a new JSON object that contains:
    	// Name of Tourist attraction, 
        // Tourist attractions lat/long, 
        // The user's location lat/long, 
        // The distance in miles between the user's location and each of the attractions.
        // The reward points for visiting each Attraction.
        //    Note: Attraction reward points can be gathered from RewardsCentral
    @RequestMapping("/getNearbyAttractions") 
    public String getNearbyAttractions(@RequestParam String userName) throws ExecutionException, InterruptedException {
    	VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
    	return JsonStream.serialize(tourGuideService.getNearByAttractions(getUser(userName)));
    }
    
    @RequestMapping("/fiveNearbyAttractions") 
    public String getFiveNearbyAttractions(@RequestParam String userName) throws ExecutionException, InterruptedException {
    	//VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
    	User user = tourGuideService.getInternalUserMap().get(userName);
      	return JsonStream.serialize(rewardsService.getFiveNearAttractions(user));
    }
    
    @RequestMapping("/getRewards") 
    public String getRewards(@RequestParam String userName) {
    	return JsonStream.serialize(tourGuideService.getUserRewards(getUser(userName)));
    }
    
    @RequestMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() {
    	// TODO: Get a list of every user's most recent location as JSON
    	//- Note: does not use gpsUtil to query for their current location, 
    	//        but rather gathers the user's current location from their stored location history.
    	//
    	// Return object should be the just a JSON mapping of userId to Locations similar to:
    	//     {
    	//        "019b04a9-067a-4c76-8817-ee75088c3822": {"longitude":-48.188821,"latitude":74.84371} 
    	//        ...
    	//     }
    	
    	return JsonStream.serialize(tourGuideService.getAllCurrentLocations(getAllUser()));
    }
    
    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {
    	List<Provider> providers = tourGuideService.getTripDeals(getUser(userName));
    	return JsonStream.serialize(providers);
    }
    
    @RequestMapping("/getUser")
    public User getUser(@RequestParam String userName) {
    	return tourGuideService.getUser(userName);
    }
   
    @RequestMapping(method = RequestMethod. POST, path = "/userPreferences")
    public User getUserPreferences(@RequestBody UserPreferenceDto userPreferenceDto) {
    	try {
			User user = tourGuideService.upDateUserPreferences(userPreferenceDto);
			return user;
		} catch (UserPreferenceException e) {
			
			return null;
		}
    }

}