package com.gpsUtil;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GpsUtilApplication {

	public static void main(String[] args) {
		
		Locale.setDefault(new Locale("en", "US", "WIN"));
		SpringApplication.run(GpsUtilApplication.class, args);
	}

}
