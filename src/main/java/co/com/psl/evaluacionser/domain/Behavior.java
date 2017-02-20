package co.com.psl.evaluacionser.domain;

public class Behavior {

    private String id;

    private String en;

    private String es;

    public Behavior() {
    }

    public Behavior(String id, String en, String es) {
        this.id = id;
        this.en = en;
        this.es = es;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


}
