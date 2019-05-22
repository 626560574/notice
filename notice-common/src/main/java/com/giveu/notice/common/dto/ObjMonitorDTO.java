package com.giveu.notice.common.dto;


import com.giveu.job.common.util.DateUtil;

import java.util.Date;

/**
 * Created by fox on 2018/10/24.
 */
public class ObjMonitorDTO {

	private Integer id;

	private String objId;

	private String objName;

	private String objCode;

	private Integer monitorStatus;

	private String monitorStatusDesc;

	private String recObj;

	private Date createTime;

	private String createTimeDesc;

	private Date updateTime;

	private String updateTimeDesc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getObjCode() {
		return objCode;
	}

	public void setObjCode(String objCode) {
		this.objCode = objCode;
	}

	public Integer getMonitorStatus() {
		return monitorStatus;
	}

	public void setMonitorStatus(Integer monitorStatus) {
		this.monitorStatus = monitorStatus;
	}

	public String getRecObj() {
		return recObj;
	}

	public void setRecObj(String recObj) {
		this.recObj = recObj;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeDesc() {
		return DateUtil.dateToStrLong(createTime);
	}

	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTimeDesc() {
		return DateUtil.dateToStrLong(updateTime);
	}

	public void setUpdateTimeDesc(String updateTimeDesc) {
		this.updateTimeDesc = updateTimeDesc;
	}


	public void setMonitorStatusDesc(String monitorStatusDesc) {
		this.monitorStatusDesc = monitorStatusDesc;
	}

	public String getMonitorStatusDesc() {
		if (monitorStatus == null) {
			return null;
		}
		if (monitorStatus.equals(1)) {
			monitorStatusDesc = "启用";
		} else {
			monitorStatusDesc = "禁用";
		}
		return monitorStatusDesc;
	}
}
