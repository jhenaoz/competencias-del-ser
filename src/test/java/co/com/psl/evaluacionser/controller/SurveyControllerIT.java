package co.com.psl.evaluacionser.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SurveyControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void recentSurveysShouldReturnBoolean() throws Exception {
        mockMvc.perform(get("/api/survey/recentsurvey")
                .param("evaluated", "A person")
                .param("evaluator", "A person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isBoolean());
    }

}
