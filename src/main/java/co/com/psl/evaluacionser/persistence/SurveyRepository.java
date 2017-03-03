package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.domain.Survey;

import java.util.List;

public interface SurveyRepository {

    Survey saveSurvey(Survey survey);

    List<Survey> findUserSurveys(String user, String startDate, String endDate);

    boolean existsRecentSurvey(String evaluated, String evaluator);
}
