package com.reward.test;

import java.util.UUID;
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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reward.RewardApplication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = RewardApplication.class)
@AutoConfigureMockMvc
public class RewardControllerTest {
	
	@Autowired
    MockMvc mockMvc;
	
	@Autowired
    ObjectMapper mapper;

	@Test
	public void getAttractionRewardPoints() throws Exception {
		
		UUID attractionId = UUID.randomUUID();
		UUID userId = UUID.randomUUID();

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/attractionRewardPoints")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON) 
		.param("attractionId", attractionId.toString())
        .param("userId", userId.toString());
		MvcResult result = mockMvc
		.perform(mockRequest)
        .andExpect(status().isOk())
        .andExpect(content()
        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andReturn();
		//String content = result.getResponse().getContentAsString();
		//System.out.println("Pour test");

        //.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));

	}
	
}
