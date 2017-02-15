package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.domain.BehaviorDto;
import co.com.psl.evaluacionser.persistence.BehaviorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {
    @Autowired
    BehaviorRepository behaviorRepository;

    //get the list of all available aptitudes
    @RequestMapping(value = "/aptitude", method = RequestMethod.GET)
    private ResponseEntity getAptitudes() {
        return null;
        //return behaviorRepository.getAllAptitudes();
    }

    //get a specific aptitude using an ID
    @RequestMapping(value = "/aptitude/{id}", method = RequestMethod.GET)
    private ResponseEntity getAptitudeById(@PathVariable("id") String id) {
        return null;
        //return behaviorRepository.getAptitudeById(id);
    }

    //get all behavior from a specific aptitude
    @RequestMapping(value = "/aptitude/{id}/behavior", method = RequestMethod.GET)
    private ResponseEntity getBehaviors(@PathVariable("id") String id) {
        return null;
        //return new ResponseEntity(behaviorRepository.getBehaviors(id),HttpStatus.OK);
    }

    /**
     * Creates a new behavior for the specified aptitude, the behavior has to be sent as a JSON,and the JSON must have the fields specified below
     * the ID will be auto assigned by the DB
     * {
     * "sp": "spanish text",
     * "en": "english text"
     * }
     * @param id       the id from the aptitude the behavior is in
     * @param behaviorDto the new behavior your are creating
     * @return ResponseEntity according
     *
     */

    @RequestMapping(value = "/aptitude/{id}/behavior", headers = "Accept=application/json", method = RequestMethod.POST)
    private ResponseEntity addBehavior(@PathVariable("id") String id, @RequestBody BehaviorDto behaviorDto) {
        return null;
        //return new ResponseEntity(behaviorRepository.addBehavior(behaviorDto), HttpStatus.ACCEPTED);
    }

    //get one specific behavior using an ID
    @RequestMapping(value = "/aptitude/{id}/behavior/{behaviorId}", method = RequestMethod.GET)
    private ResponseEntity getBehaviorById(@PathVariable("id") String id, @PathVariable("behaviorId") String behaviorId) {
        return null;
        //return behaviorRepository.getBehaviorById(id,behaviorId);
    }

    /**
     * updates a behavior
     *
     * @param id         the id from the aptitude the behavior is in
     * @param behaviorId the specific behavior you are modifying
     * @param behaviorDto   the behavior your are overwriting the old one with
     * @return ResponseEntity according
     */
    @RequestMapping(value = "/aptitude/{id}/behavior/{behaviorId}", method = RequestMethod.PUT)
    private ResponseEntity ModifyBehavior(@PathVariable("id") String id, @PathVariable("behaviorId") String behaviorId, @RequestBody BehaviorDto behaviorDto) {
        return null;
        //return behaviorRepository.modifyBehaviour(id,behaviorId,behaviorDto)
    }
}
