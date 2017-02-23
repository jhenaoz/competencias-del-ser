package co.com.psl.evaluacionser.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AptitudeSurveyTest {

    @Test
    public void AptitudeSurveyByConstructor() {
        AptitudeDto aptitudeDto = new AptitudeDto("1", "Openness", "Apertura");
        List<BehaviorSurvey> behaviors = new ArrayList<BehaviorSurvey>();
        Behavior behavior = new Behavior("1", "Accepts review", "Acepta retroalimentacion");
        behaviors.add(new BehaviorSurvey(behavior, "5"));
        AptitudeSurvey aptitudeSurvey = new AptitudeSurvey(aptitudeDto, "A really good friend", behaviors);
        assertEquals("1", aptitudeSurvey.getAptitude().getId());
        assertEquals("1", aptitudeSurvey.getBehaviors().get(0).getBehavior().getId());
        assertEquals("A really good friend", aptitudeSurvey.getObservation());
    }

    @Test
    public void AptitudeSurveyBySetter() {
        AptitudeSurvey aptitudeSurvey = new AptitudeSurvey();
        aptitudeSurvey.setAptitude(new AptitudeDto("1", "Openness", "Apertura"));
        List<BehaviorSurvey> behaviors = new ArrayList<BehaviorSurvey>();
        Behavior behavior = new Behavior("1", "Accepts review", "Acepta retroalimentacion");
        behaviors.add(new BehaviorSurvey(behavior, "5"));
        aptitudeSurvey.setBehaviors(behaviors);
        aptitudeSurvey.setObservation("A really good friend");
        assertEquals("1", aptitudeSurvey.getAptitude().getId());
        assertEquals("1", aptitudeSurvey.getBehaviors().get(0).getBehavior().getId());
        assertEquals("A really good friend", aptitudeSurvey.getObservation());
    }
    
    @Test
    public void NullObject() {
        AptitudeSurvey aptitudeSurvey = new AptitudeSurvey();
        assertNull(aptitudeSurvey.getAptitude());
        assertNull(aptitudeSurvey.getBehaviors());
        assertNull(aptitudeSurvey.getObservation());
    }
}
