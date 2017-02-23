package co.com.psl.evaluacionser.domain;

import java.util.List;

public class AptitudeSurvey {

    private AptitudeDto aptitude;

    private String observation;

    private List<BehaviorSurvey> behaviors;

    public AptitudeSurvey() {
    }

    public AptitudeSurvey(AptitudeDto aptitude, String observation, List<BehaviorSurvey> behaviors) {
        this.aptitude = aptitude;
        this.observation = observation;
        this.behaviors = behaviors;
    }

    public AptitudeDto getAptitude() {
        return aptitude;
    }

    public void setAptitude(AptitudeDto aptitude) {
        this.aptitude = aptitude;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public List<BehaviorSurvey> getBehaviors() {
        return behaviors;
    }

    public void setBehaviors(List<BehaviorSurvey> behaviors) {
        this.behaviors = behaviors;
    }

}
