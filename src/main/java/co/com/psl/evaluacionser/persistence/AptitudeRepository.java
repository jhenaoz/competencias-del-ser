package co.com.psl.evaluacionser.persistence;

import java.util.List;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorDto;

public interface AptitudeRepository {

	Behavior addBehavior(BehaviorDto behaviorDto, String aptitudeId);

	Aptitude deleteBehavior(String id, String behaviorId);

	Aptitude save(Aptitude aptitude);

	List<Aptitude> findAll();

	Aptitude findById(String id);

	List<Behavior> findAllBehaviors(String aptitudeId);

	Behavior findBehaviorById(String aptitudeId, String id);

	Behavior updateBehaviorById(String id, Behavior behavior);
}
