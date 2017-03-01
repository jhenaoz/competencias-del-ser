package co.com.psl.evaluacionser.service.dto;

import java.util.List;

public class AptitudeSurveyDto {

    private String aptitudeId;
    private String observation;
    private List<BehaviorSurveyDto> behaviors;

    public AptitudeSurveyDto() {
    }

    public AptitudeSurveyDto(String aptitudeId, String observation, List<BehaviorSurveyDto> behaviors) {
        this.aptitudeId = aptitudeId;
        this.observation = observation;
        this.behaviors = behaviors;
    }

    public String getAptitudeId() {
        return aptitudeId;
    }

    public void setAptitudeId(String aptitudeId) {
        this.aptitudeId = aptitudeId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public List<BehaviorSurveyDto> getBehaviors() {
        return behaviors;
    }

    public void setBehaviors(List<BehaviorSurveyDto> behaviors) {
        this.behaviors = behaviors;
    }
}