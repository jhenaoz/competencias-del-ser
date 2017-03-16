package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.service.ExcelReportGenerator;
import co.com.psl.evaluacionser.service.NameService;
import co.com.psl.evaluacionser.service.PdfService;
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

import javax.servlet.http.HttpServletResponse;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/survey")
public class SurveyController {

    private SurveyService surveyService;
    private ExcelReportGenerator excelReportGenerator;
    private PdfService pdfService = new PdfService();
    private NameService nameService = new NameService();

    @Autowired
    public SurveyController(final SurveyService surveyService, final ExcelReportGenerator excelReportGenerator) {
        this.excelReportGenerator = excelReportGenerator;
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
     * Get all surveys made to a person within a time period, the surveys are downloaded in a xlsx file.
     *
     * @param user      the person used to search for surveys
     * @param startDate starting date used to search for surveys
     * @param endDate   ending date used to search for surveys
     */
    @RequestMapping(value = "/report/user/xlsx", method = RequestMethod.GET)
    public void getUserReport(@RequestParam(value = "name", required = false) String user,
                              @RequestParam(value = "startdate", required = false) String startDate,
                              @RequestParam(value = "enddate", required = false) String endDate,
                              HttpServletResponse response) {

        List<Survey> userSurveys = surveyService.findUserSurveys(user, startDate, endDate);


        String userFileName = nameService.getUserFileName(user, startDate, endDate);
        excelReportGenerator.createUserExcelReport(userSurveys, response, userFileName);

    }

    /**
     * Get all surveys made to a person within a time period, the surveys are downloaded in a pdf file.
     *
     * @param user      the person used to search for surveys
     * @param startDate starting date used to search for surveys
     * @param endDate   ending date used to search for surveys
     */
    @RequestMapping(value = "/report/user/pdf", method = RequestMethod.GET)
    public void getUserReportPdf(@RequestParam(value = "user", required = false) String user,
                                 @RequestParam(value = "startdate", required = false) String startDate,
                                 @RequestParam(value = "enddate", required = false) String endDate,
                                 HttpServletResponse response) {

        List<Survey> userSurveys = surveyService.findUserSurveys(user, startDate, endDate);
        String fileName = nameService.getUserFileName(user, startDate, endDate);

        pdfService.getUserPdf(userSurveys, response, fileName);

    }

    /**
     * Get all relations from the surveys made in time period, the relations are downloaded in a xlsx file.
     *
     * @param startDate starting date
     * @param endDate   ending date
     */
    @RequestMapping(value = "/report/relation/xlsx", method = RequestMethod.GET)
    public void getRelationReport(
            @RequestParam(value = "startdate", required = false) String startDate,
            @RequestParam(value = "enddate", required = false) String endDate,
            HttpServletResponse response) {

        List<Survey> userSurveys = surveyService.findUserSurveys(null, startDate, endDate);

        String relationFileName = nameService.getRelationFileName(startDate, endDate);
        excelReportGenerator.createRelationExcelReport(userSurveys, response, relationFileName);

    }

    /**
     * Get all surveys made to a person within a time period, the relations are downloaded in a pdf file.
     *
     * @param startDate starting date used to search for surveys
     * @param endDate   ending date used to search for surveys
     */
    @RequestMapping(value = "/report/relation/pdf", method = RequestMethod.GET)
    public void getRelationReportPdf(@RequestParam(value = "startdate", required = false) String startDate,
                                     @RequestParam(value = "enddate", required = false) String endDate,
                                     HttpServletResponse response) {

        List<Survey> userSurveys = surveyService.findUserSurveys(null, startDate, endDate);
        String fileName = nameService.getRelationFileName(startDate, endDate);

        pdfService.getRelationPdf(userSurveys, response, fileName);

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
