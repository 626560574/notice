package com.giveu.model;

/**
 * Created by fox on 2018/9/4.
 */
public class TopObjFailModel {

	private Integer objCount;

	private Integer days;

	private Integer logCount;

	private Integer objFailCount;

	private Integer logMaxCount;

	private String logMaxName;

	private Float objFailPct;

	public Integer getObjCount() {
		return objCount;
	}

	public void setObjCount(Integer objCount) {
		this.objCount = objCount;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getLogCount() {
		return logCount;
	}

	public void setLogCount(Integer logCount) {
		this.logCount = logCount;
	}

	public Integer getObjFailCount() {
		return objFailCount;
	}

	public void setObjFailCount(Integer objFailCount) {
		this.objFailCount = objFailCount;
	}

	public Integer getLogMaxCount() {
		return logMaxCount;
	}

	public void setLogMaxCount(Integer logMaxCount) {
		this.logMaxCount = logMaxCount;
	}

	public String getLogMaxName() {
		return logMaxName;
	}

	public void setLogMaxName(String logMaxName) {
		this.logMaxName = logMaxName;
	}

	public Float getObjFailPct() {
		return ((Float.valueOf(objFailCount)) / objCount) * 100;
	}

	public void setObjFailPct(Float objFailPct) {
		this.objFailPct = objFailPct;
	}
}
