package co.com.psl.evaluacionser.domain;

import java.util.ArrayList;
import java.util.List;

public class Survey {

    private String evaluated;

    private String evaluator;

    private String role;

    private String date;

    List<AptitudeSurvey> aptitudeSurvey;

    public Survey() {
    }

    public Survey(String evaluated, String evaluator, String role, String date, List<AptitudeSurvey> aptitudeSurvey) {
        this.evaluated = evaluated;
        this.evaluator = evaluator;
        this.role = role;
        this.date = date;
        this.setAptitudeSurvey(aptitudeSurvey);
    }

    public String getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(String evaluated) {
        this.evaluated = evaluated;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<AptitudeSurvey> getAptitudeSurvey() {
        return aptitudeSurvey;
    }

    public void setAptitudeSurvey(List<AptitudeSurvey> aptitudeSurvey) {
        if (aptitudeSurvey == null)
            this.aptitudeSurvey = new ArrayList<AptitudeSurvey>();
        else
            this.aptitudeSurvey = aptitudeSurvey;
    }

}
