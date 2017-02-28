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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ElasticsearchAptitudeRepository implements AptitudeRepository {

    private static Logger logger = Logger.getLogger(ElasticsearchAptitudeRepository.class);
    @Value("${elasticAptitudeIndex}")
    private String aptitudeIndexName;
    @Value("${elasticAptitudeType}")
    private String aptitudeTypeName;
    @Autowired
    private JestClient client;



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

            if (!result.isSucceeded())
                return Collections.emptyList();

            List<Hit<Aptitude, Void>> aptitudes = result.getHits(Aptitude.class);
            return aptitudes.stream().map(this::getAptitude).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("There was a problem finding all the Aptitudes ", e);
            return Collections.emptyList();
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
        searchSourceBuilder.query(QueryBuilders.matchQuery("_id", id));

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(aptitudeIndexName).build();

        try {
            SearchResult result = client.execute(search);

            if (!result.isSucceeded())
                return null;

            return getAptitude(result.getFirstHit(Aptitude.class));
        } catch (IOException e) {
            logger.error("The search could not be completed ", e);
            return null;
        }
    }

    /**
     * makes the Hit from the Search an Aptitude
     *
     * @param hit the Hit from the previously done Search to the DB
     * @return an Aptitude with the data corresponding to the Source of the hit
     */
    private Aptitude getAptitude(Hit<Aptitude, Void> hit) {
        if (hit == null)
            return null;

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

        if (aptitude == null)
            return Collections.emptyList();

        return aptitude.getBehaviors();
    }

    @Override
    public Behavior findBehaviorById(String aptitudeId, String id) {
        List<Behavior> behaviors = findAllBehaviors(aptitudeId);

        if (behaviors.isEmpty())
            return null;

        for (Behavior behavior : behaviors)
            if (id.equals(behavior.getId()))
                return behavior;

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
        List<Behavior> behaviors = aptitude.getBehaviors();

        Behavior behavior = new Behavior(String.valueOf(behaviors.size() + 1), behaviorDto.getEn(), behaviorDto.getEs());
        aptitude.addBehavior(behavior);
        updateAptitude(aptitude);
        return behavior;
    }

    /**
     * deletes a specific behavior from a Aptitude
     *
     * @param id         this is the id of the Aptitude containing the Behaviors
     * @param behaviorId this is the id of the behavior you want to delete
     * @return an Aptitude without the Behaviors specified
     */
    @Override
    public Aptitude deleteBehavior(String id, String behaviorId) {
        if (findBehaviorById(id, behaviorId) == null)
            return null;

        Aptitude aptitude = findById(id);
        List<Behavior> behaviors = aptitude.getBehaviors();

        for (Behavior behavior : behaviors) {
            if (behaviorId.equals(behavior.getId())) {
                behaviors.remove(behavior);

                for (int i = 0; i < behaviors.size(); i++) {
                    behaviors.get(i).setId(String.valueOf(i + 1));
                }

                aptitude.setBehaviors(behaviors);
                break;
            }
        }

        updateAptitude(aptitude);
        return aptitude;


    }

    @Override
    public Behavior updateBehaviorById(String id, Behavior behavior) {
        Aptitude aptitude = findById(id);
        List<Behavior> behaviors = aptitude.getBehaviors();
        behaviors.set(Integer.parseInt(behavior.getId()) - 1, behavior);
        aptitude.setBehaviors(behaviors);
        updateAptitude(aptitude);
        return behavior;

    }

    private Aptitude updateAptitude(Aptitude aptitude) {
        Index index = new Index.Builder(aptitude).index(aptitudeIndexName).type(aptitudeTypeName).id(String.valueOf(aptitude.getId())).build();
        try {
            client.execute(index);
            return aptitude;
        } catch (IOException e) {
            logger.error("the aptitude could not be updated ", e);
            return null;
        }
    }

}
