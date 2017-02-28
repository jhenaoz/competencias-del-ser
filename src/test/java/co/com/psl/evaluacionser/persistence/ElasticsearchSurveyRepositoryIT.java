package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchSurveyRepositoryIT {

    @Autowired
    private ElasticsearchSurveyRepository elasticsearchSurveyRepository;

    @Test
    public void SurveySaveDelete() {
        Object lock = new Object();
        synchronized (lock) {
            Survey survey = new Survey();
            survey.setEvaluated("evaluated");
            survey.setEvaluator("evaluator");
            survey.setTimestamp("2017-1-30");
            Survey returnedSaveSurvey = new Survey();
            Survey foundSurvey = new Survey();
            returnedSaveSurvey = elasticsearchSurveyRepository.saveSurvey(survey);

            try {
                lock.wait(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            foundSurvey = elasticsearchSurveyRepository.findSurvey("evaluator", "evaluated", "2017-1-30");
            assertEquals("evaluated", foundSurvey.getEvaluated());
            assertEquals("evaluator", foundSurvey.getEvaluator());
            assertEquals("2017-1-30", foundSurvey.getTimestamp());

            assertEquals("evaluated", returnedSaveSurvey.getEvaluated());
            assertEquals("evaluator", returnedSaveSurvey.getEvaluator());
            assertEquals("2017-1-30", returnedSaveSurvey.getTimestamp());
            boolean wasDeleted;
            wasDeleted = elasticsearchSurveyRepository.deleteSurvey("evaluator", "evaluated", "2017-1-30");
            assertEquals(true, wasDeleted);
        }
    }
}
