package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchPersonRepositoryIT {

    @Autowired
    private ElasticsearchPersonRepository elasticsearchPersonRepository;

    @Test
    public void personSaveAndDelete() {

        Person person = new Person();
        person.setName("el señor fulano de tal");
        person.setId("1");

        Person savedPerson = elasticsearchPersonRepository.save(person);

        assertEquals("el señor fulano de tal", savedPerson.getName());

        Person foundPerson = elasticsearchPersonRepository.findPersonById(person.getId());
        assertEquals("1", foundPerson.getId());

        boolean wasDeleted;
        wasDeleted = elasticsearchPersonRepository.deletePersonByIdAndName(person.getId(), person.getName());
        assertEquals(true, wasDeleted);

    }

    @Test
    public void personFindAll() {
        Person person = new Person();
        person.setId("1425");
        person.setName("el señor fulano");

        Person person2 = new Person();
        person2.setId("1254");
        person2.setName("la señorita fulana");

        elasticsearchPersonRepository.save(person);
        elasticsearchPersonRepository.save(person2);
        List<Person> personList = elasticsearchPersonRepository.findAll();

        boolean contains1 = false;
        for (Person personInList : personList) {

            if (personInList.getId().equals(person.getId())) {
                contains1 = true;
            }

        }

        boolean contains2 = false;
        for (Person personInList : personList) {

            if (personInList.getId().equals(person2.getId())) {
                contains2 = true;
            }

        }

        assertEquals(true, contains1);
        assertEquals(true, contains2);

        Person personById = elasticsearchPersonRepository.findPersonById(person.getId());
        Person personById2 = elasticsearchPersonRepository.findPersonById(person2.getId());

        assertEquals(person.getName(), personById.getName());
        assertEquals(person.getId(), personById.getId());

        assertEquals(person2.getId(), personById2.getId());
        assertEquals(person2.getName(), personById2.getName());

        boolean wasDeleted1 = elasticsearchPersonRepository.deletePersonByIdAndName(person.getId(), person.getName());
        boolean wasDeleted2 = elasticsearchPersonRepository.deletePersonByIdAndName(person2.getId(), person2.getName());

        assertEquals(true, wasDeleted1);
        assertEquals(true, wasDeleted2);
    }

    @Test
    public void personWithId789654123NotFound() {
        Person personById = elasticsearchPersonRepository.findPersonById("789654123");
        assertNull(personById);
    }
}
