package co.com.psl.evaluacionser.report;

public class BehaviorReport {

    private String behavior;
    private int score;

    public BehaviorReport() {
    }

    public BehaviorReport(String behavior, int score) {
        this.behavior = behavior;
        this.score = score;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
