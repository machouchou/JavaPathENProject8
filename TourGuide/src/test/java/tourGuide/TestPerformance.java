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
