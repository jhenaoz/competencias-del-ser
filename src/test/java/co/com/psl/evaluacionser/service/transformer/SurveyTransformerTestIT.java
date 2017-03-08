package co.com.psl.evaluacionser.service.transformer;

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
import co.com.psl.evaluacionser.service.dto.BehaviorSurveyDto;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SurveyTransformerTestIT {

    @Autowired
    private SurveyTransformer surveyTransformer;

    @Test
    public void aptitudeSurveyTransformerTest() {
        List<BehaviorSurveyDto> behaviorsSurveyDto = new ArrayList<>();
        behaviorsSurveyDto.add(new BehaviorSurveyDto("1", 5));
        behaviorsSurveyDto.add(new BehaviorSurveyDto("2", 4));
        behaviorsSurveyDto.add(new BehaviorSurveyDto("3", 1));
        AptitudeSurveyDto aptitudeSurveyDto = new AptitudeSurveyDto("1", "Siempre abierto a cambios", behaviorsSurveyDto);
        AptitudeSurvey aptitudeSurvey = surveyTransformer.aptitudeSurveyTransformer(aptitudeSurveyDto);
        assertEquals("Apertura", aptitudeSurvey.getAptitude().getEs());
    }

    @Test
    public void surveyTransformerTest() {
        List<BehaviorSurveyDto> behaviorsSurveyDto = new ArrayList<>();
        behaviorsSurveyDto.add(new BehaviorSurveyDto("1", 5));
        behaviorsSurveyDto.add(new BehaviorSurveyDto("2", 4));
        behaviorsSurveyDto.add(new BehaviorSurveyDto("3", 1));
        AptitudeSurveyDto aptitudeSurveyDto = new AptitudeSurveyDto("1", "Siempre abierto a cambios", behaviorsSurveyDto);
        List<AptitudeSurveyDto> aptitudeSurveyDtos = new ArrayList<AptitudeSurveyDto>();
        aptitudeSurveyDtos.add(aptitudeSurveyDto);
        SurveyDto surveyDto = new SurveyDto("Juan evaluator", "Juana evaluated", "Team", aptitudeSurveyDtos);
        Survey survey = surveyTransformer.transformer(surveyDto);
        assertEquals("Juan evaluator", survey.getEvaluator());
        assertEquals("Siempre abierto a cambios", survey.getAptitudes().get(0).getObservation());
        assertEquals(5, survey.getAptitudes().get(0).getBehaviors().get(0).getScore());
    }
}
