package com.tripPricer.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripPricer.TripPricerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TripPricerApplication.class)
@AutoConfigureMockMvc
public class TripPricerControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    ObjectMapper mapper;
	
	@Test
	public void getPrice() throws Exception {
		String apiKey = ("");
		UUID attractionId = UUID.randomUUID();
		Integer adults = 2;
		Integer children = 3;
		Integer nightsStay = 5 ;
		Integer rewardsPoints = 50;
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/getPrice")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.param("apiKey", apiKey.toString())
				.param("attractionId", attractionId.toString())
		        .param("adults", adults.toString())
		        .param("children", children.toString() )
		        .param("nightsStay", nightsStay.toString())
		        .param("rewardsPoints", rewardsPoints.toString());
		        
				MvcResult result = mockMvc
				.perform(mockRequest)
		        .andExpect(status().isOk())
		        .andExpect(content()
		        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		        .andReturn();
	}
}
