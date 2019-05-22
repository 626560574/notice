package com.giveu.core.Wechat;

import com.alibaba.fastjson.JSON;
import com.giveu.component.Token;
import com.giveu.component.Url;
import com.giveu.core.Http.HttpRequest;
import com.giveu.entity.User;
import com.giveu.model.Text;
import com.giveu.model.TextMessage;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: ScheduleCenter
 * @description: 发送消息
 * @author: qin
 * @create: 2018-08-20 22:19
 **/
@Component
public class SendMsgUtil {
    @Autowired
    Url url;
    @Autowired
    HttpRequest httpRequest;
    @Autowired
    Token token;

    //定义一个全局的记录器，通过LoggerFactory获取
    private static final Logger logger = LoggerFactory.getLogger(SendMsgUtil.class);

    /**
     * @Description:发送信息
     * @Param: []
     * @return: toeken
     * @Author: qin
     * @Date: 2018/8/15
     */
    public  com.alibaba.fastjson.JSONObject sendMsg(String content,String users) {
        try {
            //1.创建文本消息对象
            TextMessage message=new TextMessage();
            String acess_token=token.getAccess_token();
            Map<String,Object> params = new HashMap<>();
            List<User> userUtilList = new ArrayList<User>();
            //1.1非必需
            message.setTouser(users);  //不区分大小写
            //1.2必需
            message.setMsgtype("text");
            message.setAgentid(1000002);
            message.setToparty("@all");
            Text text=new Text();
            text.setContent(content);
            message.setText(text);
            Object postData=JSON.toJSON(message);

            String msgurl=url.getSendMsgUrl()+"access_token="+acess_token;
            com.alibaba.fastjson.JSONObject json = httpRequest.HttpPostPara(msgurl,postData);
            if (!json.isEmpty()) {
                logger.info(json.toString());
            }
            logger.info(com.alibaba.fastjson.JSONObject.toJSONString(json));
            return json;
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            return null;
        }
    }
}
