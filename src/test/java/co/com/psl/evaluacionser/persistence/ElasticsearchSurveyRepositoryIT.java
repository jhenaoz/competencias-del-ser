package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchSurveyRepositoryIT {

    @Autowired
    private ElasticsearchSurveyRepository elasticsearchSurveyRepository;

    @Test
    public void SurveySaveDelete() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(cal.getTime());

        Survey survey = new Survey();
        survey.setEvaluated("evaluated");
        survey.setEvaluator("evaluator");
        survey.setRole("teanmmate");
        survey.setTimestamp(formatted);

        Survey survey2 = new Survey();
        survey2.setEvaluated("evaluated");
        survey2.setEvaluator("evaluator2");
        survey2.setTimestamp(formatted);

        Survey returnedSaveSurvey = new Survey();
        Survey foundSurvey = new Survey();
        returnedSaveSurvey = elasticsearchSurveyRepository.saveSurvey(survey);
        elasticsearchSurveyRepository.saveSurvey(survey2);

        foundSurvey = elasticsearchSurveyRepository.findSurvey("evaluator", "evaluated", formatted);
        List<Survey> surveys = elasticsearchSurveyRepository.findUserSurveys("evaluated", formatted, formatted);

        boolean evaluator2Found = false;
        for (Survey survey1 : surveys) {
            if (survey1.getEvaluator().equals("evaluator2")) {
                evaluator2Found = true;
            }

        }

        boolean evaluator1984NotFound = false;
        for (Survey survey1 : surveys) {
            if (survey1.getEvaluator().equals("evaluator1984")) {
                evaluator1984NotFound = true;
            }

        }
        assertEquals(false, evaluator1984NotFound);
        assertEquals(true, evaluator2Found);

        assertEquals("evaluated", foundSurvey.getEvaluated());
        assertEquals("evaluator", foundSurvey.getEvaluator());
        assertEquals("teanmmate", foundSurvey.getRole());
        assertEquals(formatted, foundSurvey.getTimestamp());

        assertEquals("evaluated", returnedSaveSurvey.getEvaluated());
        assertEquals("evaluator", returnedSaveSurvey.getEvaluator());
        assertEquals(formatted, returnedSaveSurvey.getTimestamp());

        boolean surveyExist = elasticsearchSurveyRepository.existsRecentSurvey(survey.getEvaluated(), survey.getEvaluator());
        boolean surveyDontExist = elasticsearchSurveyRepository.existsRecentSurvey("unexistent evaluated", survey.getEvaluator());

        assertEquals(true, surveyExist);
        assertEquals(false, surveyDontExist);


        boolean wasDeleted = false;
        wasDeleted = elasticsearchSurveyRepository.deleteSurvey(survey.getEvaluator(), survey.getEvaluated(), formatted);
        elasticsearchSurveyRepository.deleteSurvey(survey2.getEvaluator(), survey2.getEvaluated(), formatted);
        assertEquals(true, wasDeleted);

    }
}
