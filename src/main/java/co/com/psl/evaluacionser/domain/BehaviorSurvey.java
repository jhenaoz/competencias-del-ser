package co.com.psl.evaluacionser.domain;

/**
 * This class is used to save the data in the required format
 *
 */
public class BehaviorSurvey {

    private Behavior behavior;

    private int score;

    public BehaviorSurvey() {
    }

    public BehaviorSurvey(Behavior behavior, int score) {
        this.behavior = behavior;
        this.score = score;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
