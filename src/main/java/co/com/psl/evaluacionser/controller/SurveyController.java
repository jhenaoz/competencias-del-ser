package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.domain.SurveyDto;
import co.com.psl.evaluacionser.persistence.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     * This method saves a Survey to the DB, receives a JSON containing the Survey basic data (DTO)
     *
     * @return Response entity with HttpStatus.ACCEPTED and the Survey saved
     */
    @RequestMapping(value = "/survey", method = RequestMethod.POST)
    public ResponseEntity<Survey> saveSurvey(@RequestBody SurveyDto surveyDto) {
        Survey survey = new Survey();
        //Todo transformar de Dto a Survey normal
        return new ResponseEntity(surveyRepository.saveSurvey(survey), HttpStatus.ACCEPTED);

    }
}