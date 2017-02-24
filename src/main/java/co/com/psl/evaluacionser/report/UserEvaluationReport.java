package co.com.psl.evaluacionser.report;

import java.util.List;

public class UserEvaluationReport {

    private String evaluated;
    private String aptitude;
    private List<BehaviorReport> behaviors;
    private String date;
    private String evaluator;
    private String role;
    private String observation;

    public UserEvaluationReport() {
    }

    public UserEvaluationReport(String evaluated, String aptitude, List<BehaviorReport> behaviors, String date,
            String evaluator, String role, String observation) {
        this.evaluated = evaluated;
        this.aptitude = aptitude;
        this.behaviors = behaviors;
        this.date = date;
        this.evaluator = evaluator;
        this.role = role;
        this.observation = observation;
    }

    public String getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(String evaluated) {
        this.evaluated = evaluated;
    }

    public String getAptitude() {
        return aptitude;
    }

    public void setAptitude(String aptitude) {
        this.aptitude = aptitude;
    }

    public List<BehaviorReport> getBehaviors() {
        return behaviors;
    }

    public void setBehaviors(List<BehaviorReport> behaviors) {
        this.behaviors = behaviors;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

}
