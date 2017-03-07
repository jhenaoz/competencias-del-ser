package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ElasticsearchPersonRepositoryIT {

    Person person = new Person();
    Person person2 = new Person();
    Person save = new Person();
    Person save1 = new Person();
    boolean b;
    boolean b1;

    @Autowired
    private ElasticsearchPersonRepository elasticsearchPersonRepository;

    @Before
    public void setUp() throws Exception {
        person.setName("el señor fulano de tal");
        person.setId("125874");


        person2.setId("12547514");
        person2.setName("la señorita fulana");

        save = elasticsearchPersonRepository.save(person);
        save1 = elasticsearchPersonRepository.save(person2);
    }

    @After
    public void tearDown() throws Exception {

        elasticsearchPersonRepository.deletePersonByIdAndName(person.getId(), person.getName());
        elasticsearchPersonRepository.deletePersonByIdAndName(person2.getId(), person2.getName());

    }

    @Test
    public void findAll() throws Exception {
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

    }

    @Test
    public void save() throws Exception {
        assertEquals(person.getId(), save.getId());
        assertEquals(person.getName(), save.getName());
        assertEquals(person2.getId(), save1.getId());
        assertEquals(person2.getName(), save1.getName());

    }

    @Test
    public void findPersonById() throws Exception {
        Person foundPerson = elasticsearchPersonRepository.findPersonById(person.getId());
        assertEquals(person.getId(), foundPerson.getId());

    }

    @Test
    public void deletePersonByIdAndName() throws Exception {

        b = elasticsearchPersonRepository.deletePersonByIdAndName(person.getId(), person.getName());
        b1 = elasticsearchPersonRepository.deletePersonByIdAndName(person2.getId(), person2.getName());

        assertEquals(true,b);
        assertEquals(true,b1);

    }

}