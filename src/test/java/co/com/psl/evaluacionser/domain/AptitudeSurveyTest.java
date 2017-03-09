package co.com.psl.evaluacionser.domain;

import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AptitudeSurveyTest {

    @Test
    public void aptitudeSurveyByConstructor() {
        AptitudeDto aptitudeDto = new AptitudeDto("1", "Openness", "Apertura");
        List<BehaviorSurvey> behaviors = new ArrayList<>();
        Behavior behavior = new Behavior(1, "Accepts review", "Acepta retroalimentacion");
        behaviors.add(new BehaviorSurvey(behavior, 5));
        AptitudeSurvey aptitudeSurvey = new AptitudeSurvey(aptitudeDto, "A really good friend", behaviors);
        assertEquals("1", aptitudeSurvey.getAptitude().getId());
        assertEquals(1, aptitudeSurvey.getBehaviors().get(0).getBehavior().getId());
        assertEquals("A really good friend", aptitudeSurvey.getObservation());
    }

    @Test
    public void aptitudeSurveyBySetter() {
        AptitudeSurvey aptitudeSurvey = new AptitudeSurvey();
        aptitudeSurvey.setAptitude(new AptitudeDto("1", "Openness", "Apertura"));
        List<BehaviorSurvey> behaviors = new ArrayList<>();
        Behavior behavior = new Behavior(1, "Accepts review", "Acepta retroalimentacion");
        behaviors.add(new BehaviorSurvey(behavior, 5));
        aptitudeSurvey.setBehaviors(behaviors);
        aptitudeSurvey.setObservation("A really good friend");
        assertEquals("1", aptitudeSurvey.getAptitude().getId());
        assertEquals(1, aptitudeSurvey.getBehaviors().get(0).getBehavior().getId());
        assertEquals("A really good friend", aptitudeSurvey.getObservation());
    }
}
