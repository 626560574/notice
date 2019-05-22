package com.giveu.controller;

import com.giveu.notice.common.vo.ResultModel;
import com.giveu.service.ObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fox on 2018/10/23.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/checkData")
public class AdminObjController {

	@Autowired
	ObjService objService;

	@PostMapping(value = "/list")
	public ResultModel appList(HttpServletRequest request) throws Exception {
		ResultModel resultModel = new ResultModel();
		objService.list(request, resultModel);
		return resultModel;
	}

	@RequestMapping(value = "/add")
	public ResultModel addApp(HttpServletRequest request) throws Exception {
		ResultModel resultModel = new ResultModel();
		objService.add(request, resultModel);
		return resultModel;
	}


	@RequestMapping(value = "/update")
	public ResultModel updAppById(HttpServletRequest request) throws Exception {
		ResultModel resultModel = new ResultModel();
		objService.update(request, resultModel);
		return resultModel;
	}

	@RequestMapping(value = "/delete")
	public Integer delAppById(@RequestParam(value = "Id") Integer Id) throws Exception {
		return objService.delete(Id);
	}

	@RequestMapping(value = "/enable/id")
	public Integer enableById(@RequestParam(value = "Id") Integer Id) throws Exception {
		return objService.enableById(Id);
	}

	@RequestMapping(value = "/disable/id")
	public Integer disableById(@RequestParam(value = "Id") Integer Id) throws Exception {
		return objService.disableById(Id);
	}

}
