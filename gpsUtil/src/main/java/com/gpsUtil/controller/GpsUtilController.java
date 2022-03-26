package com.gpsUtil.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gpsUtil.service.GpsUtilService;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@RestController
public class GpsUtilController {
	
	private Logger logger = LoggerFactory.getLogger(GpsUtilController.class);
	
	@Autowired
    GpsUtilService gpsUtilService;

    @GetMapping(value="/attractions")
    public List<Attraction> getAttractions() {

        return gpsUtilService.getAttractions();
    }

    @GetMapping(value="/userLocation")
    public VisitedLocation getUserLocation (@RequestParam UUID userUuid) {

        logger.debug("USER UUID: " + userUuid);
        return gpsUtilService.getUserLocation(userUuid);
    }

}
