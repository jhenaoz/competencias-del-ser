package co.com.psl.evaluacionser.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SurveyControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUserSurveys() throws Exception {
        mockMvc.perform(get("/api/survey/report")
                .param("user", "A user name")
                .param("startdate", "2017-1-30")
                .param("enddate", "2017-2-28"))
                .andExpect(status().isOk());
    }

    @Test
    public void getSurveysWithStartDateNull() throws Exception {
        mockMvc.perform(get("/api/survey/report")
                .param("user", "A user name")
                .param("enddate", "2017-2-28"))
                .andExpect(status().isOk());
    }

    @Test
    public void getSurveysWithEndDateNull() throws Exception {
        mockMvc.perform(get("/api/survey/report")
                .param("user", "A user name")
                .param("startdate", "2017-1-30"))
                .andExpect(status().isOk());
    }

    @Test
    public void getSurveysWithBothDatesNull() throws Exception {
        mockMvc.perform(get("/api/survey/report")
                .param("user", "A user name"))
                .andExpect(status().isOk());
    }

    @Test
    public void invalidDateRangeShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/survey/report")
                .param("user", "A user name")
                .param("startdate", "2017-3-30")
                .param("enddate", "2017-2-28"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void badDateFormatShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/survey/report")
                .param("user", "A user name")
                .param("startdate", "2017/1/30")
                .param("enddate", "2017-2-28"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void recentSurveysShouldReturnBoolean() throws Exception {
        mockMvc.perform(get("/api/survey/recentsurvey")
                .param("evaluated", "A person")
                .param("evaluator", "A person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isBoolean());
    }

}
