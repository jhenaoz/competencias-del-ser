package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Aptitude;
import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorDto;
import co.com.psl.evaluacionser.persistence.AptitudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AptitudeController {


    @Autowired
    private AptitudeRepository aptitudeRepository;

    /**
     * mapping for getting all the aptitudes (with their respective behaviors)
     *
     * @return a JSON with all the data according to ElasticSearch
     */
    @RequestMapping(value = "/aptitude", method = RequestMethod.GET)
    private ResponseEntity<List<Aptitude>> getAptitudes() {
        return new ResponseEntity<>(aptitudeRepository.findAll(), HttpStatus.OK);
    }


    /**
     * this method provides a mapping to get one specific aptitude via an
     * URL ID, the aptitude you get also contains its Behaviors
     *
     * @param id the ID corresponding to the Aptitude you want
     * @return Json schema of the specific {id} Aptitude
     */
    @RequestMapping(value = "/aptitude/{id}", method = RequestMethod.GET)
    private ResponseEntity<Aptitude> getAptitudeById(@PathVariable("id") String id) {
        Aptitude aptitudeFound = aptitudeRepository.findById(id);

        if (aptitudeFound == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(aptitudeFound, HttpStatus.OK);

    }

    /**
     * via this requestMethod you can get all the Behaviors corresponding to an Aptitude (but you dont get the Aptitude INFO)
     *
     * @param id the ID of the Aptitude you want to get the Behaviors from
     * @return the JSON corresponding to the Behaviors from the {id} specified Aptitude
     */
    @RequestMapping(value = "/aptitude/{id}/behavior", method = RequestMethod.GET)
    private ResponseEntity<List<Behavior>> getBehaviors(@PathVariable("id") String id) {

        return new ResponseEntity<List<Behavior>>(aptitudeRepository.findById(id).getBehaviors(), HttpStatus.OK);
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

    @RequestMapping(value = "/aptitude/{id}/behavior", headers = "Accept=application/json", method = RequestMethod.POST)
    private ResponseEntity<Behavior> addBehavior(@PathVariable("id") String id, @RequestBody BehaviorDto behaviorDto) {

        return new ResponseEntity<>(aptitudeRepository.addBehavior(behaviorDto, id), HttpStatus.CREATED);
    }

    /**
     * gets the JSON of a specified (via Aptitude ID and BehaviorID) behavior
     *
     * @param id         URL param that specifies the aptitude you want to get the behavior from
     * @param behaviorId URL param that specifies the specific behavior from the aptitude you want
     * @return
     */
    @RequestMapping(value = "/aptitude/{id}/behavior/{behaviorId}", method = RequestMethod.GET)
    private ResponseEntity<Behavior> getBehaviorById(@PathVariable("id") String id,
                                                     @PathVariable("behaviorId") String behaviorId) {

        return new ResponseEntity<>(aptitudeRepository.findBehaviorById(id, behaviorId), HttpStatus.ACCEPTED);
    }

    /**
     * updates a behavior
     *
     * @param id          the id from the aptitude the behavior is in
     * @param behaviorId  the specific behavior you are modifying
     * @param behaviorDto the behavior your are overwriting the old one with
     * @return ResponseEntity according
     */
    @RequestMapping(value = "/aptitude/{id}/behavior/{behaviorId}", method = RequestMethod.PUT)
    private ResponseEntity ModifyBehavior(@PathVariable("id") String id, @PathVariable("behaviorId") String behaviorId, @RequestBody BehaviorDto behaviorDto) {
        return null;//TODO HOW?
        //return aptitudeRepository.modifyBehaviour(id,behaviorId,behaviorDto)
    }

    @RequestMapping(value = "/aptitude/{id}/behavior/{behaviorId}", method = RequestMethod.DELETE)
    private ResponseEntity deleteBehavior(@PathVariable("id") String id, @PathVariable("behaviorId") String behaviorId) {
        return new ResponseEntity(aptitudeRepository.deleteBehavior(id, behaviorId), HttpStatus.OK);
    }

}


