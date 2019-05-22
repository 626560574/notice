package com.stylefeng.guns.modular.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giveu.notice.common.vo.JobTriggerAppLogVo;
import com.giveu.notice.common.vo.ResultModel;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.modular.system.warpper.MenuWarpper;
import jodd.http.HttpMultiMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志列表控制器
 *
 * @author fengshuonan
 * @Date 2018-07-03 14:35:10
 */
@Controller
@RequestMapping("/jobLogList")
public class JobLogListController extends BaseController {

    private String PREFIX = "/system/jobLogList/";

    private static final int OK = 200;

    private static final String dataUrl = "http://localhost:8765/job/log/list";

    /**
     * 跳转到日志列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "jobLogList.html";
    }

    /**
     * 跳转到添加日志列表
     */
    @RequestMapping("/jobLogList_add")
    public String jobLogListAdd() {
        return PREFIX + "jobLogList_add.html";
    }

    /**
     * 跳转到修改日志列表
     */
    @RequestMapping("/jobLogList_update/{jobLogListId}")
    public String jobLogListUpdate(@PathVariable Integer jobLogListId, Model model) {
        return PREFIX + "jobLogList_edit.html";
    }

    /**
     * 获取日志列表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(HttpServletRequest request) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();

        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String jobId = request.getParameter("jobId");
        String jobCode = request.getParameter("jobCode");
        String executeStatus = request.getParameter("executeStatus");

        HttpRequest httpRequest = HttpRequest.post(dataUrl);
        HttpMultiMap multiMap = httpRequest.query();
        multiMap.add("beginTime", beginTime);
        multiMap.add("endTime", endTime);
        multiMap.add("jobId", jobId);
        multiMap.add("jobCode", jobCode);
        multiMap.add("executeStatus", executeStatus);

        HttpResponse response = httpRequest.send();

        ObjectMapper mapper = new ObjectMapper();
        String result = response.bodyText();
        ResultModel resultModel = mapper.readValue(result, ResultModel.class);
        List<JobTriggerAppLogVo> jobTriggerAppLogVoList = null;
        if (resultModel.getCode() == OK) {
            jobTriggerAppLogVoList = (ArrayList<JobTriggerAppLogVo>)resultModel.getData();

        }

        for (int i = 0; i < jobTriggerAppLogVoList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", ((Map)jobTriggerAppLogVoList.get(i)).get("id"));
            map.put("jobName", ((Map)jobTriggerAppLogVoList.get(i)).get("jobName"));
            map.put("jobDesc", ((Map)jobTriggerAppLogVoList.get(i)).get("jobDesc"));
            map.put("jobCode", ((Map)jobTriggerAppLogVoList.get(i)).get("jobCode"));
            map.put("appKey", ((Map)jobTriggerAppLogVoList.get(i)).get("appKey"));
            map.put("triggerTime", ((Map)jobTriggerAppLogVoList.get(i)).get("triggerTime"));
            map.put("leadTime", ((Map)jobTriggerAppLogVoList.get(i)).get("leadTime"));
            map.put("callbackUrl", ((Map)jobTriggerAppLogVoList.get(i)).get("callbackUrl"));
            map.put("callbackCount", ((Map)jobTriggerAppLogVoList.get(i)).get("callbackCount"));
            map.put("callbackCount", ((Map)jobTriggerAppLogVoList.get(i)).get("callbackCount"));
            map.put("statusDesc", ((Map)jobTriggerAppLogVoList.get(i)).get("statusDesc"));
            map.put("executeDesc", ((Map)jobTriggerAppLogVoList.get(i)).get("executeDesc"));
            list.add(map);
        }

        List<Map<String, Object>> menus = list;
        return super.warpObject(new MenuWarpper(menus));
    }

    /**
     * 新增日志列表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add() {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除日志列表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }




    /**
     * 修改日志列表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 日志列表详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
