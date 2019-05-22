package com.giveu.model;

/**
 * Created by fox on 2018/11/1.
 */
public class TopModel {

	private TopFrequencyModel topFrequencyModel;
	private TopFailModel topFailModel;
	private TopLeadTimeModel topLeadTimeModel;
	private TopObjFailModel topObjFailModel;
	private TopApiProFlow topApiProFlow;
	private TopApiProException topApiProException;
	private TopApiServerFlow topApiServerFlow;
	private TopApiServerException topApiServerException;

	public TopApiProFlow getTopApiProFlow() {
		return topApiProFlow;
	}

	public void setTopApiProFlow(TopApiProFlow topApiProFlow) {
		this.topApiProFlow = topApiProFlow;
	}

	public TopApiProException getTopApiProException() {
		return topApiProException;
	}

	public void setTopApiProException(TopApiProException topApiProException) {
		this.topApiProException = topApiProException;
	}

	public TopApiServerFlow getTopApiServerFlow() {
		return topApiServerFlow;
	}

	public void setTopApiServerFlow(TopApiServerFlow topApiServerFlow) {
		this.topApiServerFlow = topApiServerFlow;
	}

	public TopApiServerException getTopApiServerException() {
		return topApiServerException;
	}

	public void setTopApiServerException(TopApiServerException topApiServerException) {
		this.topApiServerException = topApiServerException;
	}

	public TopObjFailModel getTopObjFailModel() {
		return topObjFailModel;
	}

	public void setTopObjFailModel(TopObjFailModel topObjFailModel) {
		this.topObjFailModel = topObjFailModel;
	}

	public TopFrequencyModel getTopFrequencyModel() {
		return topFrequencyModel;
	}

	public void setTopFrequencyModel(TopFrequencyModel topFrequencyModel) {
		this.topFrequencyModel = topFrequencyModel;
	}

	public TopFailModel getTopFailModel() {
		return topFailModel;
	}

	public void setTopFailModel(TopFailModel topFailModel) {
		this.topFailModel = topFailModel;
	}

	public TopLeadTimeModel getTopLeadTimeModel() {
		return topLeadTimeModel;
	}

	public void setTopLeadTimeModel(TopLeadTimeModel topLeadTimeModel) {
		this.topLeadTimeModel = topLeadTimeModel;
	}
}
