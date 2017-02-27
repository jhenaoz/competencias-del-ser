package co.com.psl.evaluacionser.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import co.com.psl.evaluacionser.service.transformer.SurveyTransformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.psl.evaluacionser.domain.AptitudeSurvey;
import co.com.psl.evaluacionser.service.dto.AptitudeSurveyDto;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorSurvey;
import co.com.psl.evaluacionser.service.dto.BehaviorSurveyDto;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SurveyTransformerTestIT {

    @Autowired
    private SurveyTransformer surveyTransformer = new SurveyTransformer();

    @Test
    public void BehaviorsSurveyTransformerTest() {
        List<Behavior> behaviors = new ArrayList<>();
        behaviors.add(new Behavior("1", "Accepts review", "Acepta retroalimentacion"));
        behaviors.add(new Behavior("2", "Play in team", "Juega en equipo"));
        behaviors.add(new Behavior("3", "Good person", "Buena persona"));
        List<BehaviorSurveyDto> behaviorsSurveyDto = new ArrayList<>();
        behaviorsSurveyDto.add(new BehaviorSurveyDto("1", 5));
        behaviorsSurveyDto.add(new BehaviorSurveyDto("2", 4));
        behaviorsSurveyDto.add(new BehaviorSurveyDto("3", 1));
        List<BehaviorSurvey> behaviorSurvey = surveyTransformer.BehaviorsSurveyTransformer(behaviors,
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
    
    @Test
    public void AptitudeSurveyTransformerTest() {
        List<BehaviorSurveyDto> behaviorsSurveyDto = new ArrayList<>();
        behaviorsSurveyDto.add(new BehaviorSurveyDto("1", 5));
        behaviorsSurveyDto.add(new BehaviorSurveyDto("2", 4));
        behaviorsSurveyDto.add(new BehaviorSurveyDto("3", 1));
        AptitudeSurveyDto aptitudeSurveyDto = new AptitudeSurveyDto("1", "Siempre abierto a cambios", behaviorsSurveyDto);
        AptitudeSurvey aptitudeSurvey = surveyTransformer.AptitudeSurveyTransformer(aptitudeSurveyDto);
        assertEquals("Apertura", aptitudeSurvey.getAptitude().getEs());
    }

    @Test
    public void SurveyTransformerTest() {
        List<BehaviorSurveyDto> behaviorsSurveyDto = new ArrayList<>();
        behaviorsSurveyDto.add(new BehaviorSurveyDto("1", 5));
        behaviorsSurveyDto.add(new BehaviorSurveyDto("2", 4));
        behaviorsSurveyDto.add(new BehaviorSurveyDto("3", 1));
        AptitudeSurveyDto aptitudeSurveyDto = new AptitudeSurveyDto("1", "Siempre abierto a cambios", behaviorsSurveyDto);
        List<AptitudeSurveyDto> aptitudeSurveyDtos = new ArrayList<AptitudeSurveyDto>();
        aptitudeSurveyDtos.add(aptitudeSurveyDto);
        SurveyDto surveyDto = new SurveyDto("Juan evaluator", "Juana evaluated", "Team", aptitudeSurveyDtos);
        Survey survey = surveyTransformer.Transformer(surveyDto);
        assertEquals("Juan evaluator", survey.getEvaluator());
        assertEquals("Siempre abierto a cambios", survey.getAptitudes().get(0).getObservation());
        assertEquals(5, survey.getAptitudes().get(0).getBehaviors().get(0).getScore());
    }
}
