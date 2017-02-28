package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;
import io.searchbox.client.JestClient;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
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

    @Override
    public Survey saveSurvey(Survey survey) {
        Index index = new Index.Builder(survey).index(surveyIndexName).type(surveyTypeName).build();
        try {
            client.execute(index);
            return survey;
        } catch (IOException e) {
            e.printStackTrace();
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
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery("timestamp").from(startDate).to(endDate));
        searchSourceBuilder.query(QueryBuilders.matchQuery("evaluated", user));
        searchSourceBuilder.sort("timestamp");

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(surveyIndexName).build();

        try {
            SearchResult result = client.execute(search);

            if (!result.isSucceeded())
                return null;

            List<Hit<Survey, Void>> aptitudes = result.getHits(Survey.class);
            return aptitudes.stream().map(this::getSurvey).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Survey getSurvey(Hit<Survey, Void> hit) {
        if (hit == null)
            return null;

        return hit.source;
    }

    public boolean deleteSurvey(String evaluator, String evaluated, String timestamp) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("evaluated", evaluated))
                        .must(QueryBuilders.matchQuery("evaluator", evaluator))
                        .must(QueryBuilders.matchQuery("timestamp", timestamp))
        );

        DeleteByQuery deleteSpecificSurvey = new DeleteByQuery.Builder(searchSourceBuilder.toString())
                .addIndex(surveyIndexName)
                .addType(surveyTypeName)
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

