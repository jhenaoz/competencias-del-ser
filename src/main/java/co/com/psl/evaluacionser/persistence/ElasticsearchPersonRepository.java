package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Person;
import io.searchbox.client.JestClient;
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

/**
 * The implementation of the PersonRepository with elasticsearch
 */
@Component
public class ElasticsearchPersonRepository implements PersonRepository {

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
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(personIndexName)
                .addType(personTypeName).build();
        try {
            result = client.execute(search);
            if (!result.isSucceeded())
                return null;

        } catch (IOException e) {
            logger.error("The search can't be completed " + e.getMessage());
        }
        List<SearchResult.Hit<Person, Void>> hits = result.getHits(Person.class);
        return hits.stream().map(this::getPerson).collect(Collectors.toList());
    }

    private Person getPerson(Hit<Person, Void> hit) {
        if (hit == null)
            return null;

        return hit.source;
    }

    @Override
    public Person save(Person person) {
        Index index = new Index.Builder(person).index(personIndexName).type(personTypeName).refresh(true).build();
        try {
            client.execute(index);
            return person;
        } catch (IOException e) {
            logger.error("The person can't be saved " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Person findPersonById(String personId) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("id", personId));

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(personIndexName).build();

        try {
            SearchResult result = client.execute(search);

            if (!result.isSucceeded())
                return null;

            return getPerson(result.getFirstHit(Person.class));
        } catch (IOException e) {
            logger.error("There was an error executing the search " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public boolean deletePersonByIdAndName(String id, String name) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("id", id))
                .must(QueryBuilders.matchPhraseQuery("name", name));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);

        DeleteByQuery deleteSpecificPerson = new DeleteByQuery.Builder(searchSourceBuilder.toString())
                .addIndex(personIndexName)
                .refresh(true)
                .build();

        try {
            client.execute(deleteSpecificPerson);
            return true;
        } catch (IOException e) {
            logger.error("There was an error while trying to delete "+name+" ",e);
            return false;
        }
    }
}
