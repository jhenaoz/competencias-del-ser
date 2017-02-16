package co.com.psl.evaluacionser.persistence;

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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ElasticsearchAptitudeRepository implements AptitudeRepository {

    private static final String APTITUDE_INDEX_NAME = "aptitude";
    private static final String APTITUDE_TYPE_NAME = "aptitude";

    @Override
    public Aptitude save(Aptitude aptitude) {
        Index index = new Index.Builder(aptitude).index(APTITUDE_INDEX_NAME).type(APTITUDE_TYPE_NAME).build();
        try {
            JestClientUtils.getClient().execute(index);
            return aptitude;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aptitude> findAll() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.sort("id", SortOrder.ASC);

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(APTITUDE_INDEX_NAME).build();

        try {
            SearchResult result = JestClientUtils.getClient().execute(search);
            List<Hit<Aptitude, Void>> aptitudes = result.getHits(Aptitude.class);
            return aptitudes.stream().map(this::getAptitude).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Aptitude getAptitude(Hit<Aptitude, Void> hit) {
        if (hit == null)
            return null;

        return hit.source;
    }

    @Override
    public Aptitude findById(String id) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("_id", id));

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(APTITUDE_INDEX_NAME).build();

        try {
            SearchResult result = JestClientUtils.getClient().execute(search);
            return getAptitude(result.getFirstHit(Aptitude.class));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public Behavior addBehavior(BehaviorDto behaviorDto, String aptitudeId) {
        Aptitude aptitude;
        aptitude = findById(aptitudeId);
        Behavior behavior = new Behavior((long) (aptitude.getBehaviors().size() + 1), behaviorDto.getEs(), behaviorDto.getEn());
        aptitude.addBehavior(behavior);
        save(aptitude);//TODO revisar que esto si funciona como lo imagino
        return behavior;

    }

        //TODO
        private Aptitude updateAptitude(){
        return null;
        }

    public Behavior findBehaviorById(String id, String behaviorId) {
        Aptitude aptitude;
        aptitude = findById(id);
        return aptitude.getBehaviors().get(Integer.parseInt(behaviorId));

    }


}
