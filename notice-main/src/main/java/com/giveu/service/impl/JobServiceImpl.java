package com.giveu.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.giveu.dao.MonitorJobDAO;
import com.giveu.entity.MonitorJob;
import com.giveu.job.common.dto.QrtzTriggerLogDTO;
import com.giveu.job.common.info.CommonMessage;
import com.giveu.job.common.vo.JobTriggerAppVo;
import com.giveu.notice.common.dto.JobMonitorDTO;
import com.giveu.notice.common.vo.ResultModel;
import com.giveu.service.JobService;
import jodd.http.HttpMultiMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fox on 2018/10/23.
 */
@Service
public class JobServiceImpl implements JobService {

	@Autowired
	MonitorJobDAO monitorJobDAO;

//	private static final String JOB_LIST_URL = "http://10.10.11.52:9060/job/list";
	private static final String JOB_LIST_URL = "http://10.11.13.30:9060/job/list";
//	private static final String TRIGGER_JOB_URL = "http://10.14.21.93:9050/we/cat/push/trigger/log/id";
//	private static final String TRIGGER_JOB_URL = "http://10.10.11.52:9050/we/cat/push/trigger/log/id";
	private static final String TRIGGER_JOB_URL = "http://10.11.13.30:9050/we/cat/push/trigger/log/id";
//	private static final String LOG_LIST_URL = "http://10.14.21.93:9050/we/cat/push/error/log/job";
//	private static final String LOG_LIST_URL = "http://10.10.11.52:9050/we/cat/push/error/log/job";
	private static final String LOG_LIST_URL = "http://10.11.13.30:9050/we/cat/push/error/log/job";


	@Override
	public void list(HttpServletRequest request, ResultModel resultModel) {

		String jobName = request.getParameter("jobName");
		String jobCode = request.getParameter("jobCode");

		MonitorJob monitorJob = new MonitorJob();

		List<MonitorJob> monitorJobList = monitorJobDAO.getList(monitorJob);


		HttpRequest httpRequest = HttpRequest.post(JOB_LIST_URL);
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("jobName", jobName);
		multiMap.add("jobCode", jobCode);
		multiMap.add("userAccount", "admin");
		HttpResponse response = httpRequest.send();
		String result = response.bodyText();
		ResultModel model = JSONObject.parseObject(result, ResultModel.class);
		List<JobTriggerAppVo> jobTriggerAppVoList = JSONObject.parseArray(String.valueOf(model.getData()), JobTriggerAppVo.class);

		List<JobMonitorDTO> jobMonitorDTOList = new ArrayList<>();

		for (JobTriggerAppVo j : jobTriggerAppVoList) {
			JobMonitorDTO jobMonitorDTO = new JobMonitorDTO();
			jobMonitorDTO.setJobId(j.getId());
			jobMonitorDTO.setJobCode(j.getJobCode());
			jobMonitorDTO.setJobName(j.getJobName());
			for (MonitorJob m : monitorJobList) {
				if (m.getJobId().equals(j.getId())) {
					jobMonitorDTO.setId(m.getId());
					jobMonitorDTO.setRecObj(m.getRecObj());
					jobMonitorDTO.setMonitorStatus(m.getMonitorStatus());
					jobMonitorDTO.setCreateTime(m.getCreateTime());
					jobMonitorDTO.setUpdateTime(m.getUpdateTime());
				}
			}
			jobMonitorDTOList.add(jobMonitorDTO);
		}

		resultModel.setCode(CommonMessage.OK_CODE);
		resultModel.setMessage(CommonMessage.OK_DESC);
		resultModel.setData(jobMonitorDTOList);
	}

	@Override
	public Integer add(HttpServletRequest request, ResultModel resultModel) {

		String id = request.getParameter("id");
		String jobId = request.getParameter("jobId");
		String jobCode = request.getParameter("jobCode");
		String recObj = request.getParameter("recObj");
		Integer monitorStatus = NumberUtils.toInt(request.getParameter("monitorStatus"), 1);

		MonitorJob monitorJob = new MonitorJob();

		monitorJob.setId(NumberUtils.toInt(id, 0));
		monitorJob.setJobId(jobId);
		monitorJob.setJobCode(jobCode);
		monitorJob.setRecObj(recObj);
		monitorJob.setMonitorStatus(monitorStatus);

		if (!monitorJob.getId().equals(0)) {
			return monitorJobDAO.upd(monitorJob);
		}
		return monitorJobDAO.add(monitorJob);
	}


	@Override
	public Integer update(HttpServletRequest request, ResultModel resultModel) {
		return null;
	}

	@Override
	public Integer delete(Integer id) {
		return null;
	}

	@Override
	public Integer enableById(Integer id) {
		return null;
	}

	@Override
	public Integer disableById(Integer id) {
		return null;
	}

	@Override
	public List<QrtzTriggerLogDTO> getLogListByJobId(String jobId) {
		HttpRequest httpRequest = HttpRequest.post(LOG_LIST_URL);

		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("jobId", jobId);

		HttpResponse response = httpRequest.send();

		String result = response.bodyText();
		ResultModel resultModel = JSONObject.parseObject(result, ResultModel.class);
		List<QrtzTriggerLogDTO> qrtzTriggerLogDTOList = JSONObject.parseArray(String.valueOf(resultModel.getData()), QrtzTriggerLogDTO.class);
		return qrtzTriggerLogDTOList;
	}

	@Override
	public ResultModel triggerJobByLogId(String logId) {
		HttpRequest httpRequest = HttpRequest.post(TRIGGER_JOB_URL);
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("id", logId);
		HttpResponse response = httpRequest.send();
		String result = response.bodyText();
		ResultModel resultModel = JSONObject.parseObject(result, ResultModel.class);
		return resultModel;
	}


}
