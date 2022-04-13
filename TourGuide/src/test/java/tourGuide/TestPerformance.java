 package tourGuide;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestPerformance {
	
	@Autowired
	GpsUtilProxy gpsUtilProxy;
	@Autowired
	RewardProxy rewardProxy;
	@Autowired
	TripPricerProxy tripPricerProxy;
	
	 List<Attraction> attractions = new ArrayList();
	Executor executor;
	
	@Before
	  public void setUp() {
	    executor = Executors.newFixedThreadPool(100);
	    Locale.setDefault(new Locale("en", "US", "WIN"));
	   /* attractions.add(new Attraction("Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D));
	    attractions.add(new Attraction("Jackson Hole", "Jackson Hole", "WY", 43.582767D, -110.821999D));
	    attractions.add(new Attraction("Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	    attractions.add(new Attraction("Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D, -115.90065D));
	    attractions.add(new Attraction("Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D));
	    attractions.add(new Attraction("Hot Springs National Park", "Hot Springs", "AR", 34.52153D, -93.042267D));
	    attractions.add(new Attraction("Kartchner Caverns State Park", "Benson", "AZ", 31.837551D, -110.347382D));
	    attractions.add(new Attraction("Legend Valley", "Thornville", "OH", 39.937778D, -82.40667D));
	    attractions.add(new Attraction("Flowers Bakery of London", "Flowers Bakery of London", "KY", 37.131527D, -84.07486D));
	    attractions.add(new Attraction("McKinley Tower", "Anchorage", "AK", 61.218887D, -149.877502D));
	    attractions.add(new Attraction("Flatiron Building", "New York City", "NY", 40.741112D, -73.989723D));
	    attractions.add(new Attraction("Fallingwater", "Mill Run", "PA", 39.906113D, -79.468056D));
	    attractions.add(new Attraction("Union Station", "Washington D.C.", "CA", 38.897095D, -77.006332D));
	    attractions.add(new Attraction("Roger Dean Stadium", "Jupiter", "FL", 26.890959D, -80.116577D));
	    attractions.add(new Attraction("Texas Memorial Stadium", "Austin", "TX", 30.283682D, -97.732536D));
	    attractions.add(new Attraction("Bryant-Denny Stadium", "Tuscaloosa", "AL", 33.208973D, -87.550438D));
	    attractions.add(new Attraction("Tiger Stadium", "Baton Rouge", "LA", 30.412035D, -91.183815D));
	    attractions.add(new Attraction("Neyland Stadium", "Knoxville", "TN", 35.955013D, -83.925011D));
	    attractions.add(new Attraction("Kyle Field", "College Station", "TX", 30.61025D, -96.339844D));
	    attractions.add(new Attraction("San Diego Zoo", "San Diego", "CA", 32.735317D, -117.149048D));
	    attractions.add(new Attraction("Zoo Tampa at Lowry Park", "Tampa", "FL", 28.012804D, -82.469269D));
	    attractions.add(new Attraction("Franklin Park Zoo", "Boston", "MA", 42.302601D, -71.086731D));
	    attractions.add(new Attraction("El Paso Zoo", "El Paso", "TX", 31.769125D, -106.44487D));
	    attractions.add(new Attraction("Kansas City Zoo", "Kansas City", "MO", 39.007504D, -94.529625D));
	    attractions.add(new Attraction("Bronx Zoo", "Bronx", "NY", 40.852905D, -73.872971D));
	    attractions.add(new Attraction("Cinderella Castle", "Orlando", "FL", 28.419411D, -81.5812D));*/
	    
	  }
	
	/*
	 * A note on performance improvements:
	 *     
	 *     The number of users generated for the high volume tests can be easily adjusted via this method:
	 *     
	 *     		InternalTestHelper.setInternalUserNumber(100000);
	 *     
	 *     
	 *     These tests can be modified to suit new solutions, just as long as the performance metrics
	 *     at the end of the tests remains consistent. 
	 * 
	 *     These are performance metrics that we are trying to hit:
	 *     
	 *     highVolumeTrackLocation: 100,000 users within 15 minutes:
	 *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     *     highVolumeGetRewards: 100,000 users within 20 minutes:
	 *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 */
	
	
	@Test
	public void highVolumeTrackLocation() throws ExecutionException, InterruptedException {
		//Locale.setDefault(new Locale("en", "US", "WIN"));
		RewardsService rewardsService = new RewardsService(gpsUtilProxy, rewardProxy);
		// Users should be incremented up to 100,000, and test finishes within 15 minutes
		InternalTestHelper.setInternalUserNumber(100000);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilProxy, rewardsService, rewardProxy, tripPricerProxy);

		List<User> allUsers = new ArrayList<>();
		List<CompletableFuture> lCompletable = new ArrayList<>();
		allUsers = tourGuideService.getAllUsers();
		
	    StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for(User user : allUsers) {
			CompletableFuture completable = CompletableFuture.runAsync(
			    () -> {
			    tourGuideService.trackUserLocation(user);
			    }, executor);
			lCompletable.add(completable);
		}
		CompletableFuture.allOf(lCompletable.toArray(new CompletableFuture[lCompletable.size()])).join();  
		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	
	
	@Test
	public void highVolumeGetRewards() throws ExecutionException, InterruptedException {
		Locale.setDefault(new Locale("en", "US", "WIN"));
		//GpsUtilProxy gpsUtil = new GpsUtilProxy();
		RewardsService rewardsService = new RewardsService(gpsUtilProxy, rewardProxy);

		// Users should be incremented up to 100,000, and test finishes within 20 minutes
		InternalTestHelper.setInternalUserNumber(100000);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		TourGuideService tourGuideService = new TourGuideService(gpsUtilProxy, rewardsService, rewardProxy, tripPricerProxy);
		
	    Attraction attraction = gpsUtilProxy.getAttractions().get(0);
		List<User> allUsers = new ArrayList<>();
		List<CompletableFuture> lCompletable = new ArrayList<>();
		allUsers = tourGuideService.getAllUsers();
		allUsers.forEach(u -> {
			VisitedLocation visitedLocation = new VisitedLocation();
			visitedLocation.setUserId(u.getUserId());
			visitedLocation.setLocation(attraction);
			visitedLocation.setTimeVisited(new Date());
		u.addToVisitedLocations(visitedLocation);
		}); //new VisitedLocation(u.getUserId(), attraction, new Date())));}
	     
	    allUsers.forEach(u -> {
	    	CompletableFuture completable = CompletableFuture.runAsync(
	    	    () -> {
	    	    	rewardsService.calculateRewards(u);
	    	    }, executor);
	    	lCompletable.add(completable);
	    });

	    CompletableFuture.allOf(lCompletable.toArray(new CompletableFuture[lCompletable.size()])).join();  
	    		
		for(User user : allUsers) {
			assertTrue(user.getUserRewards().size() > 0);
		}
		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	
}
