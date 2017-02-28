package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.service.SurveyService;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/survey")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    /**
     * This method saves a Survey to the DB, receives a JSON containing the
     * Survey basic data (DTO)
     *
     * @return Response entity with HttpStatus.ACCEPTED and the Survey saved
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Survey> saveSurvey(@RequestBody SurveyDto surveyDto) {
        return new ResponseEntity<Survey>(surveyService.saveSurvey(surveyDto), HttpStatus.ACCEPTED);
    }

    /**
     * Get all surveys made to a person within a time period.
     *
     * @param user
     *            the person to search
     * @param startDate
     *            starting date
     * @param endDate
     *            ending date
     * @return Response entity with HttpStatus.OK and the Surveys retrieved
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Survey>> getSurveys(@RequestParam(value = "user") String user,
                                                   @RequestParam(value = "startdate", required = false) String startDate,
                                                   @RequestParam(value = "enddate", required = false) String endDate) {
        List<Survey> userSurveys = surveyService.findUserSurveys(user, startDate, endDate);

        if (userSurveys == null)
            return new ResponseEntity<List<Survey>>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<List<Survey>>(userSurveys, HttpStatus.OK);
    }

}