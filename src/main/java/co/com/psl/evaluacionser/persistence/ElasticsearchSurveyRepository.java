package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
            logger.error("The Survey could not be saved ", e);
            return null;
        }
    }

    /**
     * Searches all surveys made to a person within a time period.
     *
     * @param user      the person to search
     * @param startDate starting date
     * @param endDate   ending date
     * @return the surveys made to the user
     */
    @Override
    public List<Survey> findUserSurveys(String user, String startDate, String endDate) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery("timestamp").from(startDate).to(endDate));
        searchSourceBuilder.query(QueryBuilders.matchQuery("evaluated", user));
        searchSourceBuilder.sort("timestamp");

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(surveyIndexName).build();

        try {
            SearchResult result = client.execute(search);

            if (!result.isSucceeded())
                return Collections.emptyList();

            List<Hit<Survey, Void>> aptitudes = result.getHits(Survey.class);
            return aptitudes.stream().map(this::getSurvey).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("The survey search could not be completed", e);
            return Collections.emptyList();
        }
    }

    private Survey getSurvey(Hit<Survey, Void> hit) {
        if (hit == null)
            return null;

        return hit.source;
    }
}
