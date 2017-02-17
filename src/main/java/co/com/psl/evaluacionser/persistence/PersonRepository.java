package co.com.psl.evaluacionser.persistence;

import java.util.List;
import co.com.psl.evaluacionser.domain.Person;

// This interface is the firm for the person implementations
public interface PersonRepository {

	Person save(Person person);

	List<Person> findAll();
}