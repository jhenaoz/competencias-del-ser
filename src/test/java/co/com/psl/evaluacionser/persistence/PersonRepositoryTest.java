package co.com.psl.evaluacionser.persistence;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.psl.evaluacionser.domain.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTest {

	@Autowired
	PersonRepositoryInterface personRepository;
	
	@Test
	public void personListNotNull() {
		List<Person> personList = personRepository.findAll();
		assertNotNull(personList);
	}
	
	@Test
	public void personList() {
		List<Person> personList = personRepository.findAll();
		assertTrue(personList.size() > 0);
	}

}
