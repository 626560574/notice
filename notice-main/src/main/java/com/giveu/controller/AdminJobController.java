package com.giveu.controller;

import com.giveu.notice.common.vo.ResultModel;
import com.giveu.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fox on 2018/10/23.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/checkTask")
public class AdminJobController {

	@Autowired
	JobService jobService;

	@PostMapping(value = "/list")
	public ResultModel appList(HttpServletRequest request) throws Exception {
		ResultModel resultModel = new ResultModel();
		jobService.list(request, resultModel);
		return resultModel;
	}

	@RequestMapping(value = "/add")
	public ResultModel addApp(HttpServletRequest request) throws Exception {
		ResultModel resultModel = new ResultModel();
		jobService.add(request, resultModel);
		return resultModel;
	}


	@RequestMapping(value = "/update")
	public ResultModel updAppById(HttpServletRequest request) throws Exception {
		ResultModel resultModel = new ResultModel();
		jobService.update(request, resultModel);
		return resultModel;
	}

	@RequestMapping(value = "/delete")
	public Integer delAppById(@RequestParam(value = "id") Integer id) throws Exception {
		return jobService.delete(id);
	}

	@RequestMapping(value = "/enable/id")
	public Integer enableById(@RequestParam(value = "id") Integer id) throws Exception {
		return jobService.enableById(id);
	}

	@RequestMapping(value = "/disable/id")
	public Integer disableById(@RequestParam(value = "id") Integer id) throws Exception {
		return jobService.disableById(id);
	}
}
