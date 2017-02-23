package co.com.psl.evaluacionser.domain;

import java.util.ArrayList;
import java.util.List;

public class AptitudeSurvey {

    private String id;

    private String observation;

    List<BehaviorSurvey> behaviorSurvey;

    public AptitudeSurvey() {
    }

    public AptitudeSurvey(String id, String observation, List<BehaviorSurvey> behaviorSurvey) {
        this.id = id;
        this.observation = observation;
        this.setBehaviorSurvey(behaviorSurvey);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if (behaviorSurvey == null)
            this.behaviorSurvey = new ArrayList<BehaviorSurvey>();
        else
            this.behaviorSurvey = behaviorSurvey;
    }

}
