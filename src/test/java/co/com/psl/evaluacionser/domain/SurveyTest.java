package co.com.psl.evaluacionser.domain;

import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SurveyTest {

    @Test
    public void surveyByConstructor() {
        AptitudeDto aptitudeDto = new AptitudeDto("1", "Openness", "Apertura");
        List<BehaviorSurvey> behaviors = new ArrayList<>();
        Behavior behavior = new Behavior(1, "Accepts review", "Acepta retroalimentacion");
        behaviors.add(new BehaviorSurvey(behavior, 5));
        AptitudeSurvey aptitudeSurvey = new AptitudeSurvey(aptitudeDto, "A really good friend", behaviors);
        ArrayList<AptitudeSurvey> aptitudeSurveys = new ArrayList<>();
        aptitudeSurveys.add(aptitudeSurvey);
        Survey survey = new Survey("Juan Perez", "Jhon Doe", "Team", "1998-02-02", aptitudeSurveys);
        assertEquals("Juan Perez", survey.getEvaluator());
        assertEquals(1, survey.getAptitudes().get(0).getBehaviors().get(0).getBehavior().getId());
    }

    @Test
    public void surveyBySetter() {
        Survey survey = new Survey();
        survey.setEvaluator("Juan Perez");
        survey.setEvaluated("Jhon Doe");
        survey.setRole("Team");
        survey.setTimestamp("1998-02-02");
        assertEquals("Juan Perez", survey.getEvaluator());
        assertEquals("1998-02-02", survey.getTimestamp());
        assertNull(survey.getAptitudes());
    }
}
