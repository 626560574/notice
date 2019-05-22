package com.giveu.component;

import com.giveu.core.Token.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//@Component
//@EnableScheduling
public class ScheduleToken {
    private Logger logger = LoggerFactory.getLogger(ScheduleToken.class);
    private final static long tokenTime = 7000 * 1000;
//    private final static long tokenTime =  1000;
    @Autowired
    TokenUtil tokenUtil;
    @Scheduled(fixedDelay = tokenTime)   //每7000秒执行一次
    public void getToken() {
        tokenUtil.getAccessToken();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info(sdf.format(new Date()) + "*********更新token成功，每次间隔7000秒");
    }
}
