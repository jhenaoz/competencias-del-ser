package co.com.psl.evaluacionser.domain;

import java.util.List;

public class Survey {
    
    private String id;
    
    private String evaluated; 
    
    private String evaluator;
    
    private String role;
    
    private String date;
    
    List<AptitudeSurvey> aptitudeSurvey;

    public Survey() {
        super();
    }
    
    public Survey(String id, String evaluated, String evaluator, String rol, String date,
            List<AptitudeSurvey> aptitudeSurvey) {
        super();
        this.id = id;
        this.evaluated = evaluated;
        this.evaluator = evaluator;
        this.rol = rol;
        this.date = date;
        this.aptitudeSurvey = aptitudeSurvey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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
        this.aptitudeSurvey = aptitudeSurvey;
    }
    
}
