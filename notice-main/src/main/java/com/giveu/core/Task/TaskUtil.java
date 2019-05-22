package com.giveu.core.Task;

import com.giveu.component.Token;
import com.giveu.component.Url;
import com.giveu.component.WxConfig;
import com.giveu.core.Http.HttpRequest;
import com.giveu.core.Wechat.UserUtil;
import com.giveu.entity.WxCheckTask;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: ScheduleCenter
 * @description: 从韩斌接口获取任务数据
 * @author: qin
 * @create: 2018-08-21 11:32
 **/
@Component
public class TaskUtil {
    @Autowired
    Url url;
    @Autowired
    WxConfig wxConfig;
    @Autowired
    HttpRequest httpRequest;
    @Autowired
    Token token;

    //定义一个全局的记录器，通过LoggerFactory获取
    private static final Logger logger = LoggerFactory.getLogger(UserUtil.class);

    /**
     * @Description:获取所有任务信息
     * @Param: []
     * @return: toeken
     * @Author: qin
     * @Date: 2018/8/15
     */
    public List<WxCheckTask> getTask() {
        try {
            List<WxCheckTask> wxCheckTaskList = new ArrayList<WxCheckTask>();
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("id", "");
            map.add("jobCode", "");
            map.add("appKey", "");
            map.add("userAccount", "admin");
            String json = httpRequest.HttpPost(url.getTaskListUrl(), map);
            JSONObject jsonObject = JSONObject.fromObject(json);
            if (!jsonObject.isEmpty()) {
                logger.info(jsonObject.toString());
            }
            /*
             * 判断是否存在用户列表
             * */
            if (jsonObject.has("code") && Integer.parseInt(jsonObject.get("code").toString())== 200) {
                JSONArray array = jsonObject.getJSONArray("data");
                if (array != null && array.size() > 0) {
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        WxCheckTask wxCheckTask = new WxCheckTask();
                        wxCheckTask.setTaskno(item.get("id").toString());
                        wxCheckTask.setName(item.get("jobName").toString());
                        wxCheckTask.setServicemac(item.get("jobDesc").toString());
                        wxCheckTaskList.add(wxCheckTask);
                    }
                }
            }
            return wxCheckTaskList;
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            return null;
        }
    }
}
