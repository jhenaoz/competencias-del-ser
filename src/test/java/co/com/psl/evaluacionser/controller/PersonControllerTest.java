package co.com.psl.evaluacionser.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.com.psl.evaluacionser.domain.Person;
import co.com.psl.evaluacionser.persistence.PersonRepository;
import co.com.psl.evaluacionser.service.PersonService;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

    @Mock
    private PersonRepository mockPersonRepository;
    private PersonController personController;

    @Before
    public void setup() {
        List<Person> personList = setupPerson();

        when(mockPersonRepository.findAll()).thenReturn(personList);
        PersonService personService = new PersonService(mockPersonRepository);
        personController = new PersonController(personService);
    }

    private List<Person> setupPerson() {
        List<Person> personList = new ArrayList<>();

        personList.add(new Person("1", "Primero"));
        personList.add(new Person("2", "Segundo"));

        return personList;
    }

    @Test
    public void getAllPeopleReturnHttpStatusOK() {
        ResponseEntity<List<Person>> peopleResponseEntity = personController.getAllPeople();
        HttpStatus responseStatus = peopleResponseEntity.getStatusCode();

        assertEquals("OK", responseStatus.name());
    }

    @Test
    public void controllerWithNullParameterReturnNotFound() {
        when(mockPersonRepository.findAll()).thenReturn(null);

        ResponseEntity<List<Person>> peopleResponseEntity = personController.getAllPeople();
        HttpStatus responseStatus = peopleResponseEntity.getStatusCode();

        assertEquals("NOT_FOUND", responseStatus.name());
    }

    @Test
    public void getAllPeopleShouldReturnPersonArraySize2() {
        ResponseEntity<List<Person>> peopleResponseEntity = personController.getAllPeople();
        List<Person> allPersons = peopleResponseEntity.getBody();

        assertEquals(2, allPersons.size());

        Person person2 = allPersons.get(1);
        assertEquals("2", person2.getId());
        assertEquals("Segundo", person2.getName());
    }

}
