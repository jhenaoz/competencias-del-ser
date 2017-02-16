package co.com.psl.evaluacionser.domain;

public class Behavior {

	private String id;

	private String es;

	private String en;

	public Behavior() {
	}

	public Behavior(String id, String es, String en) {
		this.id = id;
		this.es = es;
		this.en = en;
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
