package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.config.ElasticSearchUtils;
import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorDto;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ElasticsearchAptitudeRepository implements AptitudeRepository {

    private static final String APTITUDE_INDEX_NAME = "aptitude";
    private static final String APTITUDE_TYPE_NAME = "aptitude";
    @Autowired
    ElasticSearchUtils elasticSearchUtils;

    /**
     * receives one aptitude and saves it in the DB
     *
     * @param aptitude the aptitude you want to save
     * @return the aptitude you just saved, now with its ID included
     */
    @Override
    public Aptitude save(Aptitude aptitude) {
        Index index = new Index.Builder(aptitude).index(APTITUDE_INDEX_NAME).type(APTITUDE_TYPE_NAME).build();
        try {
            elasticSearchUtils.getClient().execute(index);
            return aptitude;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * searches the DB for all the diferent Aptitudes
     *
     * @return an Aptitude List with all the aptitudes in the DB
     */
    @Override
    public List<Aptitude> findAll() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.sort("id", SortOrder.ASC);

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(APTITUDE_INDEX_NAME).build();

        try {
            SearchResult result = elasticSearchUtils.getClient().execute(search);
            List<Hit<Aptitude, Void>> aptitudes = result.getHits(Aptitude.class);
            return aptitudes.stream().map(this::getAptitude).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Behavior modifyBehaviour(String id, String behaviorId, BehaviorDto behaviorDto) {
        if (findBehaviorById(id,behaviorId)==null)return null;
        Aptitude aptitude;
        aptitude = findById(id);
        List<Behavior> behaviors;
        behaviors = aptitude.getBehaviors();
        for (Behavior behavior:behaviors) {
            if (behavior.getId().equals(Long.getLong(behaviorId))){
                behavior.setEn(behaviorDto.getEn());
                behavior.setEs(behaviorDto.getEs());
                break;
            }

        }aptitude.setBehaviors(behaviors);
        save(aptitude);
        return findBehaviorById(id,behaviorId);
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
     * finds one specific Aptitude using its ID
     *
     * @param id the id of the aptitude you are looking for
     * @return an Aptitude with ES text, EN text, and a ID
     */
    @Override
    public Aptitude findById(String id) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("_id", id));

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(APTITUDE_INDEX_NAME).build();

        try {
            SearchResult result = elasticSearchUtils.getClient().execute(search);
            if (result.getTotal()==null)return null;
            return getAptitude(result.getFirstHit(Aptitude.class));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * add a new behavior to the Aptitude
     *
     * @param behaviorDto a JSON with the structure of the Behavior, without the ID
     * @param aptitudeId  the ID of the Aptitude in which you are adding the Behavior
     * @return The behavior you just added, now with its ID
     */
    @Override
    public Behavior addBehavior(BehaviorDto behaviorDto, String aptitudeId) {
        Aptitude aptitude;
        aptitude = findById(aptitudeId);
        Behavior behavior = new Behavior((long) (aptitude.getBehaviors().size() + 1), behaviorDto.getEs(), behaviorDto.getEn());
        aptitude.addBehavior(behavior);
        save(aptitude);//TODO revisar que esto si funciona como lo imagino
        return behavior;

    }

    /**
     * finds one specific behavior in the DB
     *
     * @param id         the ID of the Aptitude the Behavior is in
     * @param behaviorId the id of the Behavior you are looking for
     * @return Behavior schemed JSON with the data of the behavior you looked for
     */
    public Behavior findBehaviorById(String id, String behaviorId) {
        Aptitude aptitude;
        aptitude = findById(id);
        List<Behavior> behaviors = aptitude.getBehaviors();
        for (Behavior behavior : behaviors) {
            if (behavior.getId().equals(Long.getLong(behaviorId))) {
                return behavior;

            }

        }
        return null;

    }

    /**
     * deletes a specific behavior from a Aptitude
     *
     * @param id         this is the id of the Aptitude containing the Behavior
     * @param behaviorId this is the id of the behavior you want to delete
     * @return an Aptitude without the Behavior specified
     */
    @Override
    public Aptitude deleteBehavior(String id, String behaviorId) {
        if (findBehaviorById(id, behaviorId) == null) return null;
        else {
            Aptitude aptitude;
            aptitude = findById(id);
            List<Behavior> behaviors = aptitude.getBehaviors();
            for (Behavior behavior : behaviors) {
                if (behavior.getId().equals(Long.getLong(behaviorId))) {
                    behaviors.remove(behavior);
                    aptitude.setBehaviors(behaviors);
                    break;
                }
            }
            save(aptitude);
            return aptitude;
        }

    }
}
