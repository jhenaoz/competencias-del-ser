package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ElasticsearchSurveyRepositoryIT {

    Survey survey = new Survey();
    Survey survey2 = new Survey();
    Survey returnedSaveSurvey;
    Survey returnedSaveSurvey2;

    @Autowired
    ElasticsearchSurveyRepository elasticsearchSurveyRepository;


    @Before
    public void setUp() throws Exception {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(cal.getTime());


        survey.setEvaluated("evaluated");
        survey.setEvaluator("evaluator");
        survey.setRole("teanmmate");
        survey.setTimestamp(formatted);


        survey2.setEvaluated("evaluated");
        survey2.setEvaluator("evaluator2");
        survey2.setTimestamp(formatted);

        returnedSaveSurvey = elasticsearchSurveyRepository.saveSurvey(survey);
        returnedSaveSurvey2 = elasticsearchSurveyRepository.saveSurvey(survey2);
    }

    @After
    public void tearDown() throws Exception {
        elasticsearchSurveyRepository.deleteSurvey(survey.getEvaluator(), survey.getEvaluated(), survey.getTimestamp());
        elasticsearchSurveyRepository.deleteSurvey(survey2.getEvaluator(), survey2.getEvaluated(), survey2.getTimestamp());

    }

    @Test
    public void saveSurvey() throws Exception {
        assertEquals(survey.toString(), returnedSaveSurvey.toString());

    }

    @Test
    public void findUserSurveys() throws Exception {

        List<Survey> userSurveys = elasticsearchSurveyRepository.findUserSurveys(survey.getEvaluated(), survey.getTimestamp(), survey.getTimestamp());

        boolean evaluatorFound = false;
        for (Survey surveyInList : userSurveys) {
            if (surveyInList.getEvaluator().equals(survey.getEvaluator())) {
                evaluatorFound = true;
            }

        }

        boolean evaluator2Found = false;
        for (Survey surveyInList : userSurveys) {
            if (surveyInList.getEvaluator().equals(survey2.getEvaluator())){
                evaluator2Found = true;
            }

        }
        assertTrue(evaluator2Found);
        assertTrue(evaluatorFound);
    }

    @Test
    public void existsRecentSurvey() throws Exception {

        assertTrue(elasticsearchSurveyRepository.existsRecentSurvey(survey.getEvaluated(), survey.getEvaluator()));

    }

    @Test
    public void deleteSurvey() throws Exception {

        assertTrue(elasticsearchSurveyRepository.deleteSurvey(survey2.getEvaluator(), survey2.getEvaluated(), survey2.getTimestamp()));

    }

    @Test
    public void findSurvey() throws Exception {
        Survey surveyFound = elasticsearchSurveyRepository.findSurvey(this.survey.getEvaluator(), this.survey.getEvaluated(), this.survey.getTimestamp());
        assertEquals(survey.getEvaluator(),surveyFound.getEvaluator());
        assertEquals(survey.getEvaluated(),surveyFound.getEvaluated());
        assertEquals(survey.getRole(),surveyFound.getRole());
        assertEquals(survey.getAptitudes(),surveyFound.getAptitudes());
        assertEquals(survey.getTimestamp(),surveyFound.getTimestamp());

    }

}