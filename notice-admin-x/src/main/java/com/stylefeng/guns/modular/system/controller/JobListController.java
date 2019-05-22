package com.stylefeng.guns.modular.system.controller;

import com.giveu.notice.common.vo.JobTriggerAppVo;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.modular.system.service.IAppListService;
import com.stylefeng.guns.modular.system.service.IJobListService;
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
 * 任务列表控制器
 *
 * @author fengshuonan
 * @Date 2018-07-03 17:03:27
 */
@Controller
@RequestMapping("/jobList")
public class JobListController extends BaseController {

    private String PREFIX = "/system/jobList/";

    @Autowired
    IAppListService appListService;

    @Autowired
    IJobListService jobListService;


    /**
     * 跳转到任务列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "jobList.html";
    }

//    /**
//     * 跳转到任务列表首页
//     */
//    @RequestMapping("/show/log")
//    public String showLog() {
//        return PREFIX + "../jobLogList/jobLogList.html";
//    }

    /**
     * 跳转到添加任务列表
     */
    @RequestMapping("/jobList_add")
    public String jobListAdd(Model model) throws IOException {
        String appListJson = appListService.getListJson();
        model.addAttribute("appListJson", appListJson);
        return PREFIX + "jobList_add.html";
    }

    /**
     * 跳转到修改任务列表
     */
    @RequestMapping("/jobList_update/{jobListId}")
    public String jobListUpdate(@PathVariable Integer jobListId, Model model) {
        return PREFIX + "jobList_edit.html";
    }

    /**
     * 获取任务列表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(HttpServletRequest request) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();

        jobListService.list(request, list);

        List<Map<String, Object>> menus = list;

        return super.warpObject(new MenuWarpper(menus));
    }

    /**
     * 新增任务列表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid JobTriggerAppVo jobTriggerAppVo, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        System.out.println(jobTriggerAppVo);
        Integer code = jobListService.addJob(jobTriggerAppVo);
        if (code == 517 || code.equals(517)) {
            throw new Exception(" CRON 格式错误");

        }
        if (code == 518 || code.equals(518)) {
            throw new Exception(" JobName 已经存在");

        }
        return super.SUCCESS_TIP;
    }

    /**
     * 删除任务列表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "jobCode") String jobCode, @RequestParam(value = "appKey") String appKey) throws IOException {
        System.out.println(jobCode);
        System.out.println(appKey);
        jobListService.delJobByCode(jobCode, appKey);
        return SUCCESS_TIP;
    }


    /**
     * 手动触发
     */
    @RequestMapping(value = "/trigger/log/id")
    @ResponseBody
    public Object triggerByLogId(@RequestParam(value = "jobLogListId") String jobLogListId) throws IOException {
        System.out.println(jobLogListId);
        jobListService.triggerByLogId(jobLogListId);
        return SUCCESS_TIP;
    }


    /**
     * 修改任务列表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 任务列表详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
