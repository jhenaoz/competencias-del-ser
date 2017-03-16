package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.service.ReportGenerator;
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

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/survey")
public class SurveyController {

    private SurveyService surveyService;
    private ReportGenerator reportGenerator;

    @Autowired
    public SurveyController(final SurveyService surveyService, final ReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
        this.surveyService = surveyService;
    }

    /**
     * This method saves a Survey to the DB, receives a JSON containing the
     * Survey basic data (DTO).
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
     * @param user      the person used to search for surveys
     * @param startDate starting date used to search for surveys
     * @param endDate   ending date used to search for surveys
     * @return Response entity with HttpStatus.OK and the report, triggers a download
     */
    @RequestMapping(value = "/report/user", method = RequestMethod.GET)
    public ResponseEntity<HttpStatus> getUserReport(@RequestParam(value = "name", required = false) String user,
                                                @RequestParam(value = "startdate", required = false) String startDate,
                                                @RequestParam(value = "enddate", required = false) String endDate) {

        List<Survey> userSurveys = surveyService.findUserSurveys(user, startDate, endDate);

        if ((user != null && user.isEmpty()) || userSurveys == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reportGenerator.createUserExcelReport(userSurveys);

        return surveyService.getSurveysFile(user, startDate, endDate);
    }

    /**
     * Get all relations from the surveys made to a person within a time period.
     *
     * @param startDate starting date
     * @param endDate   ending date
     * @return Response entity with HttpStatus.OK and the downloaded report, triggers a download
     */
    @RequestMapping(value = "/report/relation", method = RequestMethod.GET)
    public ResponseEntity<HttpStatus> getRelationReport(
                                                @RequestParam(value = "startdate", required = false) String startDate,
                                                @RequestParam(value = "enddate", required = false) String endDate) {

        List<Survey> userSurveys = surveyService.findUserSurveys(null, startDate, endDate);

        if (userSurveys == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reportGenerator.createRelationExcelReport(userSurveys);

        return surveyService.getReportFile(startDate, endDate);
    }

    /**
     * Checks whether a survey was made in the last week.
     *
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
