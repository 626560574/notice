package com.giveu.core.Token;

import com.giveu.common.CommonToken;
import com.giveu.component.Token;
import com.giveu.component.Url;
import com.giveu.component.WxConfig;
import com.giveu.core.Http.HttpRequest;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: ScheduleCenter
 * @description: 微信token
 * @author: qin
 * @create: 2018-08-15 11:09
 **/
@Component
public class TokenUtil {
    @Autowired
    Url url;
    @Autowired
    WxConfig wxConfig;
    @Autowired
    HttpRequest httpRequest;
    @Autowired
    Token token;

    //定义一个全局的记录器，通过LoggerFactory获取
    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);
    /**
     * @Description:插入token到数据库
     * @Param: []
     * @return: toeken
     * @Author: qin
     * @Date: 2018/8/15
     */
    public  void getAccessToken() {
        try {
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("corpid", wxConfig.getcorpid());
            para.put("corpsecret", wxConfig.getSecret());
            String json = httpRequest.HttpGet(url.getTokenUrl(), para);
            JSONObject jsonObject = JSONObject.fromObject(json);
            if(!jsonObject.isEmpty())
            {
                logger.info(jsonObject.toString());
            }
            if(jsonObject.has("access_token"))
            token.setAccess_token(jsonObject.getString("access_token"));
            CommonToken.token = jsonObject.getString("access_token");
            if(jsonObject.has("expires_in"))
            token.setExpires_in(new Integer(jsonObject.getString("expires_in")));
        } catch (Exception e) {
             logger.error(e.getStackTrace().toString());
        }
    }
}
