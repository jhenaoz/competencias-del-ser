package co.com.psl.evaluacionser.domain;

/**
 * Person class represents the person in an object
 */
public class Person {

	private String id;

	private String name;

	public Person() {
	}
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
