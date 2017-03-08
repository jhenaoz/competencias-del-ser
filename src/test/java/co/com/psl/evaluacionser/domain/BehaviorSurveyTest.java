package co.com.psl.evaluacionser.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BehaviorSurveyTest {

    @Test
    public void behaviorIdFromBehaviorSurvey() {
        Behavior behavior = new Behavior("1", "Accepts review", "Acepta retroalimentacion");
        BehaviorSurvey behaviourSurvey = new BehaviorSurvey(behavior, 2);
        assertEquals("1", behaviourSurvey.getBehavior().getId());
        assertEquals(2, behaviourSurvey.getScore());
    }

    @Test
    public void constructBySetter() {
        Behavior behavior = new Behavior("1", "Accepts review", "Acepta retroalimentacion");
        BehaviorSurvey behaviourSurvey = new BehaviorSurvey();
        behaviourSurvey.setBehavior(behavior);
        behaviourSurvey.setScore(2);
        assertEquals("1", behaviourSurvey.getBehavior().getId());
        assertEquals(2, behaviourSurvey.getScore());
    }

}
