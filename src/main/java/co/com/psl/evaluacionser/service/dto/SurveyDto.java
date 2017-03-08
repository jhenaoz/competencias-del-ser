package co.com.psl.evaluacionser.service.dto;

import java.util.List;

public class SurveyDto {

    private List<AptitudeSurveyDto> aptitudes;
    private String evaluator;
    private String evaluated;
    private String role;

    public SurveyDto() {
    }

    public SurveyDto(String evaluator, String evaluated, String role, List<AptitudeSurveyDto> aptitudes) {
        this.evaluator = evaluator;
        this.evaluated = evaluated;
        this.role = role;
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

    public List<AptitudeSurveyDto> getAptitudes() {
        return aptitudes;
    }

    public void setAptitudes(List<AptitudeSurveyDto> aptitudes) {
        this.aptitudes = aptitudes;
    }

}
