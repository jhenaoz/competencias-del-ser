package co.com.psl.evaluacionser.domain;

import java.util.List;

public class Aptitude {

    private String en;
    private String es;
    private List<Behavior> behaviors;

    public Aptitude() {}

    public Aptitude(String en, String es) {
        this.en = en;
        this.es = es;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getEs() {
        return es;
    }

    public void setEs(String es) {
        this.es = es;
    }

    public List<Behavior> getBehaviors() {
        return behaviors;
    }

    public void setBehaviors(List<Behavior> behaviors) {
        this.behaviors = behaviors;
    }
}
