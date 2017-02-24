package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ElasticsearchSurveyRepository implements SurveyRepository {

    private static final String APTITUDE_INDEX_NAME = "survey";
    private static final String APTITUDE_TYPE_NAME = "survey";

    @Autowired
    private JestClient client;

    @Override
    public Survey saveSurvey(Survey survey) {
        Index index = new Index.Builder(survey).index(APTITUDE_INDEX_NAME).type(APTITUDE_TYPE_NAME).build();
        try {
            client.execute(index);
            return survey;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
