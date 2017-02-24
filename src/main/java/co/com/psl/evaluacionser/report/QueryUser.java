package co.com.psl.evaluacionser.report;

public class QueryUser {

    private String name;
    private String lang;
    private String initDate;
    private String endDate;

    public QueryUser() {
    }

    public QueryUser(String name, String lang, String initDate, String endDate) {
        this.name = name;
        this.lang = lang;
        this.initDate = initDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
