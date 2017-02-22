package co.com.psl.evaluacionser.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Ignore("integration tests aren't ready yet") public class PersonControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getPeopleIsNotNull() throws Exception{
		mockMvc.perform(get("/person"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getPeopleIsNotEmpty() throws Exception{
		mockMvc.perform(get("/person"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].name").exists());
	}
}
