package co.com.psl.evaluacionser.domain;

public class BehaviorSurvey {

    private Behavior behavior;
    
    private String score;

    public BehaviorSurvey() {
    }

    public BehaviorSurvey(Behavior behavior, String score) {
        this.behavior = behavior;
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
