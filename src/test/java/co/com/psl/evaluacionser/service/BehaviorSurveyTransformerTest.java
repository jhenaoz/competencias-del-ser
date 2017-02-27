package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.AptitudeSurvey;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorSurvey;
import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.service.dto.AptitudeSurveyDto;
import co.com.psl.evaluacionser.service.dto.BehaviorSurveyDto;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import co.com.psl.evaluacionser.service.transformer.SurveyTransformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BehaviorSurveyTransformerTest {

    @Autowired
    private SurveyTransformer surveyTransformer = new SurveyTransformer();

    @Test
    public void behaviorsSurveyTransformerTest() {
        List<Behavior> behaviors = new ArrayList<>();
        behaviors.add(new Behavior("1", "Accepts review", "Acepta retroalimentacion"));
        behaviors.add(new Behavior("2", "Play in team", "Juega en equipo"));
        behaviors.add(new Behavior("3", "Good person", "Buena persona"));
        List<BehaviorSurveyDto> behaviorsSurveyDto = new ArrayList<>();
        behaviorsSurveyDto.add(new BehaviorSurveyDto("1", 5));
        behaviorsSurveyDto.add(new BehaviorSurveyDto("2", 4));
        behaviorsSurveyDto.add(new BehaviorSurveyDto("3", 1));
        List<BehaviorSurvey> behaviorSurvey = surveyTransformer.behaviorsSurveyTransformer(behaviors,
                behaviorsSurveyDto);
        assertEquals("1", behaviorSurvey.get(0).getBehavior().getId());
        assertEquals("Acepta retroalimentacion", behaviorSurvey.get(0).getBehavior().getEs());
        assertEquals(5, behaviorSurvey.get(0).getScore());
        assertEquals("2", behaviorSurvey.get(1).getBehavior().getId());
        assertEquals("Juega en equipo", behaviorSurvey.get(1).getBehavior().getEs());
        assertEquals(4, behaviorSurvey.get(1).getScore());
        assertEquals("3", behaviorSurvey.get(2).getBehavior().getId());
        assertEquals("Buena persona", behaviorSurvey.get(2).getBehavior().getEs());
        assertEquals(1, behaviorSurvey.get(2).getScore());
    }
}
