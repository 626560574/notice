package com.giveu.model;

import jodd.util.collection.SortedArrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fox on 2018/11/2.
 */
public class ApiProStatModel {
	List<ApiStatModel> apiStats;
	List<ApiPathStatModel> apiPathStats;

	public List<ApiStatModel> getApiStats() {
		if (apiStats == null)
			apiStats = new ArrayList<>();
		return apiStats;
	}

	public void setApiStats(List<ApiStatModel> apiStats) {
		this.apiStats = apiStats;
	}

	public List<ApiPathStatModel> getApiPathStats() {
		if (apiPathStats == null)
			apiPathStats = new ArrayList<>();
		return apiPathStats;
	}

	public void setApiPathStats(List<ApiPathStatModel> apiPathStats) {
		this.apiPathStats = apiPathStats;
	}
}
