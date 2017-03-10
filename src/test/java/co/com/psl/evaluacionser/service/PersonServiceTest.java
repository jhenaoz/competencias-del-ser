package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.Person;
import co.com.psl.evaluacionser.persistence.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository mockPersonRepository;
    private PersonService personService;

    @Before
    public void setup() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("1", "Juan"));
        personList.add(new Person("2", "Pedro"));

        when(mockPersonRepository.findAll()).thenReturn(personList);
        personService = new PersonService(mockPersonRepository);
    }

    @Test
    public void findAllPeopleShouldReturnArraySize2() {
        List<Person> people = personService.findAllPeople();
        assertEquals(2, people.size());

        Person person1 = people.get(0);
        assertEquals("1", person1.getId());
        assertEquals("Juan", person1.getName());
    }

    @Test
    public void findAllPeopleWithNullArrayReturnsNull() {
        when(mockPersonRepository.findAll()).thenReturn(null);
        assertNull(personService.findAllPeople());
    }

}
