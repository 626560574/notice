package com.giveu.entity;

import java.util.Date;

/**
 * 监控人员配置表
 * Created by fox on 2018/10/23.
 */
public class MonitorRec {

	private Integer id;

	private String recNo;
	private String recName;
	private String recWxId;
	private String recWxNick;
	private String recPhone;
	private String recEmail;
	private String recDeptNo;
	private Integer recStatus;
	private Date createTime;
	private Date updateTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
