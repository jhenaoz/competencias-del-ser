package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.AptitudeSurvey;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorSurvey;
import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReporterGeneratorTest {

    private ReportGenerator reportGenerator = new ReportGenerator();

    private Survey survey = new Survey();
    private List<Survey> surveys = new ArrayList<>();

    @Before
    public void setUp() {
        Behavior behavior = new Behavior(1, "Acepta sugerencias sin distincion de quien las de",
                "They accept suggestions regardless of who gives them");

        Behavior behavior1 = new Behavior(2, "Reconoce sus errore", "They recognize their mistakes");

        List<BehaviorSurvey> behaviorSurveys = new ArrayList<>();
        behaviorSurveys.add(new BehaviorSurvey(behavior, 5));
        behaviorSurveys.add(new BehaviorSurvey(behavior1, 3));

        AptitudeDto aptitudeDto = new AptitudeDto("1", "Apertura", "Openness");

        List<AptitudeSurvey> aptitudeSurveys = new ArrayList<>();
        aptitudeSurveys.add(new AptitudeSurvey(aptitudeDto, "", behaviorSurveys));

        Survey jhonSurvey = new Survey("Jhon Doe Evaluator", "Jane Doe evaluated",
                "Teammate", "2017-03-06", aptitudeSurveys);
        Survey juanSurvey = new Survey("Juan Perez", "Juana Perez",
                "Client", "2017-02-02", aptitudeSurveys);

        surveys.add(jhonSurvey);
        surveys.add(juanSurvey);
    }

    @Test
    public void roleTranslateClientShouldReturnCliente() {
        String result = reportGenerator.roleTranslate("client");
        assertEquals("Cliente", result);
    }

    @Test
    public void roleTranslateNoValidShouldReturnNa() {
        String result = reportGenerator.roleTranslate("Relation no defined");
        assertEquals("N/A", result);
    }

    @Test
    public void roleTranslateNullShouldReturnNa() {
        String result = reportGenerator.roleTranslate(null);
        assertEquals("N/A", result);
    }
}
