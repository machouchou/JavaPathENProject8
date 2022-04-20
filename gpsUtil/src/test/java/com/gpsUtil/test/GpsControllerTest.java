package com.gpsUtil.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Executors;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpsUtil.GpsUtilApplication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GpsUtilApplication.class)
@AutoConfigureMockMvc
public class GpsControllerTest {
	@Autowired
    MockMvc mockMvc;
	
	@Autowired
    ObjectMapper mapper;
	
	@Before
	  public void setUp() {
	    Locale.setDefault(new Locale("en", "US", "WIN"));
	}

	@Test
	public void getAttractions() throws Exception {
		
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/attractions")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON); 
		MvcResult result = mockMvc
		.perform(mockRequest)
        .andExpect(status().isOk())
        .andExpect(content()
        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println("Pour test");

        //.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
	}
	
	@Test
	public void getUserLocation() throws Exception {
		
		UUID userId = UUID.randomUUID();
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/userLocation")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.param("userId", userId.toString());
		MvcResult result = mockMvc
		.perform(mockRequest)
        .andExpect(status().isOk())
        .andExpect(content()
        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println("Pour test");
		
	}

}
