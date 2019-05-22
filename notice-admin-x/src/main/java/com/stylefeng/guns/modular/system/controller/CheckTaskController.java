package com.stylefeng.guns.modular.system.controller;

import com.giveu.notice.common.dto.JobMonitorDTO;
import com.giveu.notice.common.vo.CheckTaskVo;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.modular.system.service.ICheckTaskService;
import com.stylefeng.guns.modular.system.service.IWxUserService;
import com.stylefeng.guns.modular.system.warpper.MenuWarpper;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
 * @description: 任务预警
 * @author: qin
 * @create: 2018-08-18 15:00
 **/
@Controller
@RequestMapping("/checkTask")
public class CheckTaskController extends BaseController {
    private String PREFIX = "/system/wxCheckTask/";

    @Autowired
    ICheckTaskService checkTaskService;
    @Autowired
    IWxUserService userService;
    /**
     * 跳转到列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "taskList.html";
    }

    /**
     * 跳转到添加列表
     */
    @RequestMapping("/taskList_add")
    public String appListAdd() throws IOException{
        return PREFIX + "taskList_add.html";
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
     * 获取用户列表
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
     * 跳转到修改应用列表
     */
    @RequestMapping("/taskList_update")
    public String appListUpdate(HttpServletRequest request, Model model) throws IOException {
        String id = request.getParameter("id");
        String jobId = request.getParameter("jobId");
        String jobName = request.getParameter("jobName");
        String jobCode = request.getParameter("jobCode");
        String monitorStatus = request.getParameter("monitorStatus");
        JobMonitorDTO jobMonitorDTO = new JobMonitorDTO();
        jobMonitorDTO.setId(NumberUtils.toInt(id));
        jobMonitorDTO.setJobId(jobId);
        jobMonitorDTO.setJobName(jobName);
        jobMonitorDTO.setJobCode(jobCode);
        jobMonitorDTO.setMonitorStatus(NumberUtils.toInt(monitorStatus, 1));
        model.addAttribute("jobMonitorDTO", jobMonitorDTO);
        return PREFIX + "taskList_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(HttpServletRequest request) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        checkTaskService.list(request, list);

        List<Map<String, Object>> menus = list;
        return super.warpObject(new MenuWarpper(menus));
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid JobMonitorDTO jobMonitorDTO, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Integer code = checkTaskService.add(jobMonitorDTO);

        return super.SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "id") Integer id) {
        checkTaskService.delete(id);
        return SUCCESS_TIP;
    }
    /**
     * 启用
     */
    @RequestMapping(value = "/enable")
    @ResponseBody
    public Object enable(@RequestParam(value = "id") Integer id) {
        checkTaskService.enableById(id);
        return SUCCESS_TIP;
    }
    /**
     * 禁用
     */
    @RequestMapping(value = "/disable")
    @ResponseBody
    public Object disable(@RequestParam(value = "id") Integer id) {
        checkTaskService.disableById(id);
        return SUCCESS_TIP;
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid CheckTaskVo checkTaskVo, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        checkTaskService.update(checkTaskVo);
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
