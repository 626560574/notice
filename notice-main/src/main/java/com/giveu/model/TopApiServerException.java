package com.giveu.model;

/**
 * 主机异常
 * Created by fox on 2018/11/5.
 */
public class TopApiServerException {

	private Integer serCout;

	private Integer days;

	private Integer invCount;

	private Integer invExcCount;

	private String invExcMaxSerName;

	private Integer invExcMaxSerCount;

	private String invExcMinSerName;

	private Integer invExcMinSerCount;

	public Integer getSerCout() {
		return serCout;
	}

	public void setSerCout(Integer serCout) {
		this.serCout = serCout;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getInvCount() {
		return invCount;
	}

	public void setInvCount(Integer invCount) {
		this.invCount = invCount;
	}

	public Integer getInvExcCount() {
		return invExcCount;
	}

	public void setInvExcCount(Integer invExcCount) {
		this.invExcCount = invExcCount;
	}

	public String getInvExcMaxSerName() {
		return invExcMaxSerName;
	}

	public void setInvExcMaxSerName(String invExcMaxSerName) {
		this.invExcMaxSerName = invExcMaxSerName;
	}

	public Integer getInvExcMaxSerCount() {
		return invExcMaxSerCount;
	}

	public void setInvExcMaxSerCount(Integer invExcMaxSerCount) {
		this.invExcMaxSerCount = invExcMaxSerCount;
	}

	public String getInvExcMinSerName() {
		return invExcMinSerName;
	}

	public void setInvExcMinSerName(String invExcMinSerName) {
		this.invExcMinSerName = invExcMinSerName;
	}

	public Integer getInvExcMinSerCount() {
		return invExcMinSerCount;
	}

	public void setInvExcMinSerCount(Integer invExcMinSerCount) {
		this.invExcMinSerCount = invExcMinSerCount;
	}
}
