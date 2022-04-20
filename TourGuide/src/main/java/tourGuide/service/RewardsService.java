package tourGuide.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import tourGuide.GpsUtilProxy;
import tourGuide.RewardProxy;
import tourGuide.dto.UserAttractionResultDto;
import tourGuide.dto.UserFiveNearAttracDto;
import tourGuide.dto.UserLatLongDto;
import tourGuide.response.rest.Attraction;
import tourGuide.response.rest.Location;
import tourGuide.response.rest.VisitedLocation;
import tourGuide.user.Position;
import tourGuide.user.User;
import tourGuide.user.UserReward;

@Service
public class RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
    private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	private final GpsUtilProxy gpsUtilProxy;
	private final RewardProxy rewardProxy;
	
	public RewardsService(GpsUtilProxy gpsUtilProxy, RewardProxy rewardProxy) {
		this.gpsUtilProxy = gpsUtilProxy;
		this.rewardProxy = rewardProxy;
	}
	
	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}
	
	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}
	
	public void calculateRewards(User user) {
		List<VisitedLocation> userLocations = user.getVisitedLocations();
		List<Attraction> attractions = gpsUtilProxy.getAttractions();
		
		for(VisitedLocation visitedLocation : userLocations) {
			for(Attraction attraction : attractions) {
				if(user.getUserRewards().stream().filter(r -> r.attraction.attractionName.equals(attraction.attractionName)).count() == 0) {
					if(nearAttraction(visitedLocation, attraction)) {
						user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
					}
				}
			}
		}
	}
	
	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return getDistance(attraction, location) > attractionProximityRange ? false : true;
	}
	
	public boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		boolean dist = getDistance(attraction, visitedLocation.getLocation()) > proximityBuffer ? false : true;
		return dist;
	}
	
	private int getRewardPoints(Attraction attraction, User user) {
		return rewardProxy.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
	}
	
	private int getRewardPoints(UUID attractionId, UUID userId) {
		return rewardProxy.getAttractionRewardPoints(attractionId, userId);
	}
	
	public UserAttractionResultDto getDistanceBetweenUserAndAttraction(User user) {
		List<Attraction> attractions = gpsUtilProxy.getAttractions();
		List <Position> attractionPositionsFromUser = new ArrayList<>();  
		Attraction attractionFirst = attractions.get(0);
		VisitedLocation visitedLocation = new VisitedLocation();
		visitedLocation.setUserId(user.getUserId());
		visitedLocation.setLocation(attractionFirst);
		visitedLocation.setTimeVisited(new Date());
		user.addToVisitedLocations(visitedLocation);
		VisitedLocation lastLocation = user.getLastVisitedLocation();
		
		for (Attraction attraction : attractions) {
			int rewardPoints = getRewardPoints(attraction, user);
			Double distance = getDistance(lastLocation.getLocation(), attraction);
			
			Position attractionPositionFromUser = new Position();
			attractionPositionFromUser.setAttraction(attraction);
			attractionPositionFromUser.setDistanceFromUser(distance);
			attractionPositionFromUser.setRewardPoints(rewardPoints);
			attractionPositionsFromUser.add(attractionPositionFromUser);
		}
			Collections.sort(attractionPositionsFromUser, new Comparator<Position>(){
		    public int compare(Position P1, Position P2) {
		        return P1.getDistanceFromUser().compareTo(P2.getDistanceFromUser());
		    }
		}); 
			List<Position> fiveNearAttractions = attractionPositionsFromUser.stream().limit(5).collect(Collectors.toList());
			UserAttractionResultDto userAttractionResultDto = new UserAttractionResultDto();
			UserLatLongDto userLatLongDto = new UserLatLongDto();
			userAttractionResultDto.setAttractions(fiveNearAttractions);
			userLatLongDto.setUserLong(lastLocation.getLocation().longitude);
			userLatLongDto.setUserLat(lastLocation.getLocation().latitude);
			userAttractionResultDto.setUserLocation(userLatLongDto);

			return userAttractionResultDto;
	}
	
	public List<UserFiveNearAttracDto> getFiveNearAttractions(User user) {
		List<Attraction> lAttraction = gpsUtilProxy.getAttractions();
		List<UserFiveNearAttracDto> lUserFiveNearAttracDto = new ArrayList<UserFiveNearAttracDto>();
		for (Attraction attraction : lAttraction) {
			UserFiveNearAttracDto userFiveNearAttracDto = new UserFiveNearAttracDto();
			VisitedLocation lastLocation = user.getLastVisitedLocation();
			Double distanceFromUser = getDistance(lastLocation.getLocation(), attraction);
			double attractionLatitude = attraction.latitude;
			double attractionLongitude = attraction.longitude;
			
			int rewardPoints = getRewardPoints(attraction, user);
			
			userFiveNearAttracDto.setAttractionName(attraction.attractionName);
			
			userFiveNearAttracDto.setAttractionLatitude(attractionLatitude);
			userFiveNearAttracDto.setAttractionLongitude(attractionLongitude);
			userFiveNearAttracDto.setUserLatitude(lastLocation.getLocation().latitude);
			userFiveNearAttracDto.setUserLongitude(lastLocation.getLocation().longitude);
			userFiveNearAttracDto.setDistanceFromUser(distanceFromUser);
			userFiveNearAttracDto.setRewardPoints(rewardPoints);
			
			lUserFiveNearAttracDto.add(userFiveNearAttracDto);
		}
		
		Collections.sort(lUserFiveNearAttracDto, new Comparator<UserFiveNearAttracDto>(){
		    public int compare(UserFiveNearAttracDto P1, UserFiveNearAttracDto P2) {
		        return P1.getDistanceFromUser().compareTo(P2.getDistanceFromUser());
		    }
		}); 
		
		return lUserFiveNearAttracDto.stream().limit(5).collect(Collectors.toList());
	}
	
	public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
	}

}
