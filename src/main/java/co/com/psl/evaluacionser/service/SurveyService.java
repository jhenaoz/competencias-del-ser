package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.persistence.SurveyRepository;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import co.com.psl.evaluacionser.service.transformer.SurveyTransformer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SurveyService {

    private static final Logger logger = Logger.getLogger(SurveyService.class);
    private SurveyRepository surveyRepository;
    private SurveyTransformer surveyTransformer;
    private EmailService emailService;

    @Autowired
    public SurveyService(final SurveyRepository surveyRepository, final SurveyTransformer surveyTransformer,
                         final EmailService emailService) {
        this.surveyRepository = surveyRepository;
        this.surveyTransformer = surveyTransformer;
        this.emailService = emailService;
    }

    /**
     * this method receives a SurveyDto, transforms it into a Survey and saves it to the database
     * @param surveyDto the SurveyDto containing the data to be saved
     * @return the Survey result from invoking the repository save method
     */
    public Survey saveSurvey(SurveyDto surveyDto) {
        Survey survey = surveyTransformer.transformer(surveyDto);
        try {
            emailService.sendSimpleMail(survey);
        } catch (AddressException ae) {
            logger.error("Can't construct the internet address with the given String " + ae);
        } catch (MessagingException me) {
            logger.error("Can't send the email " + me);
        }
        return surveyRepository.saveSurvey(survey);
    }

    /**
     * this method calls the repository method to search for surveys using the parameters received
     *
     * @param user      the person who was evaluated in the survey
     * @param startDate the start date of the survey date range
     * @param endDate   the end date of the survey date range
     * @return returns a List containing all the surveys found in the database
     */
    public List<Survey> findUserSurveys(String user, String startDate, String endDate) {

        if (!isDateRangeValid(startDate, endDate)) {
            return null;
        }
        return surveyRepository.findUserSurveys(user, startDate, endDate);
    }

    public boolean existsRecentSurvey(String evaluated, String evaluator, String aptitudeId) {
        return surveyRepository.existsRecentSurvey(evaluated, evaluator, aptitudeId);
    }

    /**
     * Checks if the dates provided form a valid range of dates.
     *
     * @param startDate starting date
     * @param endDate   ending date
     * @return whether the starting date is smaller than the ending date
     */
    private boolean isDateRangeValid(String startDate, String endDate) {
        if (startDate == null || endDate == null) {
            return true;
        }

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date start = dateFormatter.parse(startDate);
            Date end = dateFormatter.parse(endDate);

            return start.compareTo(end) <= 0;
        } catch (ParseException e) {
            logger.error("The Date couldn't be properly formatted " + e.getMessage());
            return false;
        }
    }

    /**
     * this method redirects the response containing the xlsx to download back to the controller
     *
     * @param evaluated the person who was evaluated in the survey
     * @param startDate the start date from the date range
     * @param endDate   the end date of the range
     * @return returns a response entity with BAD_REQUEST in case the survey search didnt get any surveys,
     * or returns the response entity with OK and the document to download
     */
    public ResponseEntity getSurveysFile(String evaluated, String startDate, String endDate) {

        FileService fileService = new FileService();
        return fileService.getDownloadResponse(evaluated, startDate, endDate);
    }

    public ResponseEntity getReportFile(String startDate, String endDate) {

        FileService fileService = new FileService();
        return fileService.getReportDownloadResponse(startDate, endDate);
    }

}
