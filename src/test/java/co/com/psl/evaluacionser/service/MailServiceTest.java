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
        survey = new Survey("Jhon Doe", "Jane Doe", "Team",
                "2017-06-06", aptitudeSurveyList);
    }

    @Test
    public void constructMessageTextWithOneAptitude() {
        String result = mailService.constructHtml(survey);
        String expected  = "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "* {\n" +
                "    font-family: Arial, Helvetica, sans-serif;\n" +
                "}\n" +
                "h2, h4{\n" +
                "    color: #134C96;\n" +
                "    font-weight: bold;\n" +
                "}\n" +
                "html {\n" +
                "  height: 100%;\n" +
                "  box-sizing: border-box;\n" +
                "}\n" +
                "*,\n" +
                "*:before,\n" +
                "*:after {\n" +
                "  box-sizing: inherit;\n" +
                "}\n" +
                "body {\n" +
                "  position: relative;\n" +
                "  margin: 0;\n" +
                "  padding-bottom: 6rem;\n" +
                "  min-height: 100%;\n" +
                "}\n" +
                ".box {\n" +
                "  margin: 0 auto;\n" +
                "  padding-top: 10%;\n" +
                "  max-width: 640px;\n" +
                "  width: 94%;\n" +
                "}\n" +
                ".box h1 {\n" +
                "  margin-top: 0;\n" +
                "}\n" +
                ".footer {\n" +
                "  position: absolute;\n" +
                "  right: 0;\n" +
                "  bottom: 0;\n" +
                "  left: 0;\n" +
                "  padding: 1rem;\n" +
                "  background-color: #efefef;\n" +
                "  text-align: center;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"footer\"></div>\n" +
                "<div class=\"box\">\n" +
                "<h2>Cordial saludo, se ha realizado una valoración del ser</h2> \n" +
                " <h4>Persona valorada:</h4> \n" +
                " <p> Jane Doe </p> \n" +
                " <h4>Persona que realizó la valoración:</h4>\n" +
                " <p> Jhon Doe </p> \n" +
                " <h4>Tipo de valoracón:</h4> \n" +
                " <p> Una competencia de una persona</p> \n" +
                " <h4>Competencia evaluada:</h4> \n" +
                " <p> Apertura</p>\n" +
                "</div>\n" +
                "<div class=\"footer\">This message is auto-generated - <strong>Competencias del ser PSL </strong> - </div>\n" +
                "</body>\n" +
                "</html>\n";
        assertEquals(expected, result);
    }

    @Test
    public void constructMessageTextWithManyAptitude() {
        Survey newSurvey = survey;
        List<AptitudeSurvey> aptitudeSurveyList = survey.getAptitudes();
        aptitudeSurveyList.add(null);
        newSurvey.setAptitudes(aptitudeSurveyList);
        String result = mailService.constructHtml(newSurvey);
        String expected = "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "* {\n" +
                "    font-family: Arial, Helvetica, sans-serif;\n" +
                "}\n" +
                "h2, h4{\n" +
                "    color: #134C96;\n" +
                "    font-weight: bold;\n" +
                "}\n" +
                "html {\n" +
                "  height: 100%;\n" +
                "  box-sizing: border-box;\n" +
                "}\n" +
                "*,\n" +
                "*:before,\n" +
                "*:after {\n" +
                "  box-sizing: inherit;\n" +
                "}\n" +
                "body {\n" +
                "  position: relative;\n" +
                "  margin: 0;\n" +
                "  padding-bottom: 6rem;\n" +
                "  min-height: 100%;\n" +
                "}\n" +
                ".box {\n" +
                "  margin: 0 auto;\n" +
                "  padding-top: 10%;\n" +
                "  max-width: 640px;\n" +
                "  width: 94%;\n" +
                "}\n" +
                ".box h1 {\n" +
                "  margin-top: 0;\n" +
                "}\n" +
                ".footer {\n" +
                "  position: absolute;\n" +
                "  right: 0;\n" +
                "  bottom: 0;\n" +
                "  left: 0;\n" +
                "  padding: 1rem;\n" +
                "  background-color: #efefef;\n" +
                "  text-align: center;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"footer\"></div>\n" +
                "<div class=\"box\">\n" +
                "<h2>Cordial saludo, se ha realizado una valoración del ser</h2> \n" +
                " <h4>Persona valorada:</h4> \n" +
                " <p> Jane Doe </p> \n" +
                " <h4>Persona que realizó la valoración:</h4>\n" +
                " <p> Jhon Doe </p> \n" +
                " <h4>Tipo de valoracón:</h4> \n" +
                " <p>Todas las competencias de una persona</p> \n" +
                "</div>\n" +
                "<div class=\"footer\">This message is auto-generated - <strong>Competencias del ser PSL </strong> - </div>\n" +
                "</body>\n" +
                "</html>\n";
        assertEquals(expected, result);
    }
}
