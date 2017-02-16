package co.com.psl.evaluacionser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.persistence.AptitudeRepository;

@RestController
public class AptitudeController {

	@Autowired
	private AptitudeRepository aptitudeRepository;

	// get the list of all available aptitudes
	@RequestMapping(value = "/aptitude", method = RequestMethod.GET)
	private ResponseEntity<List<Aptitude>> getAptitudes() {
		return new ResponseEntity<List<Aptitude>>(aptitudeRepository.findAll(), HttpStatus.OK);
	}

	// get a specific aptitude using an ID
	@RequestMapping(value = "/aptitude/{id}", method = RequestMethod.GET)
	private ResponseEntity<Aptitude> getAptitudeById(@PathVariable("id") String id) {
		Aptitude aptitudeFound = aptitudeRepository.findById(id);

		if (aptitudeFound == null)
			return new ResponseEntity<Aptitude>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<Aptitude>(aptitudeFound, HttpStatus.OK);

	}

	// get all behavior from a specific aptitude
	@RequestMapping(value = "/aptitude/{id}/behavior", method = RequestMethod.GET)
	private ResponseEntity<List<Behavior>> getBehaviors(@PathVariable("id") String id) {
		List<Behavior> behaviorsFound = aptitudeRepository.findAllBehaviors(id);

		if (behaviorsFound == null)
			return new ResponseEntity<List<Behavior>>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<List<Behavior>>(behaviorsFound, HttpStatus.OK);
	}

	// get one specific behavior using an ID
	@RequestMapping(value = "/aptitude/{id}/behavior/{behaviorId}", method = RequestMethod.GET)
	private ResponseEntity<Behavior> getBehaviorById(@PathVariable("id") String id,
			@PathVariable("behaviorId") String behaviorId) {
		Behavior behaviorFound = aptitudeRepository.findBehaviorById(id, behaviorId);

		if (behaviorFound == null)
			return new ResponseEntity<Behavior>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<Behavior>(behaviorFound, HttpStatus.OK);

	}

}
