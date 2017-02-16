package co.com.psl.evaluacionser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.psl.evaluacionser.domain.Person;
import co.com.psl.evaluacionser.persistence.PersonRepository;

@RestController
@RequestMapping(value = "/person")
public class PersonController {

	@Autowired
	private PersonRepository personRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Person> getAllPeople(){
		return personRepository.findAll();
	}
}
