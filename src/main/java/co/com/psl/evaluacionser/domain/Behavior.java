package co.com.psl.evaluacionser.domain;

public class Behavior {

    private Long id;

    private String en;

    private String es;

    public Behavior(String en, String es) {
        this.en = en;
        this.es = es;
    }

    public Behavior(Long id, String en, String es) {
        this.id = id;
        this.en = en;
        this.es = es;
    }

    public Behavior() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
