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
public class AptitudeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllAptitudes() throws Exception {
        mockMvc.perform(get("/aptitude"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].es").exists())
                .andExpect(jsonPath("$[0].en").exists());
    }

    @Test
    public void getSingleAptitude() throws Exception {
        mockMvc.perform(get("/aptitude/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.es").exists())
                .andExpect(jsonPath("$.en").exists());
    }

    @Test
    public void getBehaviorsOfAnAptitude() throws Exception {
        mockMvc.perform(get("/aptitude/1/behavior"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].es").exists())
                .andExpect(jsonPath("$[0].en").exists());
    }

    @Test
    public void getSingleBehavior() throws Exception {
        mockMvc.perform(get("/aptitude/1/behavior/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.es").exists())
                .andExpect(jsonPath("$.en").exists());
    }

    @Test
    public void getInvalidAptitudeShouldReturn404() throws Exception {
        mockMvc.perform(get("/aptitude/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getInvalidBehaviorShouldReturn404() throws Exception {
        mockMvc.perform(get("/aptitude/1/behavior/99"))
                .andExpect(status().isNotFound());
    }

}
