package co.com.psl.evaluacionser.persistence;

import java.util.List;

import co.com.psl.evaluacionser.domain.Aptitude;

public interface AptitudeRepository {

	public Aptitude save(Aptitude aptitude);

	public List<Aptitude> findAll();

	public Aptitude findById(String id);

}
