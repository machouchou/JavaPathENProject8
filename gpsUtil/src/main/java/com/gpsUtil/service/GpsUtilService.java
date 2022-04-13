package com.gpsUtil.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@Service
public class GpsUtilService {
	
	private Logger logger = LoggerFactory.getLogger(GpsUtilService.class);
	
	private GpsUtil gpsUtil;
	
	public GpsUtilService(GpsUtil gpsUtil) {
		this.gpsUtil = gpsUtil;

	}
	
	public List<Attraction> getAttractions() {

        logger.debug("Attractions got!");

        return gpsUtil.getAttractions();
    }

    public VisitedLocation getUserLocation(UUID userId) {

        logger.debug("User's location get!");

        VisitedLocation visitedLocation = gpsUtil.getUserLocation(userId);

        return visitedLocation;
    }
	
}
