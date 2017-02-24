package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.persistence.SurveyRepository;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import co.com.psl.evaluacionser.service.dto.report.SurveyReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/survey")
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

    /**
     * This method saves a Survey to the DB, receives a JSON containing the
     * Survey basic data (DTO)
     *
     * @return Response entity with HttpStatus.ACCEPTED and the Survey saved
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Survey> saveSurvey(@RequestBody SurveyDto surveyDto) {
        Survey survey = new Survey();
        // Todo transformar de Dto a Survey normal
        return new ResponseEntity(surveyRepository.saveSurvey(survey), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<SurveyReportDto>> getSurveys(@RequestParam(value = "user", required = false) String user,
                                                            @RequestParam(value = "startdate", required = false) String startDate,
                                                            @RequestParam(value = "enddate", required = false) String endDate,
                                                            @RequestParam(value = "lang", required = false) String lang) {

        return null;
    }
}