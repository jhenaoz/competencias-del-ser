package co.com.psl.evaluacionser.domain;

import io.searchbox.annotations.JestId;

import java.util.ArrayList;
import java.util.List;

public class Aptitude {

    @JestId
    private Long id;
    private String es;
    private String en;
    private List<Behavior> behaviors = new ArrayList<>();

    public Aptitude() {
    }

    public Aptitude(Long id, String es, String en, List<Behavior> behaviors) {
        this.id = id;
        this.es = es;
        this.en = en;
        this.behaviors = behaviors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEs() {
        return es;
    }

    public void setEs(String es) {
        this.es = es;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public List<Behavior> getBehaviors() {
        return behaviors;
    }

    public void setBehaviors(List<Behavior> behaviors) {
        this.behaviors = behaviors;
    }

    public void addBehavior(Behavior behavior) {
        this.behaviors.add(behavior);

    }
}