package co.com.psl.evaluacionser.service.transformer;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.persistence.AptitudeRepository;
import co.com.psl.evaluacionser.service.dto.AptitudeSurveyDto;
import co.com.psl.evaluacionser.service.dto.BehaviorSurveyDto;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SurveyTransformerTest {

    @Mock
    private AptitudeRepository mockAptitudeRepository;
    private SurveyTransformer surveyTransformer;

    @Before
    public void setup() {
        List<Behavior> behaviorList = new ArrayList<>();
        behaviorList.add(new Behavior(1, "Acepta retroalimentacion", "Accepts review"));
        behaviorList.add(new Behavior(2, "Juega en equipo", "Play in team"));
        behaviorList.add(new Behavior(3, "Buena persona", "Good person"));

        Aptitude aptitude = new Aptitude(1L, "Apertura", "Openness", behaviorList);
        when(mockAptitudeRepository.findById("1")).thenReturn(aptitude);

        AptitudeTransformer aptitudeTransformer = new AptitudeTransformer();
        surveyTransformer = new SurveyTransformer(mockAptitudeRepository, aptitudeTransformer);
    }

    @Test
    public void surveyTransformerTest() {
        List<BehaviorSurveyDto> behaviorsSurveyDto = new ArrayList<>();
        behaviorsSurveyDto.add(new BehaviorSurveyDto(1, 5));
        behaviorsSurveyDto.add(new BehaviorSurveyDto(2, 4));
        behaviorsSurveyDto.add(new BehaviorSurveyDto(3, 1));
        AptitudeSurveyDto aptitudeSurveyDto =
                new AptitudeSurveyDto("1", "Siempre abierto a cambios", behaviorsSurveyDto);
        List<AptitudeSurveyDto> aptitudeSurveyDtos = new ArrayList<>();
        aptitudeSurveyDtos.add(aptitudeSurveyDto);
        SurveyDto surveyDto = new SurveyDto("Juan evaluator", "Juana evaluated", "Team", aptitudeSurveyDtos);
        Survey survey = surveyTransformer.transformer(surveyDto);
        assertEquals("Juan evaluator", survey.getEvaluator());
        assertEquals("Siempre abierto a cambios", survey.getAptitudes().get(0).getObservation());
        assertEquals(5, survey.getAptitudes().get(0).getBehaviors().get(0).getScore());
    }
}
