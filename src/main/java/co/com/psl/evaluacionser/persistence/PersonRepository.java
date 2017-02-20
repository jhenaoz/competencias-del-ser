package co.com.psl.evaluacionser.persistence;

import java.util.List;
import co.com.psl.evaluacionser.domain.Person;

/**
 * This interface is the firm for the person implementations
 */
public interface PersonRepository {

	/**
	 * Save the person in the database
	 * @param person entity
	 * @return person from the database
	 */
	Person save(Person person);

	List<Person> findAll();
}