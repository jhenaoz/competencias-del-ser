package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;
import io.searchbox.client.JestClient;
import io.searchbox.core.Count;
import io.searchbox.core.CountResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
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

    private JestClient client;

    private static final Logger logger = Logger.getLogger(ElasticsearchSurveyRepository.class);

    @Autowired
    public ElasticsearchSurveyRepository(final JestClient client) {
        this.client = client;
    }

    @Override
    public Survey saveSurvey(Survey survey) {
        Index index = new Index.Builder(survey).index(surveyIndexName)
                .type(surveyTypeName).refresh(true).build();
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
                .must(QueryBuilders.rangeQuery("timestamp").from(startDate).to(endDate));

        if (user != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("evaluated", user));
        }

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
    public boolean existsRecentSurvey(String evaluated, String evaluator, String aptitudeId) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("timestamp").from("now-1w").to("now"))
                .must(QueryBuilders.matchQuery("evaluated", evaluated))
                .must(QueryBuilders.matchQuery("evaluator", evaluator));

        // Only want to search for recent surveys of that aptitude, if specified
        if (aptitudeId != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("aptitudes.aptitude.id", aptitudeId));
        }

        List<Survey> surveysFound = findSurveys(boolQueryBuilder);
        return surveysFound != null && !surveysFound.isEmpty();
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
        searchSourceBuilder.size(getTotalCount());

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(surveyIndexName).build();

        try {
            SearchResult result = client.execute(search);

            if (!result.isSucceeded()) {
                return null;
            }

            List<Hit<Survey, Void>> aptitudes = result.getHits(Survey.class);
            return aptitudes.stream().map(this::getSurvey).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("The surveys couldn't be found " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * This method goes to the database to make a count of how many surveys exist to be used in the main search
     * @return the number of surveys in the database
     */
    private int getTotalCount() {

        int count;
        Count countBuilder = new Count.Builder().addIndex(surveyIndexName).build();
        try {
            CountResult countResult = client.execute(countBuilder);
            count = countResult.getCount().intValue();
            return count;
        } catch (IOException e) {
            logger.error("There was an error doing the count.", e);
            return 0;
        }
    }

    /**
     * Returns just the hits in the total query
     * @param hit All the query
     * @return Just the objects needed from the query
     */
    private Survey getSurvey(Hit<Survey, Void> hit) {
        if (hit == null) {
            return null;
        }

        return hit.source;
    }

    public boolean deleteSurvey(String evaluator, String evaluated, String timestamp) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("evaluator", evaluator))
                .must(QueryBuilders.termQuery("evaluated", evaluated))
                .must(QueryBuilders.termQuery("timestamp", timestamp));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);

        DeleteByQuery deleteSpecificSurvey = new DeleteByQuery.Builder(searchSourceBuilder.toString())
                .addIndex(surveyIndexName)
                .refresh(true)
                .build();

        try {
            client.execute(deleteSpecificSurvey);
            return true;
        } catch (IOException e) {
            logger.error("there was an error while trying to delete the survey ", e);
            return false;
        }
    }

    public Survey findSurvey(String evaluator, String evaluated, String timestamp) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(getTotalCount());
        searchSourceBuilder.query(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("evaluated", evaluated))
                        .must(QueryBuilders.matchQuery("evaluator", evaluator))
                        .must(QueryBuilders.rangeQuery("timestamp").from(timestamp).to(timestamp))
        );
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(surveyIndexName).build();

        try {
            SearchResult result = client.execute(search);

            if (!result.isSucceeded()) {
                return null;
            }
            return getSurvey(result.getFirstHit(Survey.class));
        } catch (IOException e) {
            logger.error("There was an error while searching for the survey ", e);
            return null;
        }
    }


}
