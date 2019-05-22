package com.giveu.controller;

import com.giveu.entity.MonitorRec;
import com.giveu.notice.common.vo.ResultModel;
import com.giveu.service.RecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * 微信预警对象控制器
 * Created by fox on 2018/10/23.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/wxUser")
public class AdminRecController {

	@Autowired
	private RecService recService;

	/**
	 * 获取预警对象列表
	 *
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/list")
	public ResultModel list(HttpServletRequest request) {
		ResultModel resultModel = new ResultModel();
		recService.list(request, resultModel);
		return resultModel;
	}

	/**
	 * 获取预警对象列表
	 *
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/Search")
	public ResultModel Search(HttpServletRequest request) {
		ResultModel resultModel = new ResultModel();
		recService.Search(request, resultModel);
		return resultModel;
	}

	/**
	 * 添加预警对象
	 *
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/add")
	public ResultModel add(HttpServletRequest request) {
		ResultModel resultModel = new ResultModel();
		recService.add(request, resultModel);
		return resultModel;
	}

	/**
	 * 删除预警对象
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/del")
	public Integer del(@RequestParam("id") Integer id) {
		return recService.delete(id);
	}

	/**
	 * 编辑预警对象
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ResultModel edit(HttpServletRequest request) {
		ResultModel resultModel = new ResultModel();
		recService.update(request, resultModel);
		return resultModel;

	}

	/**
	 * 获取预警对象详情
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getById")
	public MonitorRec getById(@RequestParam(value = "id") Integer id) {
		return recService.getById(id);
	}

	/**
	 * 启用
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/enable/id")
	public Integer enableById(@RequestParam(value = "id") Integer id) {
		return recService.enableById(id);
	}

	/**
	 * 禁用
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/disable/id")
	public Integer disableById(@RequestParam(value = "id") Integer id) {
		return recService.disableById(id);
	}

	/**
	 * 同步微信人员信息
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/syncwechat")
	public Integer syncwechat(HttpServletRequest request) {
		return recService.syncwechat();
	}
}
