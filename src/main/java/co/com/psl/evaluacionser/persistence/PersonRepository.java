package co.com.psl.evaluacionser.persistence;

import java.util.List;

import co.com.psl.evaluacionser.domain.Person;
/**
 * This interface is the firm for the person implementations
 * @author salveara
 *
 */
public interface PersonRepository {

	List<Person> findAll();
}