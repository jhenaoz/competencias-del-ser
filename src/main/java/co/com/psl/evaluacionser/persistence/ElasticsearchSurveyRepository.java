package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ElasticsearchSurveyRepository implements SurveyRepository {

    private static Logger logger = Logger.getLogger(ElasticsearchSurveyRepository
            .class);
    @Value("${elasticSurveyIndex}")
    private String surveyIndexName;
    @Value("${elasticSurveyType}")
    private String surveyTypeName;
    @Autowired
    private JestClient client;

    @Override
    public Survey saveSurvey(Survey survey) {
        Index index = new Index.Builder(survey).index(surveyIndexName).type(surveyTypeName).build();
        try {
            client.execute(index);
            return survey;
        } catch (IOException e) {
            logger.error("The Survey could not be saved " + e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
