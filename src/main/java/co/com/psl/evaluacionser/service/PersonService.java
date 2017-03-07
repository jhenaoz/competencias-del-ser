package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.Person;
import co.com.psl.evaluacionser.persistence.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAllPeople() {
        List<Person> people = personRepository.findAll();

        if (people == null) {
            return null;
        }


        return people;
    }

}
