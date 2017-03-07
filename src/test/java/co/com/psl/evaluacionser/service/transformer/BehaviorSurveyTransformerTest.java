package co.com.psl.evaluacionser.service.transformer;

import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorSurvey;
import co.com.psl.evaluacionser.service.dto.BehaviorSurveyDto;
import co.com.psl.evaluacionser.service.transformer.SurveyTransformer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BehaviorSurveyTransformerTest {

    private SurveyTransformer surveyTransformer = new SurveyTransformer();

    @Test
    public void behaviorsSurveyTransformerTest() {
        List<Behavior> behaviors = new ArrayList<>();
        behaviors.add(new Behavior("1", "Acepta retroalimentacion", "Accepts review"));
        behaviors.add(new Behavior("2", "Juega en equipo", "Play in team"));
        behaviors.add(new Behavior("3", "Buena persona", "Good person"));
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
