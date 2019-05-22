package com.stylefeng.guns.modular.system.controller;

import com.giveu.notice.common.vo.MonitorRecVo;
import com.giveu.notice.common.vo.ResultModel;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.modular.system.service.IWxUserService;
import com.stylefeng.guns.modular.system.warpper.MenuWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 预警对象管理
 */
@Controller
@RequestMapping(value = "/receiver")
public class ReceiverController extends BaseController {
	private String PREFIX = "/system/receiver/";

	@Autowired
	IWxUserService userService;

	/**
	 * 跳转到列表首页
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "receiverList.html";
	}

	/**
	 * 跳转到添加页面
	 */
	@RequestMapping("/receiver_add")
	public String appListAdd() throws IOException {
		return PREFIX + "receiver_add.html";
	}

	/**
	 * 跳转到修改
	 */
	@RequestMapping("/receiver_update/{id}")
	public String appListUpdate(@PathVariable Integer id, Model model) throws IOException {
		MonitorRecVo monitorRecVo = userService.getById(id);
		model.addAttribute("receiverList", monitorRecVo);
		return PREFIX + "receiver_edit.html";
	}

	/**
	 * 获取用户列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object userList(HttpServletRequest request) throws IOException {
		List<Map<String, Object>> list = new ArrayList<>();
		userService.list(request, list);

		List<Map<String, Object>> menus = list;
		return super.warpObject(new MenuWarpper(menus));
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(@Valid MonitorRecVo monitorRecVo, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		ResultModel resultModel = userService.add(monitorRecVo);

		resultModel.SetSuccess("操作成功", null);
		return result;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam(value = "id") Integer id) {
		userService.delete(id);
		return SUCCESS_TIP;
	}

	/**
	 * 启用
	 */
	@RequestMapping(value = "/enable")
	@ResponseBody
	public Object enable(@RequestParam(value = "id") Integer id) {
		userService.enableById(id);
		return SUCCESS_TIP;
	}

	/**
	 * 禁用
	 */
	@RequestMapping(value = "/disable")
	@ResponseBody
	public Object disable(@RequestParam(value = "id") Integer id) {
		userService.disableById(id);
		return SUCCESS_TIP;
	}


	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(@Valid MonitorRecVo monitorRecVo, BindingResult result) throws IOException {
		if (result.hasErrors()) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		ResultModel resultModel = userService.update(monitorRecVo);

		resultModel.SetSuccess("操作成功", null);
		return super.SUCCESS_TIP;
	}

	/**
	 * 同步人员微信资料
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/syncwechat")
	@ResponseBody
	public Object syncwechat(HttpServletRequest request) throws IOException {
		userService.syncwechat();
		return super.SUCCESS_TIP;
	}

}
