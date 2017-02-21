package co.com.psl.evaluacionser.domain;

import java.util.List;

public class AptitudeSurvey {

    private Long id;

    private String observation;
    
    List<BehaviorSurvey> behaviorSurvey;
    
    public AptitudeSurvey() {
        super();
    }

    public AptitudeSurvey(Long id, String observation, List<BehaviorSurvey> behaviorSurvey) {
        super();
        this.id = id;
        this.observation = observation;
        this.behaviorSurvey = behaviorSurvey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public List<BehaviorSurvey> getBehaviorSurvey() {
        return behaviorSurvey;
    }

    public void setBehaviorSurvey(List<BehaviorSurvey> behaviorSurvey) {
        this.behaviorSurvey = behaviorSurvey;
    }
    
    
    
}
