package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchSurveyRepository implements SurveyRepository{

    //TODO implementar guardado de Survey
    @Override
    public Survey saveSurvey(Survey survey) {
        return null;
    }
}
