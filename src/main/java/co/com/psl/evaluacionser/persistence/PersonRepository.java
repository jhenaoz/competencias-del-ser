package co.com.psl.evaluacionser.persistence;

import java.util.List;

import co.com.psl.evaluacionser.domain.Person;

public interface PersonRepository {

	List<Person> findAll();
}