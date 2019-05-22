package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

/**
 * Created by fox on 2018/10/24.
 */
public class ReceiverEntity implements Serializable {
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 7014604039131889174L;

	String id;
	String recNo;
	String recName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
}