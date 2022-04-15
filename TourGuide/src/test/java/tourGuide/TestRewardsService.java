package tourGuide;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tourGuide.helper.InternalTestHelper;
import tourGuide.response.rest.Attraction;
import tourGuide.response.rest.VisitedLocation;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tourGuide.user.UserReward;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRewardsService {
	
	@Autowired
	GpsUtilProxy gpsUtilProxy;
	@Autowired
	RewardProxy rewardProxy;
	@Autowired
	TripPricerProxy tripPricerProxy;
	
	
	@Before
	  public void setUp() {
	   // executor = Executors.newFixedThreadPool(100);
	    Locale.setDefault(new Locale("en", "US", "WIN"));
	}

	@Test
	public void userGetRewards() {
		RewardsService rewardsService = new RewardsService(gpsUtilProxy, rewardProxy);

		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilProxy, rewardsService, rewardProxy, tripPricerProxy);
		//List<Attraction> attractions = gpsUtilProxy.getAttractions();
		//System.out.println(gpsUtilProxy);
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		Attraction attraction = gpsUtilProxy.getAttractions().get(0);
		VisitedLocation visitedLocation = new VisitedLocation();
		visitedLocation.setUserId(user.getUserId());
		visitedLocation.setLocation(attraction);
		visitedLocation.setTimeVisited(new Date());
		user.addToVisitedLocations(visitedLocation);
		tourGuideService.trackUserLocation(user);
		List<UserReward> userRewards = user.getUserRewards();
		tourGuideService.tracker.stopTracking();
		assertTrue(userRewards.size() == 1);
	}
	
	@Test
	public void isWithinAttractionProximity() throws ExecutionException, InterruptedException {
		RewardsService rewardsService = new RewardsService(gpsUtilProxy, rewardProxy);
		Attraction attraction = gpsUtilProxy.getAttractions().get(0);
		assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction));
	}
	
	// Needs fixed - can throw ConcurrentModificationException
	@Test
	public void nearAllAttractions() {
		RewardsService rewardsService = new RewardsService(gpsUtilProxy, rewardProxy);
		rewardsService.setProximityBuffer(Integer.MAX_VALUE);

		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilProxy, rewardsService, rewardProxy, tripPricerProxy);
		
		rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0));
		
		List<User> users = tourGuideService.getAllUsers();
		List<UserReward> userRewards = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0));
		tourGuideService.tracker.stopTracking();

		assertEquals(gpsUtilProxy.getAttractions().size(), userRewards.size());
	}
	
}
