package co.com.psl.evaluacionser.domain;

import io.searchbox.annotations.JestId;

/**
 * This class represents the person in an object
 * @author salveara
 *
 */
public class Person {
	
    private String id;

    private String name;

    public Person() {}

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
