package co.com.psl.evaluacionser.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonTest {

    @Test
    public void constructorBySetter() {
        Person person = new Person();
        person.setId("21");
        person.setName("Juanito Perez");
        assertEquals("21", person.getId());
        assertEquals("Juanito Perez", person.getName());
    }

    @Test
    public void constructorIdAndName() {
        Person person = new Person("21", "Juanito Perez");
        assertEquals("21", person.getId());
        assertEquals("Juanito Perez", person.getName());
    }

}
