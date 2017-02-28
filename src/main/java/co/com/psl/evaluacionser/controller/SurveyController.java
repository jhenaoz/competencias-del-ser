package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.persistence.SurveyRepository;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import co.com.psl.evaluacionser.service.transformer.SurveyTransformer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/survey")
public class SurveyController {

    private static Logger logger = Logger.getLogger(SurveyController.class);
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
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Survey> saveSurvey(@RequestBody SurveyDto surveyDto) {
        Survey survey = surveyTransformer.transformer(surveyDto);
        return new ResponseEntity<>(surveyRepository.saveSurvey(survey), HttpStatus.ACCEPTED);
    }

    /**
     * Get all surveys made to a person within a time period.
     *
     * @param user      the person to search
     * @param startDate starting date
     * @param endDate   ending date
     * @return Response entity with HttpStatus.OK and the Surveys retrieved
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Survey>> getSurveys(@RequestParam(value = "user") String user,
                                                   @RequestParam(value = "startdate", required = false) String startDate,
                                                   @RequestParam(value = "enddate", required = false) String endDate) {

        if (!isDateRangeValid(startDate, endDate))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Survey> userSurveys = surveyRepository.findUserSurveys(user, startDate, endDate);
        return new ResponseEntity<>(userSurveys, HttpStatus.OK);
    }

    /**
     * Checks if the dates provided form a valid range of dates.
     *
     * @param startDate starting date
     * @param endDate   ending date
     * @return whether the starting date is smaller than the ending date
     */
    private boolean isDateRangeValid(String startDate, String endDate) {
        if (startDate == null || endDate == null)
            return true;

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date start = dateFormatter.parse(startDate);
            Date end = dateFormatter.parse(endDate);

            return start.compareTo(end) <= 0;
        } catch (ParseException e) {
            logger.error("Date range is Invalid ");
            return false;
        }
    }
}