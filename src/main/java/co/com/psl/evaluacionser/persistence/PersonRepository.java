package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Person;

public interface PersonRepository {

    Person save(Person person);

}
