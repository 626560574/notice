package com.giveu.controller;

import com.giveu.entity.MonitorApi;
import com.giveu.notice.common.vo.ResultModel;
import com.giveu.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 监控接口控制器
 * Created by fox on 2018/10/23.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/checkInterface")
public class AdminApiController {

	@Autowired
	private ApiService apiService;

	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/list")
	public ResultModel appList(HttpServletRequest request) {
		ResultModel resultModel = new ResultModel();
		apiService.list(request, resultModel);
		return resultModel;
	}

	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add")
	public ResultModel addApp(HttpServletRequest request) {
		ResultModel resultModel = new ResultModel();
		apiService.add(request, resultModel);
		return resultModel;
	}

	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getById")
	public MonitorApi getAppVoById(@RequestParam(value = "id") Integer id) {
		return apiService.getById(id);
	}

	/**
	 * 更新
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update")
	public ResultModel updAppById(HttpServletRequest request) {
		ResultModel resultModel = new ResultModel();
		apiService.update(request, resultModel);
		return resultModel;
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete")
	public Integer delAppById(@RequestParam(value = "id") Integer id) {
		return apiService.delete(id);
	}

	/**
	 * 启用
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/enable/id")
	public Integer enableById(@RequestParam(value = "id") Integer id) {
		return apiService.enableById(id);
	}

	/**
	 * 禁用
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/disable/id")
	public Integer disableById(@RequestParam(value = "id") Integer id) {
		return apiService.disableById(id);
	}
}
