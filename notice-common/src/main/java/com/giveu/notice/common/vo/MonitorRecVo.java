package com.giveu.notice.common.vo;

import com.giveu.job.common.util.DateUtil;

import java.util.Date;

/**
 * Created by fox on 2018/10/24.
 */
public class MonitorRecVo {

	private Integer id;

	private String recNo;
	private String recName;
	private String recWxId;
	private String recWxNick;
	private String recPhone;
	private String recEmail;
	private String recDeptNo;
	private Integer recStatus;
	private String recStatusDesc;
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

	public String getRecNo() {
		return recNo;
	}

	public void setRecNo(String recNo) {
		this.recNo = recNo;
	}

	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public String getRecWxId() {
		return recWxId;
	}

	public void setRecWxId(String recWxId) {
		this.recWxId = recWxId;
	}

	public String getRecWxNick() {
		return recWxNick;
	}

	public void setRecWxNick(String recWxNick) {
		this.recWxNick = recWxNick;
	}

	public String getRecPhone() {
		return recPhone;
	}

	public void setRecPhone(String recPhone) {
		this.recPhone = recPhone;
	}

	public String getRecEmail() {
		return recEmail;
	}

	public void setRecEmail(String recEmail) {
		this.recEmail = recEmail;
	}

	public String getRecDeptNo() {
		return recDeptNo;
	}

	public void setRecDeptNo(String recDeptNo) {
		this.recDeptNo = recDeptNo;
	}

	public Integer getRecStatus() {
		return recStatus;
	}

	public void setRecStatus(Integer recStatus) {
		this.recStatus = recStatus;
	}

	public String getRecStatusDesc() {
		if (recStatus.equals(1)) {
			recStatusDesc = "启用";
		} else {
			recStatusDesc = "禁用";
		}
		return recStatusDesc;
	}

	public void setRecStatusDesc(String recStatusDesc) {
		this.recStatusDesc = recStatusDesc;
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
}
