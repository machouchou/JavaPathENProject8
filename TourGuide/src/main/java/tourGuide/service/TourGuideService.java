package tourGuide.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tourGuide.GpsUtilProxy;
import tourGuide.RewardProxy;
import tourGuide.TripPricerProxy;
import tourGuide.dto.UserPreferenceDto;
import tourGuide.exception.UserPreferenceException;
import tourGuide.helper.InternalTestHelper;
import tourGuide.response.rest.Attraction;
import tourGuide.response.rest.Location;
import tourGuide.response.rest.Provider;
import tourGuide.response.rest.VisitedLocation;
import tourGuide.tracker.Tracker;
import tourGuide.user.User;
import tourGuide.user.UserReward;

@Service
public class TourGuideService {
	private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
	private final RewardsService rewardsService;
	public final Tracker tracker;
	boolean testMode = true;
	
	private final GpsUtilProxy gpsUtilProxy;
	private final RewardProxy rewardProxy;
	private final TripPricerProxy tripPricerProxy;
	
	public TourGuideService(GpsUtilProxy gpsUtilProxy, RewardsService rewardsService, RewardProxy rewardProxy, TripPricerProxy tripPricerProxy  ) {
		this.gpsUtilProxy = gpsUtilProxy;
		this.rewardsService = rewardsService;
		this.rewardProxy = rewardProxy;
		this.tripPricerProxy = tripPricerProxy;
		
		if(testMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			initializeInternalUsers();
			logger.debug("Finished initializing users");
		}
		tracker = new Tracker(gpsUtilProxy, this);
		addShutDownHook();
	}
	
	public List<UserReward> getUserRewards(User user) {
		return user.getUserRewards();
	}
	
	public VisitedLocation getUserLocation(User user) 
			throws ExecutionException, InterruptedException {
		VisitedLocation visitedLocation = (user.getVisitedLocations().size() > 0) ?
			user.getLastVisitedLocation() :
			trackUserLocation(user);
		return visitedLocation;
	}
	
	public User getUser(String userName) {
		return internalUserMap.get(userName);
	}
	
	public User upDateUserPreferences(UserPreferenceDto  userPreferenceDto) throws UserPreferenceException {
		 User user =internalUserMap.get(userPreferenceDto.getUserName());
		if (user.getUserPreferences() != null) {
			user.getUserPreferences().setTripDuration(userPreferenceDto.getTripDuration());
			user.getUserPreferences().setTicketQuantity(userPreferenceDto.getTicketQuantity());
			user.getUserPreferences().setNumberOfChildren(userPreferenceDto.getNumberOfChildren());
			user.getUserPreferences().setNumberOfAdults(userPreferenceDto.getNumberOfAdults());
			return user;
		}
		throw new UserPreferenceException("preference not found for this user");
	}
	
	
	public Map<String, User> getInternalUserMap() {
		return internalUserMap;
	}

	public List<User> getAllUsers() {
		List<User> lUsers = internalUserMap.values().stream().collect(Collectors.toList());
		//System.out.println(lUsers.size());
		return lUsers;
	}
	
	public void addUser(User user) {
		if(!internalUserMap.containsKey(user.getUserName())) {
			internalUserMap.put(user.getUserName(), user);
		}
	}
	
	public List<Provider> getTripDeals(User user) {
		int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
		 //user = getUser(user.getUserName());
		List<Provider> providers = tripPricerProxy.getPrice(tripPricerApiKey, user.getUserId().toString(), user.getUserPreferences().getNumberOfAdults(), 
				user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);
		user.setTripDeals(providers);
		return providers;
	}
	
	public VisitedLocation trackUserLocation(User user) {
		VisitedLocation visitedLocation = gpsUtilProxy.getUserLocation(user.getUserId());
		user.addToVisitedLocations(visitedLocation);
		rewardsService.calculateRewards(user);
		return visitedLocation;
	}

	public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation) {
		List<Attraction> nearbyAttractions = new ArrayList<>();
		for(Attraction attraction : gpsUtilProxy.getAttractions()) {
			if(rewardsService.nearAttraction(visitedLocation, attraction)) {
				
				nearbyAttractions.add(attraction);
				/*if(nearbyAttractions.size()==5) {
					break;
				}*/
			}
		}
		
		return nearbyAttractions;
	}

	
	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() { 
		      public void run() {
		        tracker.stopTracking();
		      } 
		    }); 
	}
	
	/**********************************************************************************
	 * 
	 * Methods Below: For Internal Testing
	 * 
	 **********************************************************************************/
	private static final String tripPricerApiKey = "test-server-api-key";
	// Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
	private final Map<String, User> internalUserMap = new HashMap<>();
	private void initializeInternalUsers() {
		IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
			String userName = "internalUser" + i;
			//System.out.println(userName);
			String phone = "000";
			String email = userName + "@tourGuide.com";
			User user = new User(UUID.randomUUID(), userName, phone, email);
			generateUserLocationHistory(user);
			
			internalUserMap.put(userName, user);
		});
		logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
	}
	
	private void generateUserLocationHistory(User user) {
		IntStream.range(0, 3).forEach(i-> {
			VisitedLocation visitedLocation = new VisitedLocation();
			visitedLocation.setUserId(user.getUserId());
			visitedLocation.setLocation(new Location(generateRandomLatitude(), generateRandomLongitude()));
			visitedLocation.setTimeVisited(getRandomTime());
			user.addToVisitedLocations(visitedLocation);
		});
	}
	
	private double generateRandomLongitude() {
		double leftLimit = -180;
	    double rightLimit = 180;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private double generateRandomLatitude() {
		double leftLimit = -85.05112878;
	    double rightLimit = 85.05112878;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private Date getRandomTime() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
	    return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}
	
}
