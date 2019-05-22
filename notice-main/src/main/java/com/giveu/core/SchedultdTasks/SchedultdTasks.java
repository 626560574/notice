package com.giveu.core.SchedultdTasks;

import com.giveu.core.Token.TokenUtil;
import com.giveu.core.Wechat.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: ScheduleCenter
 * @description: 定时任务
 * @author: qin
 * @create: 2018-08-16 20:53
 **/
@Component
@EnableScheduling
public class SchedultdTasks {
    private Logger logger = LoggerFactory.getLogger(SchedultdTasks.class);
    private final static long tokenTime = 7000 * 1000;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    UserUtil userUtil;


    /**
     * @Description: 更新token
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/9/3
     */
    @Scheduled(fixedDelay = tokenTime)   //每7000秒执行一次
    public void getToken() {
        tokenUtil.getAccessToken();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info(sdf.format(new Date()) + "*********更新token成功，每次间隔7000秒");
    }
}
