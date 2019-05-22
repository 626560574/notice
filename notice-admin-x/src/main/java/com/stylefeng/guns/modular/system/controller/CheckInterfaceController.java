package com.stylefeng.guns.modular.system.controller;

import com.giveu.notice.common.vo.*;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.modular.system.service.ICheckInterfaceService;
import com.stylefeng.guns.modular.system.service.ICheckTaskService;
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
 * @program: ScheduleCenter
 * @description: 接口预警
 * @author: qin
 * @create: 2018-08-18 15:00
 **/
@Controller
@RequestMapping("/checkInterface")
public class CheckInterfaceController extends BaseController {
    private String PREFIX = "/system/wxCheckInterface/";

    @Autowired
    ICheckInterfaceService checkInterfaceService;
    @Autowired
    IWxUserService userService;
    /**
     * 跳转到应用列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "interfaceList.html";
    }

    /**
     * 跳转到添加应用列表
     */
    @RequestMapping("/interfaceList_add")
    public String appListAdd() throws IOException{
        return PREFIX + "interfaceList_add.html";
    }

    /**
     * 获取用户列表
     */
    @RequestMapping(value = "/userList")
    @ResponseBody
    public Object userList(HttpServletRequest request) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        userService.list(request, list);

        List<Map<String, Object>> menus = list;
        return super.warpObject(new MenuWarpper(menus));
    }

    /**
     *修改时获取用户列表，用来绑定已被选择的
     */
    @RequestMapping(value = "/editUserList")
    @ResponseBody
    public Object editUserList(HttpServletRequest request) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        userService.editList(request, list);

        List<Map<String, Object>> menus = list;
        return super.warpObject(new MenuWarpper(menus));
    }


    /**
     * 跳转到修改
     */
    @RequestMapping("/interfaceList_update/{id}")
    public String appListUpdate(@PathVariable Integer id, Model model) throws IOException {
        MonitorApiVo monitorApiVo = checkInterfaceService.getById(id);
        model.addAttribute("interfaceList", monitorApiVo);
        return PREFIX + "interfaceList_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(HttpServletRequest request) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        checkInterfaceService.list(request, list);

        List<Map<String, Object>> menus = list;
        return super.warpObject(new MenuWarpper(menus));
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid MonitorApiVo monitorApiVo, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        ResultModel resultModel = checkInterfaceService.add(monitorApiVo);

        resultModel.SetSuccess("操作成功",null);
        return result;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "id") Integer id) {
        checkInterfaceService.delete(id);
        return SUCCESS_TIP;
    }
    /**
     * 启用
     */
    @RequestMapping(value = "/enable")
    @ResponseBody
    public Object enable(@RequestParam(value = "id") Integer id) {
        checkInterfaceService.enableById(id);
        return SUCCESS_TIP;
    }
    /**
     * 禁用
     */
    @RequestMapping(value = "/disable")
    @ResponseBody
    public Object disable(@RequestParam(value = "id") Integer id) {
        checkInterfaceService.disableById(id);
        return SUCCESS_TIP;
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid MonitorApiVo monitorApiVo, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
		ResultModel resultModel = checkInterfaceService.update(monitorApiVo);

		resultModel.SetSuccess("操作成功",null);
        return super.SUCCESS_TIP;
    }

    /**
     * 列表详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
