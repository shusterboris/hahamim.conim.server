package application.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;

//@Entity
public class Member extends Person implements Serializable{
	private static final long serialVersionUID = 1320062617706369358L;
	
	private Integer level; //member level
	private List<String> regions;
	private Long partnerId;
	private String analytics;

	public String getAnalytics() {
		return analytics;
	}

	public void setAnalytics(String analytics) {
		this.analytics = analytics;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public List<String> getRegions() {
		return regions;
	}
	
	public void setRegions(List<String> regions) {
		this.regions = regions;
	}

}
