package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorDto;

import java.util.List;

public interface AptitudeRepository {

    Aptitude save(Aptitude aptitude);

    List<Aptitude> findAll();

    Aptitude findById(String id);

    List<Behavior> findAllBehaviors(String aptitudeId);

    Behavior findBehaviorById(String aptitudeId, String id);

    Behavior addBehavior(BehaviorDto behaviorDto, String aptitudeId);

    Aptitude deleteBehavior(String id, String behaviorId);

    Behavior updateBehaviorById(String id, Behavior behavior);
}
