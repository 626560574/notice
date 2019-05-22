package com.giveu.notice.common.vo;

import java.io.Serializable;

/**
 * 数据响应模型
 * Created by fox on 2018/6/30.
 */
public class ResultModel implements Serializable {

	private Integer code;
	private String message;
	private Object data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResultModel() {
	}

	public ResultModel(Integer code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * 设置消息为成功状态
	 *
	 * @param message
	 * @param data
	 * @return
	 */
	public ResultModel SetSuccess(String message, Object data) {
		if (message == null)
			message = "";
		if (data == null)
			data = "";
		return new ResultModel(200, message, data);
	}

	/**
	 * 设置消息为失败状态
	 *
	 * @param message
	 * @param data
	 * @return
	 */
	public ResultModel SetFaild(String message, Object data) {
		if (message == null)
			message = "";
		if (data == null)
			data = "";
		return new ResultModel(500, message, data);
	}
}
