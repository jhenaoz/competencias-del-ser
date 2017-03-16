package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.persistence.SurveyRepository;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import co.com.psl.evaluacionser.service.transformer.SurveyTransformer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * This is the class that will interact with both the SurveyController and the SurveyRepository, its used as a service
 * to avoid direct interaction between the controller and the repo, this class is also used to apply transformation
 * methods or to call other services which shouldn't be called on either the repo or the controller
 */
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
     * This method receives a SurveyDto, transforms it into a Survey and saves it to the database.
     *
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
     * This method calls the repository method to search for surveys using the parameters received.
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

    /**
     * This method calls the repository method to check the existence of a survey as of recently.
     *
     * @param evaluated Person who was evaluated, the one who will be checked to not have received a recent survey
     * @param evaluator Person who did the evaluation
     * @param aptitudeId the aptitude id for which we will check no recent surveys exists
     * @return true if a survey with this params has been done recently; false otherwise
     */
    public boolean existsRecentSurvey(String evaluated, String evaluator, String aptitudeId) {
        return surveyRepository.existsRecentSurvey(evaluated, evaluator, aptitudeId);
    }

    /**
     * Checks if the dates provided form a valid range of dates.
     *
     * @param startDate Starting date used to search for recent surveys
     * @param endDate   Ending date used to search for recent surveys
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

}
