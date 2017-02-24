package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.domain.SurveyDto;

public interface SurveyRepository {
    Survey saveSurvey(SurveyDto surveyDto);
}
