package co.com.psl.evaluacionser.domain;

import java.util.List;

import io.searchbox.annotations.JestId;

public class Aptitude {

	@JestId
	private Long id;

	private String sp;
	private String en;
	private List<Behavior> behaviors;

	public Aptitude() {
	}

	public Aptitude(Long id, String sp, String en, List<Behavior> behaviors) {
		this.id = id;
		this.sp = sp;
		this.en = en;
		this.behaviors = behaviors;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
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
}
