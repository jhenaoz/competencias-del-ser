package co.com.psl.evaluacionser.service.dto;

public class BehaviorDto {
    private String es;
    private String en;

    public BehaviorDto() {
    }

    public BehaviorDto(String es, String en) {
        this.es = es;
        this.en = en;
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
}

