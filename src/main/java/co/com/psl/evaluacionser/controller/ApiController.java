package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Behavior;
import co.com.psl.evaluacionser.persistence.BehaviorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {
    @Autowired
    BehaviorRepository behaviorRepository;

    //get the list of all aviable aptitudes
    @RequestMapping(value = "/aptitude", method = RequestMethod.GET)
    private ResponseEntity getAptitudes(){
        return null;
        //return behaviorRepository.getAllAptitudes();
    }
    //get a specific aptitude using an ID
    @RequestMapping(value = "/aptitude/{id}", method = RequestMethod.GET)
    private ResponseEntity getAptitudeById(@PathVariable("id") String id){
        return null;
        //return behaviorRepository.getAptitudeById(id);
    }

    //get all behavior from a specific aptitude
    @RequestMapping(value = "/aptitude/{id}/behavior", method = RequestMethod.GET)
    private ResponseEntity getBehaviors(@PathVariable("id") String id){
        return null;
        //return behaviorRepository.getBehaviors(id);
    }

    /**Creates a new behavior for the specified aptitude, the behavior has to be sent as a JSON,and the JSON must have the fields "EN":"behavior text in english" && "ES":"behavior text in spanish"
    */
    @RequestMapping(value = "/aptitude/{id}/behavior",headers="Accept=application/json", method = RequestMethod.POST)
    private ResponseEntity addBehavior(@PathVariable("id") String id, @RequestBody Behavior behavior){
        return null;
        //return behaviorRepository.addBehavior(behavior);
    }

    //get one specific behavior using an ID
    @RequestMapping(value = "/aptitude/{id}/behavior/{behaviorId}", method = RequestMethod.GET)
    private ResponseEntity getBehaviorById(@PathVariable("id") String id, @PathVariable ("behaviorId") String behaviorId){
        return null;
        //return behaviorRepository.getBehaviorById(id,behaviorId);
    }
}
