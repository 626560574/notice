package com.giveu.model;

/**
 * Created by fox on 2018/11/2.
 */

public class ApiPathStatModel {
	String path;
	Long exCount;
	Long todayExCount;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getExCount() {
		return exCount;
	}

	public void setExCount(Long exCount) {
		this.exCount = exCount;
	}

	public Long getTodayExCount() {
		return todayExCount;
	}

	public void setTodayExCount(Long todayExCount) {
		this.todayExCount = todayExCount;
	}
}
