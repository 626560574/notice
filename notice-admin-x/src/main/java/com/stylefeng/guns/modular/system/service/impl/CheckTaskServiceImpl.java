package com.stylefeng.guns.modular.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giveu.notice.common.dto.JobMonitorDTO;
import com.giveu.notice.common.vo.CheckTaskVo;
import com.giveu.notice.common.vo.ResultModel;
import com.stylefeng.guns.modular.system.service.ICheckTaskService;
import jodd.http.HttpMultiMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: ScheduleCenter
 * @description: 任务预警实现类
 * @author: qin
 * @create: 2018-08-18 12:12
 **/
@Service
public class CheckTaskServiceImpl implements ICheckTaskService {
    private static final int OK = 200;

//    private static final String dataUrl = "http://noticecenter.jyfq.com/";
//    private static final String dataUrl = "http://localhost:9000/";
    private static final String dataUrl = "http://localhost/";

    /**
     * @Description: 获取列表
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    @Override
    public void list(HttpServletRequest request, List<Map<String, Object>> list) throws IOException {
        String jobName = request.getParameter("jobName");
        String jobCode = request.getParameter("jobCode");

        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkTask/list");
        HttpMultiMap multiMap = httpRequest.query();
        multiMap.add("jobName", jobName);
        multiMap.add("jobCode", jobCode);
        HttpResponse response = httpRequest.send();

        String result = response.bodyText();
        ResultModel resultModel = JSONObject.parseObject(result, ResultModel.class);
        List<JobMonitorDTO> jobMonitorDTOList = JSONObject.parseArray(String.valueOf(resultModel.getData()), JobMonitorDTO.class);

        for (JobMonitorDTO dto : jobMonitorDTOList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", dto.getId());
            map.put("jobId", dto.getJobId());
            map.put("jobName", dto.getJobName());
            map.put("jobCode", dto.getJobCode());
            map.put("monitorStatus", dto.getMonitorStatus());
            map.put("monitorStatusDesc", dto.getMonitorStatusDesc());
            map.put("recObj", dto.getRecObj());
            map.put("createTimeDesc", dto.getCreateTimeDesc());
            map.put("updateTimeDesc", dto.getUpdateTimeDesc());
            list.add(map);
        }
    }

    /**
     * @Description: 增加任务
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    @Override
    public Integer add(JobMonitorDTO jobMonitorDTO) throws IOException{
        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkTask/add");
        HttpMultiMap multiMap = httpRequest.query();

        multiMap.add("id", jobMonitorDTO.getId());
        multiMap.add("jobId", jobMonitorDTO.getJobId());
        multiMap.add("jobCode", jobMonitorDTO.getJobCode());
        multiMap.add("recObj", jobMonitorDTO.getRecObj());
        multiMap.add("monitorStatus", jobMonitorDTO.getMonitorStatus());

        HttpResponse response = httpRequest.send();
        ObjectMapper mapper = new ObjectMapper();
        String result = response.bodyText();
        ResultModel resultModel = mapper.readValue(result, ResultModel.class);
        return resultModel.getCode();
    }

    /**
     * @Description: 获取一个任务
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    @Override
    public CheckTaskVo getById(String Id,String name,String servicemac) throws IOException{

        CheckTaskVo checkTaskVo = new CheckTaskVo();
        checkTaskVo.setName(name);
        checkTaskVo.setServicemac(servicemac);
        checkTaskVo.setTaskno(Id);

        return checkTaskVo;
    }

    /**
     * @Description: 更新任务
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    @Override
    public Integer update(CheckTaskVo checkTaskVo) throws IOException{
        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkTask/update");
        HttpMultiMap multiMap = httpRequest.query();
        String id = checkTaskVo.getId();
        String name = checkTaskVo.getName();
        String taskno = checkTaskVo.getTaskno();
        String servicemac = checkTaskVo.getServicemac();
        String staffno = checkTaskVo.getStaffno();
        String createtime = checkTaskVo.getCreatetime();
        String updatetime = checkTaskVo.getUpdatetime();
        String status=checkTaskVo.getStatus();
        multiMap.add("id", id);
        multiMap.add("name", name);
        multiMap.add("taskno", taskno);
        multiMap.add("servicemac", servicemac);
        multiMap.add("staffno", staffno);
        multiMap.add("createtime", createtime);
        multiMap.add("updatetime", updatetime);
        multiMap.add("status", status);

        HttpResponse response = httpRequest.send();
        ObjectMapper mapper = new ObjectMapper();
        String result = response.bodyText();
        ResultModel resultModel = mapper.readValue(result, ResultModel.class);
        return resultModel.getCode();
    }

    /**
     * @Description: 删除任务
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    @Override
    public Integer delete(Integer Id) {
        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkTask/delete");
        HttpMultiMap multiMap = httpRequest.query();
        multiMap.add("Id", Id);
        HttpResponse response = httpRequest.send();
        return null;
    }
    @Override
    public String getListJson() {
        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkTask/list");
        HttpResponse response = httpRequest.send();
        String result = response.bodyText();
        return result;
    }
    @Override
    public Integer enableById(Integer id) {
        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkTask/enable/id");
        HttpMultiMap multiMap = httpRequest.query();
        multiMap.add("Id", id);
        HttpResponse response = httpRequest.send();
        return null;
    }

    @Override
    public Integer disableById(Integer id) {
        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkTask/disable/id");
        HttpMultiMap multiMap = httpRequest.query();
        multiMap.add("Id", id);
        HttpResponse response = httpRequest.send();
        return null;
    }
}
