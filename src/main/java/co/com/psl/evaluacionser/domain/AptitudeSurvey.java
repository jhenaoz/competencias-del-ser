package co.com.psl.evaluacionser.domain;

import java.util.ArrayList;
import java.util.List;

public class AptitudeSurvey {
    
    private AptitudeDto aptitude;

    private String observation;
    
    private List<BehaviorSurvey> behaviors;
    
    public AptitudeSurvey() {
    }

    public AptitudeSurvey(AptitudeDto aptitudeDto, String observation, List<BehaviorSurvey> behaviorSurvey) {
        this.aptitude = aptitudeDto;
        this.observation = observation;
        this.behaviors = behaviorSurvey;
    }

    public AptitudeDto getAptitudeDto() {
        return aptitude;
    }

    public void setAptitudeDto(AptitudeDto aptitudeDto) {
        this.aptitude = aptitudeDto;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public List<BehaviorSurvey> getBehaviorSurvey() {
        return behaviors;
    }

    public void setBehaviorSurvey(List<BehaviorSurvey> behaviorSurvey) {
        this.behaviors = behaviorSurvey;
    }

}
