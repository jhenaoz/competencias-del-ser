package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.service.dto.BehaviorDto;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ElasticsearchAptitudeRepository implements AptitudeRepository {

    private static final Logger logger = Logger.getLogger(ElasticsearchAptitudeRepository.class);
    @Value("${elasticAptitudeIndex}")
    private String aptitudeIndexName;
    @Value("${elasticAptitudeType}")
    private String aptitudeTypeName;
    private JestClient client;

    @Autowired
    public ElasticsearchAptitudeRepository(final JestClient client) {
        this.client = client;
    }

    /**
     * Receives one aptitude and saves it in the DB
     *
     * @param aptitude the aptitude you want to save
     * @return the aptitude you just saved, now with its ID included
     */
    @Override
    public Aptitude save(Aptitude aptitude) {
        Index index = new Index.Builder(aptitude).index(aptitudeIndexName).type(aptitudeTypeName).build();
        try {
            client.execute(index);
            return aptitude;
        } catch (IOException e) {
            logger.error("The aptitude couldn't be saved in the data base " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches the DB for all the different Aptitudes
     *
     * @return an Aptitude List with all the aptitudes in the DB
     */
    @Override
    public List<Aptitude> findAll() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.sort("id", SortOrder.ASC);

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(aptitudeIndexName).build();

        try {
            SearchResult result = client.execute(search);

            if (!result.isSucceeded()) {
                return null;
            }

            List<Hit<Aptitude, Void>> aptitudes = result.getHits(Aptitude.class);
            return aptitudes.stream().map(this::getAptitude).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("The search couldn't be executed" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds one specific aptitude in the DB
     *
     * @param id the ID of the Aptitude
     * @return the aptitude found
     */
    @Override
    public Aptitude findById(String id) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("id", id));

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(aptitudeIndexName).build();

        try {
            SearchResult result = client.execute(search);

            if (!result.isSucceeded()) {
                return null;
            }
            return getAptitude(result.getFirstHit(Aptitude.class));
        } catch (IOException e) {
            logger.error("The aptitude with the given id couldn't be found " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * makes the Hit from the Search an Aptitude
     *
     * @param hit the Hit from the previously done Search to the DB
     * @return an Aptitude with the data corresponding to the Source of the hit
     */
    private Aptitude getAptitude(Hit<Aptitude, Void> hit) {
        if (hit == null) {
            return null;
        }

        return hit.source;
    }

    /**
     * Searches the DB for all the different Behaviors of an Aptitude
     *
     * @return an Behaviors List with all the behaviors in that aptitude
     */
    @Override
    public List<Behavior> findAllBehaviors(String aptitudeId) {
        Aptitude aptitude = findById(aptitudeId);

        if (aptitude == null) {
            return null;
        }

        return aptitude.getBehaviors();
    }

    @Override
    public Behavior findBehaviorById(String aptitudeId, long id) {
        List<Behavior> behaviors = findAllBehaviors(aptitudeId);

        if (behaviors == null) {
            return null;
        }

        for (Behavior behavior : behaviors) {
            if (id == behavior.getId()) {
                return behavior;
            }
        }

        return null;
    }

    /**
     * add a new behavior to the Aptitude
     *
     * @param behaviorDto a JSON with the structure of the Behaviors, without the ID
     * @param aptitudeId  the ID of the Aptitude in which you are adding the Behaviors
     * @return The behavior you just added, now with its ID
     */
    @Override
    public Behavior addBehavior(BehaviorDto behaviorDto, String aptitudeId) {
        Aptitude aptitude = findById(aptitudeId);

        Behavior behavior = new Behavior(getNextBehaviorId(aptitudeId),
                behaviorDto.getEs(),
                behaviorDto.getEn());
        aptitude.addBehavior(behavior);
        updateAptitude(aptitude);
        return behavior;
    }

    private long getNextBehaviorId(String aptitudeId) {
        String query = "{\n"
                + "    \"query\" : {\n"
                + "        \"match\" : {\"id\":" + aptitudeId + "}\n"
                + "    },\n"
                + "    \"aggs\" : {\n"
                + "        \"maximumBehaviorId\" : {\n"
                + "            \"max\" : {\n"
                + "                \"field\" : \"behaviors.id\"\n"
                + "            }\n"
                + "        }\n"
                + "    }\n"
                + "}";
        Search search = new Search.Builder(query)
                .addIndex(aptitudeIndexName)
                .addType(aptitudeTypeName)
                .build();

        try {
            SearchResult result = client.execute(search);


            long maximumBehaviorId = result.getAggregations().getMaxAggregation("maximumBehaviorID")
                    .getMax().longValue();

            return maximumBehaviorId + 1;

        } catch (IOException e) {
            logger.error("there was an error with the aggregation", e);
        }

        return -1;
    }

    /**
     * deletes a specific behavior from a Aptitude
     *
     * @param id         this is the id of the Aptitude containing the Behaviors
     * @param behaviorId this is the id of the behavior you want to delete
     * @return an Aptitude without the Behaviors specified
     */
    @Override
    public Aptitude deleteBehavior(String id, long behaviorId) {
        if (findBehaviorById(id, behaviorId) == null) {
            return null;
        }

        Aptitude aptitude = findById(id);
        List<Behavior> behaviors = aptitude.getBehaviors();

        behaviors.removeIf(behaviorInList -> behaviorInList.getId() == behaviorId);
        aptitude.setBehaviors(behaviors);
        updateAptitude(aptitude);
        return aptitude;


    }


    @Override
    public Behavior updateBehaviorById(String id, Behavior behavior) {
        Aptitude aptitude = findById(id);
        List<Behavior> behaviors = aptitude.getBehaviors();

        int i = 0;
        while (i<getNextBehaviorId(id)) {
            if (behaviors.get(i).getId() == behavior.getId()) {
                behaviors.set(i, behavior);
                break;
            }
            i++;
        }

        aptitude.setBehaviors(behaviors);
        updateAptitude(aptitude);
        return behavior;

    }

    private Aptitude updateAptitude(Aptitude aptitude) {
        Index index = new Index.Builder(aptitude)
                .index(aptitudeIndexName)
                .type(aptitudeTypeName)
                .id(String.valueOf(aptitude.getId()))
                .build();
        try {
            client.execute(index);
            return aptitude;
        } catch (IOException e) {
            logger.error("The aptitude couldn't be updated" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
