package com.giveu.model;

import jodd.util.collection.SortedArrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fox on 2018/11/1.
 */
public class ApiStatModel {
	String address;
	Integer port;
	Long exCount;
	Long todayExCount;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
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

