package co.com.psl.evaluacionser.domain;

public class BehaviorSurvey {

    private String id;
    
    private String score;
    
    public BehaviorSurvey() {
        super();
    }

    public BehaviorSurvey(String id, String score) {
        super();
        this.id = id;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    
    
}
