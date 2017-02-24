package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.domain.SurveyDto;
import co.com.psl.evaluacionser.persistence.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

    /**
     * This method returns the list of all employees in the elasticsearch
     *
     * @return Response entity with the status of the request, ok if it exists or not_found if it is null, and if it exists returns the list
     */
    @RequestMapping(value = "/survey", method = RequestMethod.POST)
    public ResponseEntity<List<Survey>> saveSurvey(@RequestBody SurveyDto surveyDto) {
        return new ResponseEntity<List<Survey>>(surveyRepository.saveSurvey(surveyDto));

    }
}