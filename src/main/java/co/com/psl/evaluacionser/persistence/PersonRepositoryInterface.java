package co.com.psl.evaluacionser.persistence;

import java.util.List;

import co.com.psl.evaluacionser.domain.Person;

public interface PersonRepositoryInterface {

	List<Person> findAll();
}