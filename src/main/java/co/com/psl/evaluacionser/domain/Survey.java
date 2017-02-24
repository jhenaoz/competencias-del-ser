package co.com.psl.evaluacionser.domain;

import java.util.List;

/**
 * This class is used to save the data in the required format implementing the classes AptitudeSurvey and Behavior Survey
 *
 */
public class Survey {

    private String evaluator;

    private String evaluated;

    private String role;

    /**
     * This date is formated in elasticsearch 
     */
    private String timestamp;

    List<AptitudeSurvey> aptitudes;

    public Survey() {
    }

    public Survey(String evaluator, String evaluated, String role, String timestamp, List<AptitudeSurvey> aptitudes) {
        this.evaluator = evaluator;
        this.evaluated = evaluated;
        this.role = role;
        this.timestamp = timestamp;
        this.aptitudes = aptitudes;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public String getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(String evaluated) {
        this.evaluated = evaluated;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<AptitudeSurvey> getAptitudes() {
        return aptitudes;
    }

    public void setAptitudes(List<AptitudeSurvey> aptitudes) {
        this.aptitudes = aptitudes;
    }

}
