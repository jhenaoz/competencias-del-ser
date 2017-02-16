package co.com.psl.evaluacionser.controller;

import static co.com.psl.evaluacionser.controller.AptitudeDtoTransformer.convertToDto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.AptitudeDto;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.persistence.AptitudeRepository;

@RestController
@RequestMapping(value = "/aptitude")
public class AptitudeController {

	@Autowired
	private AptitudeRepository aptitudeRepository;

	/**
	 * Mapping for getting all the aptitudes (with their respective behaviors)
	 *
	 * @return a JSON with all the data according to ElasticSearch
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	private ResponseEntity<List<AptitudeDto>> getAptitudes() {
		List<AptitudeDto> aptitudes = aptitudeRepository.findAll().stream().map(AptitudeDtoTransformer::convertToDto)
				.collect(Collectors.toList());

		return new ResponseEntity<List<AptitudeDto>>(aptitudes, HttpStatus.OK);
	}

	/**
	 * This method provides a mapping to get one specific aptitude via an URL
	 * ID, the aptitude you get also contains its Behaviors
	 *
	 * @param id
	 *            the ID corresponding to the Aptitude you want
	 * @return Json schema of the specific {id} Aptitude
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private ResponseEntity<AptitudeDto> getAptitudeById(@PathVariable("id") String id) {
		Aptitude aptitudeFound = aptitudeRepository.findById(id);

		if (aptitudeFound == null)
			return new ResponseEntity<AptitudeDto>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<AptitudeDto>(convertToDto(aptitudeFound), HttpStatus.OK);

	}

	/**
	 * Via this requestMethod you can get all the Behaviors corresponding to an
	 * Aptitude (but you dont get the Aptitude INFO)
	 *
	 * @param id
	 *            the ID of the Aptitude you want to get the Behaviors from
	 * @return the JSON corresponding to the Behaviors from the {id} specified
	 *         Aptitude
	 */
	@RequestMapping(value = "/{id}/behavior", method = RequestMethod.GET)
	private ResponseEntity<List<Behavior>> getBehaviors(@PathVariable("id") String id) {
		List<Behavior> behaviorsFound = aptitudeRepository.findAllBehaviors(id);

		if (behaviorsFound == null)
			return new ResponseEntity<List<Behavior>>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<List<Behavior>>(behaviorsFound, HttpStatus.OK);
	}

	/**
	 * Gets the JSON of a specified (via Aptitude ID and BehaviorID) behavior
	 *
	 * @param id
	 *            URL param that specifies the aptitude you want to get the
	 *            behavior from
	 * @param behaviorId
	 *            URL param that specifies the specific behavior from the
	 *            aptitude you want
	 * @return
	 */
	@RequestMapping(value = "/{id}/behavior/{behaviorId}", method = RequestMethod.GET)
	private ResponseEntity<Behavior> getBehaviorById(@PathVariable("id") String id,
													 @PathVariable("behaviorId") String behaviorId) {
		Behavior behaviorFound = aptitudeRepository.findBehaviorById(id, behaviorId);

		if (behaviorFound == null)
			return new ResponseEntity<Behavior>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<Behavior>(behaviorFound, HttpStatus.OK);

	}

}
