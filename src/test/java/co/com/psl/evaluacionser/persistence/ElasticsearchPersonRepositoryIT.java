package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ElasticsearchPersonRepositoryIT {

    Person person1 = new Person();
    Person person2 = new Person();
    Person returnedPersonFromSave = new Person();
    Person save1 = new Person();
    boolean person1WasDeleted;
    boolean Person2WasDeleted;

    @Autowired
    private ElasticsearchPersonRepository elasticsearchPersonRepository;

    @Before
    public void setUp() throws Exception {
        person1.setName("jhon doe");
        person1.setId("125874");


        person2.setId("12547514");
        person2.setName("jane doe");

        returnedPersonFromSave = elasticsearchPersonRepository.save(person1);
        save1 = elasticsearchPersonRepository.save(person2);
    }

    @After
    public void tearDown() throws Exception {

        elasticsearchPersonRepository.deletePersonByIdAndName(person1.getId(), person1.getName());
        elasticsearchPersonRepository.deletePersonByIdAndName(person2.getId(), person2.getName());

    }

    @Test
    public void findAll() throws Exception {
        List<Person> personList = elasticsearchPersonRepository.findAll();

        boolean listContainsPerson1 = false;
        for (Person personInList : personList) {

            if (personInList.getId().equals(person1.getId())) {
                listContainsPerson1 = true;
            }

        }

        boolean listContainsPerson2 = false;
        for (Person personInList : personList) {

            if (personInList.getId().equals(person2.getId())) {
                listContainsPerson2 = true;
            }

        }

        assertEquals(true, listContainsPerson1);
        assertEquals(true, listContainsPerson2);

    }

    @Test
    public void save() throws Exception {
        assertEquals(person1.getId(), returnedPersonFromSave.getId());
        assertEquals(person1.getName(), returnedPersonFromSave.getName());
        assertEquals(person2.getId(), save1.getId());
        assertEquals(person2.getName(), save1.getName());

    }

    @Test
    public void findPersonById() throws Exception {
        Person foundPerson = elasticsearchPersonRepository.findPersonById(person1.getId());
        assertEquals(person1.getId(), foundPerson.getId());

    }

    @Test
    public void deletePersonByIdAndName() throws Exception {

        person1WasDeleted = elasticsearchPersonRepository.deletePersonByIdAndName(person1.getId(), person1.getName());
        Person2WasDeleted = elasticsearchPersonRepository.deletePersonByIdAndName(person2.getId(), person2.getName());

        assertEquals(true, person1WasDeleted);
        assertEquals(true, Person2WasDeleted);

    }

}