package com.giveu.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @program: ScheduleCenter
 * @description: 微信接口地址
 * @author: qin
 * @create: 2018-08-15 11:26
 **/
@Component
@PropertySource("classpath:/application.yml")
public class Url {
    /*
     * token接口地址
     * */
    @Value("${wechat.url.tokenurl}")
    private String TokenUrl;
    /*
     * 获取所有员工地址
     * */
    @Value("${wechat.url.userlisturl}")
    private String UserListUrl;

    public String getSendMsgUrl() {
        return SendMsgUrl;
    }

    public void setSendMsgUrl(String sendMsgUrl) {
        SendMsgUrl = sendMsgUrl;
    }

    /*
     * 获取所有员工地址
     * */
    @Value("${wechat.url.sendMsgUrl}")
    private String SendMsgUrl;

    public String getTaskListUrl() {
        return TaskListUrl;
    }

    public void setTaskListUrl(String taskListUrl) {
        TaskListUrl = taskListUrl;
    }

    /**
     * @Description: 任务列表地址
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/21
     */
    @Value("${wechat.url.taskListUrl}")
    private String TaskListUrl;

    public String getDataListUrl() {
        return DataListUrl;
    }

    public void setDataListUrl(String dataListUrl) {
        DataListUrl = dataListUrl;
    }

    /**
     * @Description: 数据列表地址
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/21
     */
    @Value("${wechat.url.dataListUrl}")
    private String DataListUrl;

    public String getUserListUrl() {
        return UserListUrl;
    }

    public void setUserListUrl(String userListUrl) {
        UserListUrl = userListUrl;
    }


    public String getTokenUrl() {
        return TokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        TokenUrl = tokenUrl;
    }
}
