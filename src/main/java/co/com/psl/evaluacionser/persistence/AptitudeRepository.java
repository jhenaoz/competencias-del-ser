package co.com.psl.evaluacionser.persistence;

import java.util.List;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Person;

public interface AptitudeRepository {

    void save(Aptitude aptitude);
    List<Person> findAll();
}
