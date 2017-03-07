package co.com.psl.evaluacionser.service.dto;

public class BehaviorSurveyDto {
    private String behaviorId;
    private int score;

    public BehaviorSurveyDto() {
    }

    public BehaviorSurveyDto(String behaviorId, int score) {
        this.behaviorId = behaviorId;
        this.score = score;
    }

    public String getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(String behaviorId) {
        this.behaviorId = behaviorId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
