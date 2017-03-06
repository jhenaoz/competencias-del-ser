package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Person;
import co.com.psl.evaluacionser.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

    @Mock
    private PersonService mockPersonService;
    private PersonController personController;

    @Before
    public void setup() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("1", "Juan"));
        personList.add(new Person("2", "Pedro"));

        when(mockPersonService.findAllPeople()).thenReturn(personList);
        personController = new PersonController(mockPersonService);
    }

    @Test
    public void getAllPeopleReturnHttpStatusOK() {
        ResponseEntity<List<Person>> peopleResponseEntity = personController.getAllPeople();
        HttpStatus responseStatus = peopleResponseEntity.getStatusCode();

        assertEquals("OK", responseStatus.name());
    }

    @Test
    public void controllerWithNullParameterReturnNotFound() {
        when(mockPersonService.findAllPeople()).thenReturn(null);

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
        assertEquals("Pedro", person2.getName());
    }

}
