package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.AptitudeSurvey;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorSurvey;
import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.persistence.SurveyRepository;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import co.com.psl.evaluacionser.service.dto.AptitudeSurveyDto;
import co.com.psl.evaluacionser.service.dto.BehaviorSurveyDto;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import co.com.psl.evaluacionser.service.transformer.SurveyTransformer;
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
public class SurveyServiceTest {

    @Mock
    SurveyRepository mockSurveyRepository;

    @Mock
    SurveyTransformer mockSurveyTransformer;

    @Mock
    EmailService emailService;

    SurveyService surveyService;

    SurveyDto surveyDto = new SurveyDto();
    Survey survey = new Survey();
    List<Survey> surveys = new ArrayList<>();

    @Before
    public void setUp() {
        BehaviorSurveyDto behaviorSurveyDto = new BehaviorSurveyDto();
        behaviorSurveyDto.setBehaviorId(1);
        behaviorSurveyDto.setScore(5);

        BehaviorSurveyDto behaviorSurveyDto1 = new BehaviorSurveyDto();
        behaviorSurveyDto1.setBehaviorId(2);
        behaviorSurveyDto1.setScore(3);

        List<BehaviorSurveyDto> behaviorSurveyDtos = new ArrayList<>();
        behaviorSurveyDtos.add(behaviorSurveyDto);
        behaviorSurveyDtos.add(behaviorSurveyDto1);

        AptitudeSurveyDto aptitudeSurveyDto = new AptitudeSurveyDto();
        aptitudeSurveyDto.setAptitudeId("1");
        aptitudeSurveyDto.setObservation("observation");
        aptitudeSurveyDto.setBehaviors(behaviorSurveyDtos);

        List<AptitudeSurveyDto> aptitudes = new ArrayList<>();
        aptitudes.add(aptitudeSurveyDto);

        surveyDto.setAptitudes(aptitudes);
        surveyDto.setEvaluated("jhon doe");
        surveyDto.setEvaluator("jane doe");
        surveyDto.setRole("teanmmate");


        Behavior behavior = new Behavior();
        behavior.setEn("They accept suggestions regardless of who gives them");
        behavior.setEs("Acepta sugerencias sin distincion de quien las de");
        behavior.setId(1);

        Behavior behavior1 = new Behavior();
        behavior1.setEn("They recognize their mistakes");
        behavior1.setEs("Reconoce sus errore");
        behavior1.setId(2);

        BehaviorSurvey behaviorSurvey = new BehaviorSurvey();
        behaviorSurvey.setBehavior(behavior);
        behaviorSurvey.setScore(5);

        BehaviorSurvey behaviorSurvey1 = new BehaviorSurvey();
        behaviorSurvey1.setScore(3);
        behaviorSurvey1.setBehavior(behavior1);

        List<BehaviorSurvey> behaviorSurveys = new ArrayList<>();
        behaviorSurveys.add(behaviorSurvey);
        behaviorSurveys.add(behaviorSurvey1);

        AptitudeDto aptitudeDto = new AptitudeDto();
        aptitudeDto.setId("1");
        aptitudeDto.setEs("Apertura");
        aptitudeDto.setEn("Openness");

        AptitudeSurvey aptitudeSurvey = new AptitudeSurvey();
        aptitudeSurvey.setBehaviors(behaviorSurveys);
        aptitudeSurvey.setObservation("observation");
        aptitudeSurvey.setAptitude(aptitudeDto);


        List<AptitudeSurvey> aptitudeSurveys = new ArrayList<>();
        aptitudeSurveys.add(aptitudeSurvey);

        survey.setEvaluated("evaluated");
        survey.setEvaluator("evaluator");
        survey.setTimestamp("2017-03-06");
        survey.setRole("teammate");
        survey.setAptitudes(aptitudeSurveys);

        surveys.add(survey);

        when(mockSurveyTransformer.aptitudeSurveyTransformer(aptitudeSurveyDto)).thenReturn(aptitudeSurvey);
        when(mockSurveyTransformer.transformer(surveyDto)).thenReturn(survey);


        when(mockSurveyRepository.existsRecentSurvey("evaluated", "evaluator",null)).thenReturn(true);
        when(mockSurveyRepository.existsRecentSurvey("not evaluated", "evaluator",null)).thenReturn(false);
        when(mockSurveyRepository.findUserSurveys("evaluated", "2017-03-06", "2017-03-06")).thenReturn(surveys);
        when(mockSurveyRepository.findUserSurveys("evaluated", null, null)).thenReturn(surveys);
        when(mockSurveyRepository.findUserSurveys("evaluated", "invalid date", "invalid date")).thenReturn(null);
        when(mockSurveyRepository.saveSurvey(survey)).thenReturn(survey);

        surveyService = new SurveyService(mockSurveyRepository, mockSurveyTransformer, emailService);
    }

    @Test
    public void surveyShouldBeTransformedAndSaved() {
        Survey survey1 = surveyService.saveSurvey(surveyDto);
        assertEquals(survey1, survey);
    }

    @Test
    public void userSurveyShouldBeFound() {
        List<Survey> evaluated = surveyService.findUserSurveys("evaluated", "2017-03-06", "2017-03-06");
        assertEquals(survey, evaluated.get(0));
    }

    @Test
    public void userSurveyWithNullDatesShouldBeFound() {
        List<Survey> evaluated = surveyService.findUserSurveys("evaluated", null, null);
        assertEquals(survey, evaluated.get(0));
    }

    @Test
    public void invalidSurveyShouldntBeFound() {
        List<Survey> evaluated = surveyService.findUserSurveys("evaluated", "invalid date", "invalid date");
        assertEquals(null, evaluated);
    }

    @Test
    public void recentSurveyShouldBeFound() {
        boolean surveyWasFound = surveyService.existsRecentSurvey("evaluated", "evaluator",null);
        assertEquals(true, surveyWasFound);
    }

    @Test
    public void nonexistingSurveyShouldntBeFound() {
        boolean surveyWasFound = surveyService.existsRecentSurvey("not evaluated", "evaluator",null);
        assertEquals(false, surveyWasFound);
    }

}
