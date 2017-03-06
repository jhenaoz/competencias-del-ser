package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.AptitudeSurvey;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorSurvey;
import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.service.SurveyService;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SurveyControllerTest {

    @Mock
    private SurveyService mockSurveyService;

    private SurveyController surveyController;

    @Before
    public void setUp() {
        AptitudeDto aptitudeDto = new AptitudeDto("1", "Openness", "Apertura");
        List<BehaviorSurvey> behaviors = new ArrayList<>();
        Behavior behavior = new Behavior("1", "Acepta retroalimentacion", "Accepts review");
        behaviors.add(new BehaviorSurvey(behavior, 5));
        AptitudeSurvey aptitudeSurvey = new AptitudeSurvey(aptitudeDto, "A really good friend", behaviors);
        ArrayList<AptitudeSurvey> aptitudeSurveys = new ArrayList<>();
        aptitudeSurveys.add(aptitudeSurvey);
        Survey survey = new Survey("Juan Perez", "Jhon Doe", "Team", "1998-02-02", aptitudeSurveys);
        SurveyDto surveyDto = new SurveyDto("Juan Perez", "Jhon Doe", "Team", null);
        when(mockSurveyService.saveSurvey(surveyDto)).thenReturn(survey);

        List<Survey> surveyList = new ArrayList<>();
        surveyList.add(survey);
        when(mockSurveyService.findUserSurveys("Jhon Doe", "1998-02-01", "1998-02-08")).thenReturn(surveyList);

        when(mockSurveyService.existsRecentSurvey("Jhon Doe", "Juan Perez")).thenReturn(true);

        surveyController = new SurveyController(mockSurveyService);
    }

    @Test
    public void getSurveyNotEmptyShouldReturnsStatusOk() {
        ResponseEntity<List<Survey>> result = surveyController.getSurveys("Jhon Doe", "1998-02-01", "1998-02-08");
        assertEquals("OK", result.getStatusCode().name());
    }

    @Test
    public void getSurveyNullShouldReturnsStatusBadRequest() {
        when(mockSurveyService.findUserSurveys("Jhon Doe", "1998-02-01", "1998-02-08")).thenReturn(null);
        ResponseEntity<List<Survey>> result = surveyController.getSurveys("Jhon Doe", "1998-02-01", "1998-02-08");
        assertEquals("BAD_REQUEST", result.getStatusCode().name());
    }

    @Test
    public void getSurveyEmptyUserShouldReturnsStatusBadRequest() {
        List<Survey> surveyList = new ArrayList<>();
        when(mockSurveyService.findUserSurveys("", "1998-02-01", "1998-02-08")).thenReturn(surveyList);
        ResponseEntity<List<Survey>> result = surveyController.getSurveys("", "1998-02-01", "1998-02-08");
        assertEquals("BAD_REQUEST", result.getStatusCode().name());
    }

    @Test
    public void saveSurveyShouldReturnsStatusAccepted() {
        SurveyDto surveyDto = new SurveyDto("Juan Perez", "Jhon Doe", "Team", null);
        ResponseEntity<Survey> result = surveyController.saveSurvey(surveyDto);
        assertEquals("ACCEPTED", result.getStatusCode().name());
    }

    @Test
    public void existRecentSurvey() {
        ResponseEntity<Boolean> result= surveyController.existsRecentSurvey("Jhon Doe", "Juan Perez");
        assertEquals("OK", result.getStatusCode().name());
    }
}