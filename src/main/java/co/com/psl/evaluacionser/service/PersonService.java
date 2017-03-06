package co.com.psl.evaluacionser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.psl.evaluacionser.domain.Person;
import co.com.psl.evaluacionser.persistence.PersonRepository;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAllPeople() {
        List<Person> people = personRepository.findAll();

        if (people == null)
            return null;

        return people;
    }

}
