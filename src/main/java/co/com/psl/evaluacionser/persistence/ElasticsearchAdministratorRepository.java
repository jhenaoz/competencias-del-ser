package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.service.dto.Administrator;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class connects with the database to make the password operations
 */
@Component
public class ElasticsearchAdministratorRepository implements AdministratorRepository {

    @Value("${elasticAdministratorIndex}")
    private String administratorIndex;

    @Value("${elasticAdministratorType}")
    private String administratorType;

    private JestClient client;

    private static final Logger logger = Logger.getLogger(ElasticsearchAdministratorRepository.class);

    @Autowired
    public ElasticsearchAdministratorRepository(final JestClient client) {
        this.client = client;
    }

    /**
     * This method finds the administrator in the database
     *
     * @return Administrator list from the Elasticsearch
     */
    @Override
    public Administrator findAdministrator() {
        SearchResult result;
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("_id", "1"));
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(administratorIndex)
                .addType(administratorType).build();
        try {
            result = client.execute(search);
            if (!result.isSucceeded()) {
                return null;
            }
            return getAdministrator(result.getFirstHit(Administrator.class));

        } catch (IOException e) {
            logger.error("The search can't be completed " + e.getMessage(), e);
            return null;
        }

    }

    /**
     * This method takes all elasticsearch response and returns just the administrator object
     * @param hit all elasticsearch response
     * @return all the administrator objects that must be one
     */
    private Administrator getAdministrator(SearchResult.Hit<Administrator, Void> hit) {
        if (hit == null) {
            return null;
        }
        return hit.source;
    }

    /**
     * This method updates the administrator's params in the database
     * @param administrator the administrator that is going to be updated
     * @return the updated administrator
     */
    @Override
    public Administrator updateAdministrator(Administrator administrator) {
        Index index = new Index.Builder(administrator)
                .index(administratorIndex)
                .type(administratorType)
                .id("1")
                .refresh(true)
                .build();
        try {
            client.execute(index);
            return administrator;
        } catch (IOException e) {
            logger.error("The administrator couldn't be updated " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
