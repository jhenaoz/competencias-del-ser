package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.persistence.AptitudeRepository;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import co.com.psl.evaluacionser.service.dto.BehaviorDto;
import co.com.psl.evaluacionser.service.transformer.AptitudeTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static co.com.psl.evaluacionser.service.transformer.AptitudeTransformer.convertToDto;


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
    @RequestMapping(method = RequestMethod.GET)
    private ResponseEntity<List<AptitudeDto>> getAptitudes() {
        List<Aptitude> aptitudes = aptitudeRepository.findAll();

        if (aptitudes.isEmpty())
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);

        List<AptitudeDto> aptitudesDto = aptitudes.stream().map(AptitudeTransformer::convertToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(aptitudesDto, HttpStatus.OK);
    }

    /**
     * this method provides a mapping to get one specific aptitude via an
     * URL ID, the aptitude you get also contains its Behaviors
     *
     * @param id the ID corresponding to the Aptitude you want
     * @return Json schema of the specific {id} Aptitude
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private ResponseEntity<AptitudeDto> getAptitudeById(@PathVariable("id") String id) {
        Aptitude aptitudeFound = aptitudeRepository.findById(id);

        if (aptitudeFound == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(convertToDto(aptitudeFound), HttpStatus.OK);

    }

    /**
     * via this requestMethod you can get all the Behaviors corresponding to an Aptitude (but you dont get the Aptitude INFO)
     *
     * @param id the ID of the Aptitude you want to get the Behaviors from
     * @return the JSON corresponding to the Behaviors from the {id} specified Aptitude
     */
    @RequestMapping(value = "/{id}/behavior", method = RequestMethod.GET)
    private ResponseEntity<List<Behavior>> getBehaviors(@PathVariable("id") String id) {
        List<Behavior> behaviorsFound = aptitudeRepository.findAllBehaviors(id);

        if (behaviorsFound.isEmpty())
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(behaviorsFound, HttpStatus.OK);
    }

    /**
     * Gets the JSON of a specified (via Aptitude ID and BehaviorID) behavior
     *
     * @param id         URL param that specifies the aptitude you want to get the behavior from
     * @param behaviorId URL param that specifies the specific behavior from the aptitude you want
     * @return returns a Response Entity with the HttpStatus of the operation, HttpStatus.OK if Successful
     */
    @RequestMapping(value = "/{id}/behavior/{behaviorId}", method = RequestMethod.GET)
    private ResponseEntity<Behavior> getBehaviorById(@PathVariable("id") String id,
                                                     @PathVariable("behaviorId") String behaviorId) {
        Behavior behaviorFound = aptitudeRepository.findBehaviorById(id, behaviorId);

        if (behaviorFound == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(behaviorFound, HttpStatus.OK);
    }

    /**
     * Creates a new behavior for the specified aptitude, the behavior has to be sent as a JSON,and the JSON must have the fields specified below
     * the ID will be auto assigned by the DB
     * {
     * "sp": "spanish text",
     * "en": "english text"
     * }
     *
     * @param id          the id from the aptitude the behavior is in
     * @param behaviorDto the new behavior your are creating
     * @return ResponseEntity according
     */

    @RequestMapping(value = "/{id}/behavior", headers = "Accept=application/json", method = RequestMethod.POST)
    private ResponseEntity<Behavior> saveBehavior(@PathVariable("id") String id, @RequestBody BehaviorDto behaviorDto) {
        if (aptitudeRepository.findById(id) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(aptitudeRepository.addBehavior(behaviorDto, id), HttpStatus.CREATED);


    }

    /**
     * updates a behavior
     *
     * @param id          the id from the aptitude the behavior is in
     * @param behaviorId  the specific behavior you are modifying
     * @param behaviorDto the behavior your are overwriting the old one with
     * @return ResponseEntity according
     */
    @RequestMapping(value = "/{id}/behavior/{behaviorId}", method = RequestMethod.PUT)
    private ResponseEntity modifyBehavior(@PathVariable("id") String id, @PathVariable("behaviorId") String behaviorId, @RequestBody BehaviorDto behaviorDto) {

        if (aptitudeRepository.findById(id) == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (aptitudeRepository.findBehaviorById(id, behaviorId) == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        Behavior behavior = new Behavior(behaviorId, behaviorDto.getEn(), behaviorDto.getEs());
        return new ResponseEntity(aptitudeRepository.updateBehaviorById(id, behavior), HttpStatus.ACCEPTED);
    }

    /**
     * deletes the behavior in the corresponding URL
     *
     * @param id         the id of the Aptitude
     * @param behaviorId the id of the Behaviors to delete
     * @return should return the Aptitude without the Behaviors
     */
    @RequestMapping(value = "/{id}/behavior/{behaviorId}", method = RequestMethod.DELETE)
    private ResponseEntity deleteBehavior(@PathVariable("id") String id, @PathVariable("behaviorId") String behaviorId) {
        if (aptitudeRepository.findById(id) == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (aptitudeRepository.findBehaviorById(id, behaviorId) == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        return new ResponseEntity(aptitudeRepository.deleteBehavior(id, behaviorId), HttpStatus.ACCEPTED);
    }
}

