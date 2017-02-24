package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;

public interface SurveyRepository {
    Survey saveSurvey(Survey survey);
}
