package com.giveu.controller;

import com.alibaba.fastjson.JSONObject;
import com.giveu.component.Token;
import com.giveu.entity.ApiFullModel;
import com.giveu.entity.MonitorApi;
import com.giveu.job.common.dto.QrtzTriggerLogDTO;
import com.giveu.model.ApiProStatModel;
import com.giveu.entity.ApiSimpleModel;
import com.giveu.notice.common.vo.ResultModel;
import com.giveu.service.ApiService;
import com.giveu.service.JobService;
import com.giveu.service.MobileService;
import jodd.http.HttpMultiMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * Created by fox on 2018/10/29.
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/mobile")
public class MobileController {

	@Autowired
	JobService jobService;


	@Autowired
	Token token;

	@Autowired
	MobileService mobileService;

	@Autowired
	ApiService apiService;


	@RequestMapping(value = "/log/list")
	public String getLogListByJobId(@RequestParam("jobId") String jobId, Model model) {

		List<QrtzTriggerLogDTO> triggerLogList = jobService.getLogListByJobId(jobId);

		String jobName = "";

		if (triggerLogList != null && triggerLogList.size() > 0) {
			jobName = triggerLogList.get(0).getJobName();
		}

		model.addAttribute("jobName", jobName);
		model.addAttribute("triggerLogList", triggerLogList);

		return "trigger_log_error_list";
	}

	@RequestMapping(value = "/job/trigger")
	@ResponseBody
	public ResultModel triggerJob(@RequestParam("logId") String logId) {
		return jobService.triggerJobByLogId(logId);
	}

	@RequestMapping(value = "/oauth/test")
	public String oauthTest(HttpServletRequest request) {
		String code = request.getParameter("code");
		HttpRequest httpRequest = HttpRequest.get("https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo");
		HttpMultiMap multiMap = httpRequest.query();
		System.out.println(code);
		System.out.println(token.getAccess_token());
		multiMap.add("access_token", token.getAccess_token());
		multiMap.add("code", code);
		HttpResponse response = httpRequest.send();
		System.out.println(response.bodyText());
		return "OAuthTest";
	}

	@RequestMapping(value = "/stat/job")
	public String statJob(@RequestParam("code") String code, Model model) {
		String listJson = mobileService.statJob(code);
		model.addAttribute("listJson", listJson);
		return "stat_job";
	}

	@RequestMapping(value = "/stat/obj")
	public String statObj(@RequestParam("code") String code, Model model) {
		String listJson = mobileService.statObj(code);
		model.addAttribute("listJson", listJson);
		return "stat_obj";
	}

	@RequestMapping(value = "/detail/obj")
	public String detailObj(@RequestParam("objId") String objId, Model model) {
		String listJson = mobileService.detailObj(objId);
		model.addAttribute("listJson", listJson);
		return "stat_obj";
	}

	@RequestMapping(value = "/top/job")
	public String topJob(@RequestParam("code") String code, Model model) {
		String topModelJson = mobileService.topJob(code);
		model.addAttribute("topModelJson", topModelJson);
		return "top_job";
	}

	@RequestMapping(value = "/top/obj")
	public String topObj(@RequestParam("code") String code, Model model) {
		String topModelJson = mobileService.topObj(code);
		model.addAttribute("topModelJson", topModelJson);
		return "top_obj";
	}

	@RequestMapping(value = "/top/api")
	public String topApi(@RequestParam("code") String code, Model model) {
		String topModelJson = mobileService.topApi(code);
		model.addAttribute("topModelJson", topModelJson);
		return "top_api";
	}

	@RequestMapping(value = "/stat/api")
	public String statApi(@RequestParam("code") String code, Model model) {
		List<MonitorApi> monitorApis = mobileService.getMonitorProjectsByUser(code);
		String listJson = JSONObject.toJSONString(monitorApis);
		model.addAttribute("listJson", listJson);
		return "stat_api";
	}

	@RequestMapping(value = "/stat/apiData")
	@ResponseBody
	public ApiProStatModel statApiData(@RequestParam("proId") Integer proId) {
		return mobileService.getApiStatDetail(proId, "").get(0);
	}

	@RequestMapping(value = "/stat/apiList")
	public String statApiList(@RequestParam("proId") Integer proId, @RequestParam("apiPath") String apiPath, Model model) {
		MonitorApi monitorApi = apiService.getById(proId);
		model.addAttribute("proId", proId);
		model.addAttribute("apiPath", apiPath);
		if (monitorApi != null && !monitorApi.getProName().isEmpty()) {
			model.addAttribute("proName", monitorApi.getProName());
		} else {
			model.addAttribute("proName", "无此项目");
		}
		return "stat_api_list";
	}

	@RequestMapping(value = "/stat/apiListData")
	@ResponseBody
	public List<ApiSimpleModel> statApiListData(@RequestParam("proId") Integer proId, @RequestParam("apiPath") String apiPath, @RequestParam("pageindex") Integer pageIndex) {
		return mobileService.getApiListByProAndApi(proId, apiPath, pageIndex);
	}

	@RequestMapping(value = "/stat/apiDetail")
	public String statApiDetail(@RequestParam("apiId") String apiId, Model model) {
		ApiFullModel apiFullModel = mobileService.getById(apiId);
		if (apiFullModel == null) {
			model.addAttribute("apiobj", "{}");
		} else {
			model.addAttribute("apiobj", JSONObject.toJSONString(apiFullModel));
		}
		return "stat_api_detail";
	}
}
