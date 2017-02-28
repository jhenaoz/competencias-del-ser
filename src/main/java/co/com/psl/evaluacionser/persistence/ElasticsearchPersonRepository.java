package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Person;
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

/**
 * The implementation of the PersonRepository with elasticsearch
 */
@Component
public class ElasticsearchPersonRepository implements PersonRepository {

    private static Logger logger = Logger.getLogger(ElasticsearchAptitudeRepository.class);

    /**
     * These Strings must be congruent with the elasticsearch database
     */
    @Value("${elasticPersonIndex}")
    private String personIndexName;

    @Value("${elasticPersonType}")
    private String personTypeName;

    /**
     * Calls the JestClient defined as a bean
     */
    @Autowired
    private JestClient client;

    /**
     * This method find all indexes person with type employee
     *
     * @return person list from the elastisearch
     */
    @Override
    public List<Person> findAll() {
        SearchResult result;
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(personIndexName)
                .addType(personTypeName).build();

        try {
            result = client.execute(search);

            if (!result.isSucceeded())
                return Collections.emptyList();

            List<SearchResult.Hit<Person, Void>> hits = result.getHits(Person.class);
            return hits.stream().map(this::getPerson).collect(Collectors.toList());

        } catch (IOException e) {
            logger.error("The search could not be completed " + e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    private Person getPerson(Hit<Person, Void> hit) {
        return hit.source;
    }

    @Override
    public Person save(Person person) {
        Index index = new Index.Builder(person).index(personIndexName).type(personTypeName).build();
        try {
            client.execute(index);
            return person;
        } catch (IOException e) {
            logger.error("The search could not be completed " + e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
