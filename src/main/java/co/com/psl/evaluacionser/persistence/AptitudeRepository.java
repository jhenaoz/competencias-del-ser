package co.com.psl.evaluacionser.persistence;

import java.util.List;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;

public interface AptitudeRepository {

	public Aptitude save(Aptitude aptitude);

	public List<Aptitude> findAll();

	public Aptitude findById(String id);

	public List<Behavior> findAllBehaviors(String aptitudeId);

	public Behavior findBehaviorById(String aptitudeId, String id);

}
