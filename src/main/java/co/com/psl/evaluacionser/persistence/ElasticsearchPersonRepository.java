package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Person;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

/**
 * The implementation of the PersonRepository with elasticsearch
 */
@Component
public class ElasticsearchPersonRepository implements PersonRepository {

    /**
     * These Strings must be congruent with the elasticsearch database
     */
    private String PERSON_INDEX_NAME = "person";
    private String PERSON_TYPE_NAME = "employee";

    /**
     * Calls the JestClient defined as a bean
     */
    @Autowired
    private JestClient client;

    static Logger logger = Logger.getLogger(ElasticsearchPersonRepository.class);

    /**
     * This method find all indexes person with type employee
     *
     * @return person list from the elastisearch
     */
    @Override
    public List<Person> findAll() {
        SearchResult result = null;
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(PERSON_INDEX_NAME)
                .addType(PERSON_TYPE_NAME).build();
        try {
            result = client.execute(search);
        } catch (IOException e) {
            logger.error("The search can't be completed \n" + e.getMessage());
        }
        List<SearchResult.Hit<Person, Void>> hits = result.getHits(Person.class);
        return hits.stream().map(this::getPerson).collect(Collectors.toList());
    }

    private Person getPerson(Hit<Person, Void> hit) {
        return hit.source;
    }

    @Override
    public Person save(Person person) {
        Index index = new Index.Builder(person).index(PERSON_INDEX_NAME).type(PERSON_TYPE_NAME).build();
        try {
            client.execute(index);
            return person;
        } catch (IOException e) {
            logger.error("The person can't be saved \n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
