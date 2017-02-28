package co.com.psl.evaluacionser.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.persistence.SurveyRepository;
import co.com.psl.evaluacionser.service.dto.SurveyDto;
import co.com.psl.evaluacionser.service.transformer.SurveyTransformer;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyTransformer surveyTransformer;

    public Survey saveSurvey(SurveyDto surveyDto) {
        Survey survey = surveyTransformer.transformer(surveyDto);
        return surveyRepository.saveSurvey(survey);
    }

    public List<Survey> findUserSurveys(String user, String startDate, String endDate) {

        if (!isDateRangeValid(startDate, endDate))
            return null;

        return surveyRepository.findUserSurveys(user, startDate, endDate);
    }

    /**
     * Checks if the dates provided form a valid range of dates.
     *
     * @param startDate
     *            starting date
     * @param endDate
     *            ending date
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
            e.printStackTrace();
            return false;
        }
    }

}
