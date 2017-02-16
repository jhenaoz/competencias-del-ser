package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Person;
import io.searchbox.core.Index;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ElasticsearchPersonRepository implements PersonRepository {

    private static final String PERSON_INDEX_NAME = "person";
    private static final String PERSON_TYPE_NAME = "employee";


    public Person save(Person person) {
        Index index = new Index.Builder(person).index(PERSON_INDEX_NAME).type(PERSON_TYPE_NAME).build();
        try {
            JestClientUtils.getClient().execute(index);
            return person;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> findAll() {
        return null;
    }
}
