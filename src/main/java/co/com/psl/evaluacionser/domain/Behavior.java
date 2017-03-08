package co.com.psl.evaluacionser.domain;

public class Behavior {

    private long id;

    private String en;

    private String es;

    public Behavior() {
    }

    public Behavior(long id, String es, String en) {
        this.id = id;
        this.es = es;
        this.en = en;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
