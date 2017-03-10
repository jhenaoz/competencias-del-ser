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

    /*
      Local person are the ones i am creating locally for the test
      the other person are the ones that are returned from the save method (returnedSavePerson)
      and one person im going to save in the DB (personToSave)
     */
    Person localPerson1 = new Person();
    Person localPerson2 = new Person();
    Person returnedPersonFromSave = new Person();
    Person personToSave = new Person();
    boolean person1WasDeleted;
    boolean Person2WasDeleted;

    @Autowired
    private ElasticsearchPersonRepository elasticsearchPersonRepository;

    @Before
    public void setUp() throws Exception {
        localPerson1.setName("john doe");
        localPerson1.setId("125874");


        localPerson2.setId("12547514");
        localPerson2.setName("jane doe");

        returnedPersonFromSave = elasticsearchPersonRepository.save(localPerson1);
        personToSave = elasticsearchPersonRepository.save(localPerson2);
    }

    @After
    public void tearDown() throws Exception {

        elasticsearchPersonRepository.deletePersonByIdAndName(localPerson1.getId(), localPerson1.getName());
        elasticsearchPersonRepository.deletePersonByIdAndName(localPerson2.getId(), localPerson2.getName());

    }

    @Test
    public void findAll() throws Exception {
        List<Person> personList = elasticsearchPersonRepository.findAll();

        boolean listContainsPerson1 = false;
        for (Person personInList : personList) {

            if (personInList.getId().equals(localPerson1.getId())) {
                listContainsPerson1 = true;
            }

        }

        boolean listContainsPerson2 = false;
        for (Person personInList : personList) {

            if (personInList.getId().equals(localPerson2.getId())) {
                listContainsPerson2 = true;
            }

        }

        assertEquals(true, listContainsPerson1);
        assertEquals(true, listContainsPerson2);

    }

    @Test
    public void save() throws Exception {
        assertEquals(localPerson1.getId(), returnedPersonFromSave.getId());
        assertEquals(localPerson1.getName(), returnedPersonFromSave.getName());
        assertEquals(localPerson2.getId(), personToSave.getId());
        assertEquals(localPerson2.getName(), personToSave.getName());

    }

    @Test
    public void findPersonById() throws Exception {
        Person foundPerson = elasticsearchPersonRepository.findPersonById(localPerson1.getId());
        assertEquals(localPerson1.getId(), foundPerson.getId());

    }

    @Test
    public void deletePersonByIdAndName() throws Exception {

        person1WasDeleted = elasticsearchPersonRepository.deletePersonByIdAndName(localPerson1.getId(), localPerson1.getName());
        Person2WasDeleted = elasticsearchPersonRepository.deletePersonByIdAndName(localPerson2.getId(), localPerson2.getName());

        assertEquals(true, person1WasDeleted);
        assertEquals(true, Person2WasDeleted);

    }

}