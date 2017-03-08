package co.com.psl.evaluacionser.service.dto;

public class BehaviorSurveyDto {
    private long behaviorId;
    private int score;

    public BehaviorSurveyDto() {
    }

    public BehaviorSurveyDto(long behaviorId, int score) {
        this.behaviorId = behaviorId;
        this.score = score;
    }

    public long getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(long behaviorId) {
        this.behaviorId = behaviorId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
