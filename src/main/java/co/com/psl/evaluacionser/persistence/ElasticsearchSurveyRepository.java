package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ElasticsearchSurveyRepository implements SurveyRepository {

    @Value("${elasticSurveyIndex}")
    private String SURVEY_INDEX_NAME;

    @Value("${elasticSurveyType}")
    private String SURVEY_TYPE_NAME;

    @Autowired
    private JestClient client;

    @Override
    public Survey saveSurvey(Survey survey) {
        Index index = new Index.Builder(survey).index(SURVEY_INDEX_NAME).type(SURVEY_TYPE_NAME).build();
        try {
            client.execute(index);
            return survey;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
