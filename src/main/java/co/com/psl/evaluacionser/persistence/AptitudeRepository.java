package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorDto;

import java.util.List;


public interface AptitudeRepository {

    Aptitude save(Aptitude aptitude);

    List<Aptitude> findAll();

    Aptitude findById(String id);

    Behavior addBehavior(BehaviorDto behaviorDto, String id);

    Behavior findBehaviorById(String id, String behaviorId);
}