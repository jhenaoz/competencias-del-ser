package co.com.psl.evaluacionser.domain;

public class Behavior {

	private Long id;

	private String sp;

	private String en;

	public Behavior() {
	}

	public Behavior(Long id, String sp, String en) {
		this.id = id;
		this.sp = sp;
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

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}
}
