package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Person;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface PersonRepository {


    public List<Person> findAll();

}
