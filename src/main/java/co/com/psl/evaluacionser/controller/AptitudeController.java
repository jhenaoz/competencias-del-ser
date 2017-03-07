package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.service.AptitudeService;
import co.com.psl.evaluacionser.service.dto.AptitudeDto;
import co.com.psl.evaluacionser.service.dto.BehaviorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/aptitude")
public class AptitudeController {

    private AptitudeService aptitudeService;

    @Autowired
    public AptitudeController(final AptitudeService aptitudeService) {
        this.aptitudeService = aptitudeService;
    }

    /**
     * Mapping for getting all the aptitudes (with their respective behaviors)
     *
     * @return a JSON with all the data according to ElasticSearch
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AptitudeDto>> getAptitudes() {
        List<AptitudeDto> aptitudes = aptitudeService.findAllAptitudes();

        if (aptitudes == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(aptitudes, HttpStatus.OK);
    }

    /**
     * this method provides a mapping to get one specific aptitude via an
     * URL ID, the aptitude you get also contains its Behaviors
     *
     * @param id the ID corresponding to the Aptitude you want
     * @return Json schema of the specific {id} Aptitude
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<AptitudeDto> getAptitudeById(@PathVariable("id") String id) {
        AptitudeDto aptitudeFound = aptitudeService.findAptitudeById(id);

        if (aptitudeFound == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(aptitudeFound, HttpStatus.OK);
    }

    /**
     * via this requestMethod you can get all the Behaviors corresponding
     * to an Aptitude (but you don't get the Aptitude INFO)
     *
     * @param id the ID of the Aptitude you want to get the Behaviors from
     * @return the JSON corresponding to the Behaviors from the {id} specified Aptitude
     */
    @RequestMapping(value = "/{id}/behavior", method = RequestMethod.GET)
    public ResponseEntity<List<Behavior>> getBehaviors(@PathVariable("id") String id) {
        List<Behavior> behaviorsFound = aptitudeService.findAptitudeBehaviors(id);

        if (behaviorsFound == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

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
    public ResponseEntity<Behavior> getBehaviorById(@PathVariable("id") String id,
                                                     @PathVariable("behaviorId") String behaviorId) {
        Behavior behaviorFound = aptitudeService.findAptitudeBehaviorById(id, behaviorId);

        if (behaviorFound == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(behaviorFound, HttpStatus.OK);
    }

    /**
     * Creates a new behavior for the specified aptitude, the behavior has to be sent as a JSON,
     * and the JSON must have the fields specified below. The ID will be auto assigned by the DB
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
    public ResponseEntity<Behavior> saveBehavior(@PathVariable("id") String id,
                                                  @RequestBody BehaviorDto behaviorDto) {
        Behavior behaviorSaved = aptitudeService.createAptitudeBehavior(id, behaviorDto);

        if (behaviorSaved == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(behaviorSaved, HttpStatus.CREATED);
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
    public ResponseEntity<Behavior> modifyBehavior(@PathVariable("id") String id,
                                          @PathVariable("behaviorId") String behaviorId,
                                          @RequestBody BehaviorDto behaviorDto) {
        Behavior behavior = aptitudeService.updateAptitudeBehavior(id, behaviorId, behaviorDto);

        if (behavior == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(behavior, HttpStatus.ACCEPTED);
    }

    /**
     * deletes the behavior in the corresponding URL
     *
     * @param id         the id of the Aptitude
     * @param behaviorId the id of the Behaviors to delete
     * @return should return the Aptitude without the Behaviors
     */
    @RequestMapping(value = "/{id}/behavior/{behaviorId}", method = RequestMethod.DELETE)
    public ResponseEntity<Aptitude> deleteBehavior(@PathVariable("id") String id,
                                          @PathVariable("behaviorId") String behaviorId) {
        Aptitude aptitudeDeleted = aptitudeService.deleteAptitudeBehavior(id, behaviorId);

        if (aptitudeDeleted == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(aptitudeDeleted, HttpStatus.ACCEPTED);
    }
}

