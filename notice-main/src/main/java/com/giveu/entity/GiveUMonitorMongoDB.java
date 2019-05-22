package com.giveu.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Created by fox on 2018/11/1.
 */
@Document(collection = "GiveUMonitor")
public class GiveUMonitorMongoDB implements Serializable {

	@Field("StatuCode")
	Integer statuCode;

	@Field("LocalIpAddress")
	String localIpAddress;

	@Field("LocalPort")
	Integer localPort;

	@Field("Path")
	String path;

	@Field("Count")
	Long count;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
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
}
