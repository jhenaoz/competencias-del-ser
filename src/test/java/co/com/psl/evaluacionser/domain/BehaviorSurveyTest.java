package co.com.psl.evaluacionser.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class BehaviorSurveyTest {

    @Test
    public void BehaviorIdFromBehaviorSurvey() {
        Behavior behavior = new Behavior("1", "Accepts review", "Acepta retroalimentacion");
        BehaviorSurvey behaviourSurvey = new BehaviorSurvey(behavior, 2);
        assertEquals("1", behaviourSurvey.getBehavior().getId());
        assertEquals(2, behaviourSurvey.getScore());
    }

    @Test
    public void EmptyConstructorShouldBeNull() {
        BehaviorSurvey behaviourSurvey = new BehaviorSurvey();
        assertNull(behaviourSurvey.getBehavior());
    }

    @Test
    public void construcBySetter() {
        Behavior behavior = new Behavior("1", "Accepts review", "Acepta retroalimentacion");
        BehaviorSurvey behaviourSurvey = new BehaviorSurvey();
        behaviourSurvey.setBehavior(behavior);
        behaviourSurvey.setScore(2);
        assertEquals("1", behaviourSurvey.getBehavior().getId());
        assertEquals(2, behaviourSurvey.getScore());
    }

}
