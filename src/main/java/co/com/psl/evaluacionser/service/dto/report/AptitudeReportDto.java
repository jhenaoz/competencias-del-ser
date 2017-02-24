package co.com.psl.evaluacionser.service.dto.report;

import java.util.List;

public class AptitudeReportDto {

    private String name;
    private List<BehaviorReportDto> behaviors;
    private String observation;

    public AptitudeReportDto() {
    }

    public AptitudeReportDto(String name, List<BehaviorReportDto> behaviors, String observation) {
        this.name = name;
        this.behaviors = behaviors;
        this.observation = observation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BehaviorReportDto> getBehaviors() {
        return behaviors;
    }

    public void setBehaviors(List<BehaviorReportDto> behaviors) {
        this.behaviors = behaviors;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

}
