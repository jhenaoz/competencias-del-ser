package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import co.com.psl.evaluacionser.persistence.SurveyRepository;
import co.com.psl.evaluacionser.service.transformer.SurveyTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyTransformer surveyTransformer;

    /**
     * This method saves a Survey to the DB, receives a JSON containing the
     * Survey basic data (DTO)
     *
     * @return Response entity with HttpStatus.ACCEPTED and the Survey saved
     */
    @RequestMapping(value = "/survey", method = RequestMethod.POST)
    public ResponseEntity<Survey> saveSurvey(@RequestBody SurveyDto surveyDto) {
        Survey survey = surveyTransformer.transformer(surveyDto);
        return new ResponseEntity<>(surveyRepository.saveSurvey(survey), HttpStatus.ACCEPTED);
    }
}