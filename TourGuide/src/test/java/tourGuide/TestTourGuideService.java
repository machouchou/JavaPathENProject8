package tourGuide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tourGuide.dto.UserFiveNearAttracDto;
import tourGuide.helper.InternalTestHelper;
import tourGuide.response.rest.Attraction;
import tourGuide.response.rest.CurrentLocation;
import tourGuide.response.rest.Provider;
import tourGuide.response.rest.VisitedLocation;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTourGuideService {
	
	@Autowired
	GpsUtilProxy gpsUtilProxy;
	@Autowired
	RewardProxy rewardProxy;
	@Autowired
	TripPricerProxy tripPricerProxy;
	
	@Autowired
	TourGuideService tourGuideService;
	
	List<Attraction> attractions = new ArrayList<Attraction>();
	Executor executor;
	
	@Before
	  public void setUp() {
	    executor = Executors.newFixedThreadPool(100);
	    Locale.setDefault(new Locale("en", "US", "WIN"));

	}
	@Test
	public void getUserLocation() {
		RewardsService rewardsService = new RewardsService(gpsUtilProxy, rewardProxy);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilProxy, rewardsService, rewardProxy, tripPricerProxy);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);
		tourGuideService.tracker.stopTracking();
		assertTrue(visitedLocation.getUserId().equals(user.getUserId()));
	}
	
	@Test
	public void addUser() {
		RewardsService rewardsService = new RewardsService(gpsUtilProxy, rewardProxy);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilProxy, rewardsService, rewardProxy, tripPricerProxy);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		User retrivedUser = tourGuideService.getUser(user.getUserName());
		User retrivedUser2 = tourGuideService.getUser(user2.getUserName());

		tourGuideService.tracker.stopTracking();
		
		assertEquals(user, retrivedUser);
		assertEquals(user2, retrivedUser2);
	}
	
	@Test
	public void getAllUsers() {
		RewardsService rewardsService = new RewardsService(gpsUtilProxy, rewardProxy);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilProxy, rewardsService, rewardProxy, tripPricerProxy);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		List<User> allUsers = tourGuideService.getAllUsers();

		tourGuideService.tracker.stopTracking();
		
		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
	}
	
	@Test
	public void trackUser() {
		RewardsService rewardsService = new RewardsService(gpsUtilProxy, rewardProxy);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilProxy, rewardsService, rewardProxy, tripPricerProxy);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(user.getUserId(), visitedLocation.getUserId());
	}
	
	@Test
	public void getNearbyAttractions() {
		RewardsService rewardsService = new RewardsService(gpsUtilProxy, rewardProxy);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilProxy, rewardsService, rewardProxy, tripPricerProxy);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);
		
		List<UserFiveNearAttracDto> nearbyAttractions = tourGuideService.getNearByAttractions(user);
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(5, nearbyAttractions.size());
	}
	
	@Test
	public void getTripDeals() {
		RewardsService rewardsService = new RewardsService(gpsUtilProxy, rewardProxy);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilProxy, rewardsService, rewardProxy, tripPricerProxy);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		List<Provider> providers = tourGuideService.getTripDeals(user);
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(5, providers.size());
	}
	
	@Test
	public void getAllCurrentLocations() {
		//ARRANGE:
		User user1 = tourGuideService.getInternalUserMap().get("internalUser0");
		User user2 = tourGuideService.getInternalUserMap().get("internalUser1");
		
		List<User> lUser = new ArrayList<User>();
		lUser.add(user1);
		lUser.add(user2);
		
		//ACT:
		List<CurrentLocation> lResult = tourGuideService.getAllCurrentLocations(lUser);
		
		//ASSERT:
		assertEquals(2, lResult.size());
		assertNotNull(lResult.get(0).getUserId());
		assertNotNull(lResult.get(1).getUserId());
		
	}
}
