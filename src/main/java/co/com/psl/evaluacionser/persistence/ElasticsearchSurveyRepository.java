package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import io.searchbox.params.Parameters;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ElasticsearchSurveyRepository implements SurveyRepository {

    @Value("${elasticSurveyIndex}")
    private String surveyIndexName;
    @Value("${elasticSurveyType}")
    private String surveyTypeName;
    @Autowired
    private JestClient client;

    static Logger logger = Logger.getLogger(ElasticsearchSurveyRepository.class);

    @Override
    public Survey saveSurvey(Survey survey) {
        Index index = new Index.Builder(survey).index(surveyIndexName).type(surveyTypeName).setParameter(Parameters.REFRESH, true).build();
        try {
            client.execute(index);
            return survey;
        } catch (IOException e) {
            logger.error("The survey couldn't be saved " + e.getMessage());
            throw new RuntimeException(e);
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
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("timestamp").from(startDate).to(endDate))
                .must(QueryBuilders.matchQuery("evaluated", user));

        return findSurveys(boolQueryBuilder);
    }

    /**
     * Checks whether a survey was made in the last week.
     *
     * @param evaluated the person who was evaluated in the survey
     * @param evaluator the person who made the survey
     * @return if the survey was made in the last week
     */
    @Override
    public boolean existsRecentSurvey(String evaluated, String evaluator) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("timestamp").from("now-1w").to("now"))
                .must(QueryBuilders.matchQuery("evaluated", evaluated))
                .must(QueryBuilders.matchQuery("evaluator", evaluator));

        List<Survey> surveysFound = findSurveys(boolQueryBuilder);
        return (surveysFound != null) && !surveysFound.isEmpty();
    }

    /**
     * Utility method for retrieving a list of surveys
     *
     * @param boolQueryBuilder the elasticsearch query to be used
     * @return a list with the surveys retrieved
     */
    private List<Survey> findSurveys(BoolQueryBuilder boolQueryBuilder) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(surveyIndexName).build();

        try {
            SearchResult result = client.execute(search);

            if (!result.isSucceeded())
                return null;

            List<Hit<Survey, Void>> aptitudes = result.getHits(Survey.class);
            return aptitudes.stream().map(this::getSurvey).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("The surveys couldn't be found " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Survey getSurvey(Hit<Survey, Void> hit) {
        if (hit == null)
            return null;

        return hit.source;
    }

    public boolean deleteSurvey(String evaluator, String evaluated, String timestamp) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("evaluator", evaluator))
                .must(QueryBuilders.termQuery("evaluated", evaluated))
                .must(QueryBuilders.termQuery("timestamp", timestamp));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);

        DeleteByQueryOW deleteSpecificSurvey = new DeleteByQueryOW.Builder(searchSourceBuilder.toString())
                .addIndex(surveyIndexName)
                .refresh(true)
                .build();

        try {
            client.execute(deleteSpecificSurvey);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Survey findSurvey(String evaluator, String evaluated, String timestamp) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("evaluated", evaluated))
                        .must(QueryBuilders.matchQuery("evaluator", evaluator))
                        .must(QueryBuilders.rangeQuery("timestamp").from(timestamp).to(timestamp))
        );
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(surveyIndexName).build();

        try {
            SearchResult result = client.execute(search);

            if (!result.isSucceeded())
                return null;
            return getSurvey(result.getFirstHit(Survey.class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
