package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.Person;
import co.com.psl.evaluacionser.persistence.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This is the class that will interact with both the PersonController and the PersonRepository, its used as a way
 * to avoid direct interaction between the controller and the repo, this class could also be used to apply
 * transformation methods or to call other services which shouldn't be called on either the repo or the controller
 */
@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAllPeople() {
        List<Person> people = personRepository.findAll();

        if (people == null) {
            return null;
        }


        return people;
    }

}
