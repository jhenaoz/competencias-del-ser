package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.service.SurveyService;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/survey")
public class SurveyController {

    private SurveyService surveyService;

    @Autowired
    public SurveyController(final SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    /**
     * This method saves a Survey to the DB, receives a JSON containing the
     * Survey basic data (DTO)
     *
     * @return Response entity with HttpStatus.ACCEPTED and the Survey saved
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Survey> saveSurvey(@RequestBody SurveyDto surveyDto) {
        return new ResponseEntity<>(surveyService.saveSurvey(surveyDto), HttpStatus.ACCEPTED);
    }

    /**
     * Get all surveys made to a person within a time period.
     *
     * @param user      the person to search
     * @param startDate starting date
     * @param endDate   ending date
     * @return Response entity with HttpStatus.OK and the Surveys retrieved, the response entity will trigger a download
     */
    // TODO Enable this endpoint only with an authentication token
    // @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ResponseEntity getSurveys(@RequestParam(value = "user") String user,
                                                  @RequestParam(value = "startdate", required = false) String startDate,
                                                  @RequestParam(value = "enddate", required = false) String endDate) {
        return surveyService.getSurveysFile(user, startDate, endDate);
    }

    /**
     * Checks whether a survey was made in the last week.
     * @param evaluated the person who was evaluated in the survey
     * @param evaluator the person who made the survey
     * @return Response entity with HttpStatus.OK and if the survey exists
     */
    @RequestMapping(value = "/recentsurvey", method = RequestMethod.GET)
    public ResponseEntity<Boolean> existsRecentSurvey(@RequestParam(value = "evaluated") String evaluated,
                                             @RequestParam(value = "evaluator") String evaluator,
                                             @RequestParam(value = "aptitude", required = false) String aptitudeId) {
        boolean surveyExists = surveyService.existsRecentSurvey(evaluated, evaluator, aptitudeId);
        return new ResponseEntity<>(surveyExists, HttpStatus.OK);

    }

}
