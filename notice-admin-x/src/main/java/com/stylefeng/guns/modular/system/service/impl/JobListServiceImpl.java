package com.stylefeng.guns.modular.system.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giveu.notice.common.util.MD5Util;
import com.giveu.notice.common.var.Secret;
import com.giveu.notice.common.vo.JobTriggerAppVo;
import com.giveu.notice.common.vo.ResultModel;
import com.stylefeng.guns.modular.system.service.IJobListService;
import jodd.http.HttpMultiMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 任务列表Dao
 *
 * @author fengshuonan
 * @Date 2018-07-03 17:03:27
 */
@Service
public class JobListServiceImpl implements IJobListService {

	private static final int OK = 200;

	private static final String dataUrl = "http://localhost:8765/";

	private static final String ADMIN_KEY = "admin";

	@Override
	public void list(HttpServletRequest request, List<Map<String, Object>> list) throws IOException {
		String id = request.getParameter("id");
		String jobCode = request.getParameter("jobCode");
		String appKey = request.getParameter("appKey");

		HttpRequest httpRequest = HttpRequest.post(dataUrl+"job/list");


		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("id", id);
		multiMap.add("jobCode", jobCode);
		multiMap.add("appKey", appKey);

		HttpResponse response = httpRequest.send();

		ObjectMapper mapper = new ObjectMapper();
		String result = response.bodyText();
		ResultModel resultModel = mapper.readValue(result, ResultModel.class);
		List<JobTriggerAppVo> jobTriggerAppVoList = null;
		if (resultModel.getCode() == OK) {
			jobTriggerAppVoList = (ArrayList<JobTriggerAppVo>)resultModel.getData();

		}

		for (int i = 0; i < jobTriggerAppVoList.size(); i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", ((Map)jobTriggerAppVoList.get(i)).get("id"));
			map.put("jobName", ((Map)jobTriggerAppVoList.get(i)).get("jobName"));
			map.put("jobDesc", ((Map)jobTriggerAppVoList.get(i)).get("jobDesc"));
			map.put("jobCode", ((Map)jobTriggerAppVoList.get(i)).get("jobCode"));
			map.put("callbackUrl", ((Map)jobTriggerAppVoList.get(i)).get("callbackUrl"));
			map.put("appKey", ((Map)jobTriggerAppVoList.get(i)).get("appKey"));
			map.put("cronExpression", ((Map)jobTriggerAppVoList.get(i)).get("cronExpression"));
			map.put("jobCreateTime", ((Map)jobTriggerAppVoList.get(i)).get("jobCreateTime"));
			list.add(map);
		}

	}

	@Override
	public Integer addJob(JobTriggerAppVo jobTriggerAppVo) throws IOException {
		HttpRequest httpRequest = HttpRequest.post(dataUrl+"job/add");
		String xGiveuAppKey = jobTriggerAppVo.getAppKey();
		String xGiveuTimestamp = String.valueOf(new Date().getTime());
		String jobName = jobTriggerAppVo.getJobName();
		String jobDesc = jobTriggerAppVo.getJobDesc();
		String callbackUrl = jobTriggerAppVo.getCallbackUrl();
		String cronExpression = jobTriggerAppVo.getCronExpression();
		String jobCode = jobTriggerAppVo.getJobCode();

		httpRequest.header("xGiveuAppKey", xGiveuAppKey);
		httpRequest.header("xGiveuTimestamp", xGiveuTimestamp);

		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("jobName", jobName);
		multiMap.add("jobDesc", jobDesc);
		multiMap.add("callbackUrl", callbackUrl);
		multiMap.add("cronExpression", cronExpression);
		multiMap.add("jobCode", jobCode);

		StringBuilder sb = new StringBuilder();
		sb.append("xGiveuAppKey").append(xGiveuAppKey);
		sb.append("xGiveuTimestamp").append(xGiveuTimestamp);
		sb.append("jobName").append(jobName);
		sb.append("jobDesc").append(jobDesc);
		sb.append("callbackUrl").append(callbackUrl);
		sb.append("cronExpression").append(cronExpression);
		sb.append("jobCode").append(jobCode);
		sb.append(Secret.APP_SECRET);

		String original = sb.toString();
		String encrypted = MD5Util.sign(original);

		httpRequest.header("xGiveuSign", encrypted);

		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response = httpRequest.send();
		String result = response.bodyText();
		ResultModel resultModel = mapper.readValue(result, ResultModel.class);

		return resultModel.getCode();
	}

	@Override
	public Integer delJobByCode(String jobCode, String appKey) throws IOException {
		HttpRequest httpRequest = HttpRequest.post(dataUrl+"job/del/code");
		String xGiveuAppKey = "admin";
		String xGiveuTimestamp = String.valueOf(new Date().getTime());

		httpRequest.header("xGiveuAppKey", xGiveuAppKey);
		httpRequest.header("xGiveuTimestamp", xGiveuTimestamp);

		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("jobCode", jobCode);

		StringBuilder sb = new StringBuilder();
		sb.append("xGiveuAppKey").append(xGiveuAppKey);
		sb.append("xGiveuTimestamp").append(xGiveuTimestamp);
		sb.append("jobCode").append(jobCode);
		sb.append(Secret.APP_SECRET);

		String original = sb.toString();
		String encrypted = MD5Util.sign(original);

		httpRequest.header("xGiveuSign", encrypted);
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response = httpRequest.send();
		String result = response.bodyText();
		ResultModel resultModel = mapper.readValue(result, ResultModel.class);
		return resultModel.getCode();
	}

	@Override
	public Integer triggerByLogId(String jobLogListId) throws IOException {
		HttpRequest httpRequest = HttpRequest.post(dataUrl+"job/trigger/log/id");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("jobLogListId", jobLogListId);
		HttpResponse response = httpRequest.send();
		String result = response.bodyText();
		ObjectMapper mapper = new ObjectMapper();
		ResultModel resultModel = mapper.readValue(result, ResultModel.class);
		return resultModel.getCode();
	}
}
