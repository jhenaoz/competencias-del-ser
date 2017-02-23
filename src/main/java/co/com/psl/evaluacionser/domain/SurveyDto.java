package co.com.psl.evaluacionser.domain;

import java.util.List;

public class SurveyDto {

    List<AptitudeSurveyDto> aptitudes;
    private String evaluator;
    private String evaluated;
    private String role;

    public SurveyDto() {
    }

    public SurveyDto(String evaluator, String evaluated, String role, List<AptitudeSurveyDto> aptitudes) {
        this.evaluator = evaluator;
        this.evaluated = evaluated;
        this.role = role;
        this.aptitudes = aptitudes;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public String getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(String evaluated) {
        this.evaluated = evaluated;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<AptitudeSurveyDto> getAptitudes() {
        return aptitudes;
    }

    public void setAptitudes(List<AptitudeSurveyDto> aptitudes) {
        this.aptitudes = aptitudes;
    }

    class BehaviorSurveyDto {
        private int behaviorId;
        private int score;

        public BehaviorSurveyDto() {
        }

        public BehaviorSurveyDto(int behaviorId, int score) {
            this.behaviorId = behaviorId;
            this.score = score;
        }

        public int getBehaviorId() {
            return behaviorId;
        }

        public void setBehaviorId(int behaviorId) {
            this.behaviorId = behaviorId;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

    class AptitudeSurveyDto {
        private int aptitudeId;
        private String observation;
        private List<BehaviorSurveyDto> behaviors;

        public AptitudeSurveyDto() {
        }

        public AptitudeSurveyDto(int aptitudeId, String observation, List<BehaviorSurveyDto> behaviors) {
            this.aptitudeId = aptitudeId;
            this.observation = observation;
            this.behaviors = behaviors;
        }

        public int getAptitudeId() {
            return aptitudeId;
        }

        public void setAptitudeId(int aptitudeId) {
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

}
