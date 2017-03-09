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

    /*
    as this method accesses the DB to test survey operations i need some surveys to save and test, these are the ones
     */
    Survey survey1 = new Survey();
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


        survey1.setEvaluated("jhon doe");
        survey1.setEvaluator("psl");
        survey1.setRole("teanmmate");
        survey1.setTimestamp(formatted);


        survey2.setEvaluated("jhon doe");
        survey2.setEvaluator("coomeva");
        survey2.setTimestamp(formatted);

        returnedSaveSurvey = elasticsearchSurveyRepository.saveSurvey(survey1);
        returnedSaveSurvey2 = elasticsearchSurveyRepository.saveSurvey(survey2);
    }

    @After
    public void tearDown() throws Exception {
        elasticsearchSurveyRepository.deleteSurvey(survey1.getEvaluator(), survey1.getEvaluated(), survey1.getTimestamp());
        elasticsearchSurveyRepository.deleteSurvey(survey2.getEvaluator(), survey2.getEvaluated(), survey2.getTimestamp());

    }

    @Test
    public void saveSurvey() throws Exception {
        assertEquals(survey1.toString(), returnedSaveSurvey.toString());

    }

    @Test
    public void findUserSurveys() throws Exception {

        List<Survey> userSurveys = elasticsearchSurveyRepository.findUserSurveys(survey1.getEvaluated(), survey1.getTimestamp(), survey1.getTimestamp());

        boolean evaluatorFound = false;
        for (Survey surveyInList : userSurveys) {
            if (surveyInList.getEvaluator().equals(survey1.getEvaluator())) {
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

        assertTrue(elasticsearchSurveyRepository.existsRecentSurvey(survey1.getEvaluated(), survey1.getEvaluator(),null));

    }

    @Test
    public void deleteSurvey() throws Exception {

        assertTrue(elasticsearchSurveyRepository.deleteSurvey(survey2.getEvaluator(), survey2.getEvaluated(), survey2.getTimestamp()));

    }

    @Test
    public void findSurvey() throws Exception {
        Survey surveyFound = elasticsearchSurveyRepository.findSurvey(this.survey1.getEvaluator(), this.survey1.getEvaluated(), this.survey1.getTimestamp());
        assertEquals(survey1.getEvaluator(),surveyFound.getEvaluator());
        assertEquals(survey1.getEvaluated(),surveyFound.getEvaluated());
        assertEquals(survey1.getRole(),surveyFound.getRole());
        assertEquals(survey1.getAptitudes(),surveyFound.getAptitudes());
        assertEquals(survey1.getTimestamp(),surveyFound.getTimestamp());

    }

}