package com.giveu.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by fox on 2018/11/5.
 */
@Document(collection = "GiveUMonitor")
public class ApiFullModel {

	@JSONField(ordinal = 0, name = "id")
	@Field("_id")
	String id;

	@JSONField(ordinal = 1, name = "消息类型")
	@Field("StatuCode")
	Integer statuCode;

	@JSONField(ordinal = 2, name = "当前用户")
	@Field("CurrentUser")
	String currentUser;

	@JSONField(ordinal = 3, name = "请求类型")
	@Field("Scheme")
	String scheme;

	@JSONField(ordinal = 4, name = "请求方式")
	@Field("HttpMethod")
	String httpMethod;

	@JSONField(ordinal = 5, name = "来源IP")
	@Field("RemoteIpAddress")
	String remoteIpAddress;

	@JSONField(ordinal = 6, name = "来源端口")
	@Field("RemotePort")
	String remotePort;

	@JSONField(ordinal = 7, name = "服务器IP")
	@Field("LocalIpAddress")
	String localIpAddress;

	@JSONField(ordinal = 8, name = "服务器端口")
	@Field("LocalPort")
	Integer localPort;

	@JSONField(ordinal = 9, name = "接口地址")
	@Field("Path")
	String path;

	@JSONField(ordinal = 10, name = "请求时间")
	@Field("RequestTime")
	String requestTime;

	@JSONField(ordinal = 11, name = "请求头")
	@Field("RequestHeader")
	String requestHeader;

	@JSONField(ordinal = 12, name = "URL参数")
	@Field("QueryString")
	String queryString;

	@JSONField(ordinal = 13, name = "form参数")
	@Field("RequestBody")
	String requestBody;

	@JSONField(ordinal = 14, name = "输出时间")
	@Field("ResponeTime")
	String responeTime;

	@JSONField(ordinal = 15, name = "输出头")
	@Field("ResponeHeader")
	String responeHeader;

	@JSONField(ordinal = 16, name = "输出内容")
	@Field("ResponseBody")
	String responseBody;

	@JSONField(ordinal = 17, name = "业务耗时(毫秒)")
	@Field("BusinessMilliseconds")
	Long businessMilliseconds;

	@JSONField(ordinal = 18, name = "总耗时(毫秒)")
	@Field("TotalMilliseconds")
	Long totalMilliseconds;

	@JSONField(ordinal = 19, name = "当前系统")
	@Field("LocalSystemName")
	String localSystemName;

	@JSONField(ordinal = 20, name = "来源系统")
	@Field("SourceSystemName")
	String sourceSystemName;

	@JSONField(ordinal = 21, name = "异常消息")
	@Field("ExceptionMessage")
	String exceptionMessage;

	@JSONField(ordinal = 22, name = "异常堆栈")
	@Field("ExceptionTraceStack")
	String exceptionTraceStack;

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

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getRemoteIpAddress() {
		return remoteIpAddress;
	}

	public void setRemoteIpAddress(String remoteIpAddress) {
		this.remoteIpAddress = remoteIpAddress;
	}

	public String getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(String remotePort) {
		this.remotePort = remotePort;
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

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(String requestHeader) {
		this.requestHeader = requestHeader;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getResponeTime() {
		return responeTime;
	}

	public void setResponeTime(String responeTime) {
		this.responeTime = responeTime;
	}

	public String getResponeHeader() {
		return responeHeader;
	}

	public void setResponeHeader(String responeHeader) {
		this.responeHeader = responeHeader;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public Long getBusinessMilliseconds() {
		return businessMilliseconds;
	}

	public void setBusinessMilliseconds(Long businessMilliseconds) {
		this.businessMilliseconds = businessMilliseconds;
	}

	public Long getTotalMilliseconds() {
		return totalMilliseconds;
	}

	public void setTotalMilliseconds(Long totalMilliseconds) {
		this.totalMilliseconds = totalMilliseconds;
	}

	public String getLocalSystemName() {
		return localSystemName;
	}

	public void setLocalSystemName(String localSystemName) {
		this.localSystemName = localSystemName;
	}

	public String getSourceSystemName() {
		return sourceSystemName;
	}

	public void setSourceSystemName(String sourceSystemName) {
		this.sourceSystemName = sourceSystemName;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionTraceStack() {
		return exceptionTraceStack;
	}

	public void setExceptionTraceStack(String exceptionTraceStack) {
		this.exceptionTraceStack = exceptionTraceStack;
	}
}
