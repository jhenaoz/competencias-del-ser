package co.com.psl.evaluacionser.service.dto.report;

import java.util.List;

public class SurveyReportDto {

    private String evaluated;
    private String evaluator;
    private String date;
    private String role;
    private List<AptitudeReportDto> aptitudes;

    public SurveyReportDto() {
    }

    public SurveyReportDto(String evaluated, String evaluator, String date, String role,
            List<AptitudeReportDto> aptitudes) {
        this.evaluated = evaluated;
        this.evaluator = evaluator;
        this.date = date;
        this.role = role;
        this.aptitudes = aptitudes;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<AptitudeReportDto> getAptitudes() {
        return aptitudes;
    }

    public void setAptitudes(List<AptitudeReportDto> aptitudes) {
        this.aptitudes = aptitudes;
    }

}
