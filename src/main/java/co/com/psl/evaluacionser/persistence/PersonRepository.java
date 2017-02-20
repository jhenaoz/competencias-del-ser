package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Person;

import java.util.List;

// This interface is the firm for the person implementations
public interface PersonRepository {

    Person save(Person person);

    List<Person> findAll();
}