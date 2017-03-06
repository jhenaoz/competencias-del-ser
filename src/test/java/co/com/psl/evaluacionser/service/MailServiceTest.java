package co.com.psl.evaluacionser.service;

import static org.junit.Assert.*;
import co.com.psl.evaluacionser.domain.*;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class MailServiceTest {

    private Survey survey;
    private MailService mailService = new MailService();

    @Before
    public void setUp() {
        Behavior behavior = new Behavior("5", "Acepta comentarios", "Accepts comments");
        List<BehaviorSurvey> behaviorSurveyList = new ArrayList<>();
        behaviorSurveyList.add(new BehaviorSurvey(behavior, 5));
        AptitudeSurvey aptitudeSurvey = new AptitudeSurvey( new AptitudeDto("1", "Apertura", "Openness"),
                "Siempre acepta comentarios", behaviorSurveyList);
        List<AptitudeSurvey> aptitudeSurveyList = new ArrayList<>();
        aptitudeSurveyList.add(aptitudeSurvey);
        survey = new Survey("Juan Evaluator", "Juana Evaluated", "Team",
                "06-02-2017", aptitudeSurveyList);
    }

    @Test
    public void constructMessageTextWithOneAptitude() {
        String result = mailService.constructMessageText(survey);
        String expected = "Cordial saludo, se acaba de realizar una valoración del ser \n"
                + "Persona valorada: " + "Juana Evaluated" + "\n"
                + "Persona que realizó la valoración: " + "Juan Evaluator" + "\n"
                + "Tipo de valoracón: "
                + "una competencia de una persona \n" + "Competencia evaluada: "
                + "Apertura";
        assertEquals(expected, result);
    }

    @Test
    public void constructMessageTextWithManyAptitude() {
        Survey newSurvey = survey;
        List<AptitudeSurvey> aptitudeSurveyList = survey.getAptitudes();
        aptitudeSurveyList.add(null);
        newSurvey.setAptitudes(aptitudeSurveyList);
        String result = mailService.constructMessageText(newSurvey);
        String expected = "Cordial saludo, se acaba de realizar una valoración del ser \n"
                + "Persona valorada: " + "Juana Evaluated" + "\n"
                + "Persona que realizó la valoración: " + "Juan Evaluator" + "\n"
                + "Tipo de valoracón: "
                + "Todas las competencias de una persona";
        assertEquals(expected, result);
    }
}
