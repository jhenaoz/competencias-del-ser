package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Person;
import co.com.psl.evaluacionser.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * PersonController is the class that control the people requests
 */
//Controller for the person requests

@CrossOrigin
@RestController
@RequestMapping(value = "/api/person")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(final PersonService personService) {
        this.personService = personService;
    }

    /**
     * This method returns the list of all employees in the elasticsearch
     *
     * @return Response entity with the status of the request, ok if it exists or not_found if it is null,
     *         and if it exists returns the list
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getAllPeople() {
        List<Person> people = personService.findAllPeople();

        if (people == null) {
            return new ResponseEntity<List<Person>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Person>>(people, HttpStatus.OK);
    }
}
