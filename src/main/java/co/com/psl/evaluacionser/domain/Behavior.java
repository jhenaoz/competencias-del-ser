package co.com.psl.evaluacionser.domain;

public class Behavior {

	private Long id;

	private String es;

	private String en;

	public Behavior() {
	}

	public Behavior(Long id, String es, String en) {
		this.id = id;
		this.es = es;
		this.en = en;
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
