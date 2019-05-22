package com.giveu.entity;

import com.giveu.job.common.util.DateUtil;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fox on 2018/11/5.
 */
@Document(collection = "GiveUMonitor")
public class ApiSimpleModel {

	@Field("_id")
	String id;
	@Field("StatuCode")
	Integer statuCode;
	@Field("LocalIpAddress")
	private String localIpAddress;
	@Field("LocalPort")
	private Integer localPort;
	@Field("Path")
	private String path;
	@Field("RequestTime")
	private Date requestTime;
	private String requestTimeDesc;
	@Field("ExceptionMessage")
	private String exceptionMessage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatuCode() {
		return statuCode;
	}

	public void setStatuCode(Integer statuCode) {
		this.statuCode = statuCode;
	}

	public String getLocalIpAddress() {
		return localIpAddress;
	}

	public void setLocalIpAddress(String localIpAddress) {
		this.localIpAddress = localIpAddress;
	}

	public Integer getLocalPort() {
		return localPort;
	}

	public void setLocalPort(Integer localPort) {
		this.localPort = localPort;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getRequestTimeDesc() throws ParseException {
		return DateUtil.dateToStrLong(requestTime);
	}

	public void setRequestTimeDesc(String requestTimeDesc) {
		this.requestTimeDesc = requestTimeDesc;
	}
}
