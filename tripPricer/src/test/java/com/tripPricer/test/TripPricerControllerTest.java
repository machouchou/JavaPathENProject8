package com.tripPricer.test;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripPricer.dto.UserPreferenceDto;

public class TripPricerControllerTest {
	
	@Autowired
    MockMvc mockMvc;
	
	@Autowired
    ObjectMapper mapper;
	
	@Test
	public void getProvider() throws Exception {
		UserPreferenceDto userPreferenceDto = new UserPreferenceDto();
		userPreferenceDto.setTripPricerApiKey("");
		userPreferenceDto.setUserId(UUID.randomUUID());
		userPreferenceDto.setNumberOfAdults(2);
		userPreferenceDto.setNumberOfChildren(3);
		userPreferenceDto.setTripDuration(50);
		userPreferenceDto.setCumulatativeRewardPoints(20);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/getProvider")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(userPreferenceDto));
				mockMvc.perform(mockRequest)
		        .andExpect(status().isOk())
		        .andExpect(content()
		        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
	}
}
