package co.com.psl.evaluacionser.persistence;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.psl.evaluacionser.domain.Person;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;

@Component
public class ElasticsearchPersonRepository implements PersonRepository {

	private static final String PERSON_INDEX_NAME = "person";
	private static final String PERSON_TYPE_NAME = "employee";

	@Autowired
	private JestClient client;

	/**
	 * Receives one person and saves it in the DB
	 *
	 * @param person
	 *            the person you want to save
	 * @return the person you just saved, now with its ID included
	 */
	@Override
	public Person save(Person person) {
		Index index = new Index.Builder(person).index(PERSON_INDEX_NAME).type(PERSON_TYPE_NAME).build();
		try {
			client.execute(index);
			return person;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
