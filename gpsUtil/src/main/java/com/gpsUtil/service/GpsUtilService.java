package com.gpsUtil.service;

import java.util.ArrayList;
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
	private final GpsUtil gpsUtil = new GpsUtil();
	
	public List<Attraction> getAttractions() {

        logger.debug("Attractions received!");

        return gpsUtil.getAttractions();
    }

    public VisitedLocation getUserLocation(UUID userUuid) {

        logger.debug("User's location received!");

        VisitedLocation visitedLocation = gpsUtil.getUserLocation(userUuid);

        return visitedLocation;
    }
	
}
