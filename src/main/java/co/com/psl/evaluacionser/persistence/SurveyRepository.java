package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.SurveyDto;
import org.springframework.http.HttpStatus;

public interface SurveyRepository {
    HttpStatus saveSurvey(SurveyDto surveyDto);
}
