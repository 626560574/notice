package com.stylefeng.guns.modular.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giveu.notice.common.dto.ObjMonitorDTO;
import com.giveu.notice.common.vo.CheckDataVo;
import com.giveu.notice.common.vo.ResultModel;
import com.stylefeng.guns.modular.system.service.ICheckDataService;
import jodd.http.HttpMultiMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stylefeng.guns.modular.system.service.impl.WxUserServiceImpl.staffnoList;

/**
 * @program: ScheduleCenter
 * @description: 数据预警实现类
 * @author: qin
 * @create: 2018-08-18 12:12
 **/
@Service
public class CheckDataServiceImpl implements ICheckDataService {
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
        String objName = request.getParameter("objName");

        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkData/list");
        HttpMultiMap multiMap = httpRequest.query();
        multiMap.add("objName", objName);
        HttpResponse response = httpRequest.send();

        String result = response.bodyText();
        ResultModel resultModel = JSONObject.parseObject(result, ResultModel.class);
        List<ObjMonitorDTO> objMonitorDTOList = JSONObject.parseArray(String.valueOf(resultModel.getData()), ObjMonitorDTO.class);

        for (ObjMonitorDTO dto : objMonitorDTOList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", dto.getId());
            map.put("objId", dto.getObjId());
            map.put("objName", dto.getObjName());
            map.put("objCode", dto.getObjCode());
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
    public Integer add(ObjMonitorDTO objMonitorDTO) throws IOException{

        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkData/add");
        HttpMultiMap multiMap = httpRequest.query();

        multiMap.add("id", objMonitorDTO.getId());
        multiMap.add("objId", objMonitorDTO.getObjId());
        multiMap.add("objCode", objMonitorDTO.getObjCode());
        multiMap.add("objName", objMonitorDTO.getObjName());
        multiMap.add("recObj", objMonitorDTO.getRecObj());
        multiMap.add("monitorStatus", objMonitorDTO.getMonitorStatus());

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
    public CheckDataVo getById(Integer Id) throws IOException{

        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkData/getById");
        HttpMultiMap multiMap = httpRequest.query();
        multiMap.add("Id", Id);
        HttpResponse response = httpRequest.send();
        ObjectMapper mapper = new ObjectMapper();
        String result = response.bodyText();
        CheckDataVo checkDataVo = mapper.readValue(result, CheckDataVo.class);
        staffnoList=checkDataVo.getStaffno();
        return checkDataVo;
    }

    /**
     * @Description: 更新任务
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    @Override
    public Integer update(CheckDataVo checkDataVo) throws IOException{
        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkData/update");
        HttpMultiMap multiMap = httpRequest.query();
        String id = checkDataVo.getId();
        String name = checkDataVo.getName();
        String identification = checkDataVo.getIdentification();
        String comment = checkDataVo.getComment();
        String staffno = checkDataVo.getStaffno();
        String createtime = checkDataVo.getCreatetime();
        String updatetime = checkDataVo.getUpdatetime();
        String status=checkDataVo.getStatus();
        multiMap.add("id", id);
        multiMap.add("name", name);
        multiMap.add("identification", identification);
        multiMap.add("comment", comment);
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
        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkData/delete");
        HttpMultiMap multiMap = httpRequest.query();
        multiMap.add("Id", Id);
        HttpResponse response = httpRequest.send();
        return null;
    }
    @Override
    public String getListJson() {
        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkData/list");
        HttpResponse response = httpRequest.send();
        String result = response.bodyText();
        return result;
    }
    @Override
    public Integer enableById(Integer id) {
        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkData/enable/id");
        HttpMultiMap multiMap = httpRequest.query();
        multiMap.add("Id", id);
        HttpResponse response = httpRequest.send();
        return null;
    }

    @Override
    public Integer disableById(Integer id) {
        HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkData/disable/id");
        HttpMultiMap multiMap = httpRequest.query();
        multiMap.add("Id", id);
        HttpResponse response = httpRequest.send();
        return null;
    }
}
