package com.giveu.core.Wechat;

import com.giveu.component.Token;
import com.giveu.component.Url;
import com.giveu.component.WxConfig;
import com.giveu.core.Http.HttpRequest;
import com.giveu.core.Token.TokenUtil;
import com.giveu.entity.User;
import net.sf.json.JSONArray;
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
 * @description: 用户处理类
 * @author: qin
 * @create: 2018-08-16 13:40
 **/

@Component
public class UserUtil {
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
     * @Description:获取所有员工信息
     * @Param: []
     * @return: toeken
     * @Author: qin
     * @Date: 2018/8/15
     */
    public List<User> getUser() {
        try {
            List<User> userUtilList = new ArrayList<User>();
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("access_token", token.getAccess_token());
            para.put("department_id", wxConfig.getDepartment_id());
            para.put("fetch_child", "0");
            String json = httpRequest.HttpGet(url.getUserListUrl(), para);
            JSONObject jsonObject = JSONObject.fromObject(json);
            if (!jsonObject.isEmpty()) {
                logger.info(jsonObject.toString());
            }
            /*
             * 判断是否存在用户列表
             * */
            if (jsonObject.has("userlist")) {
                JSONArray array = jsonObject.getJSONArray("userlist");
                if (array != null && array.size() > 0) {
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        User user = new User();
                        user.setName(item.get("name").toString());
                        user.setAvatar(item.get("avatar").toString());
                        user.setDepartment(item.get("department").toString().replace("[","").replace("]",""));
                        user.setEmail(item.get("email").toString());
                        user.setEnable(item.get("enable").toString());
                        user.setEnglish_name(item.get("english_name").toString());
                        user.setExtattr(item.get("extattr").toString());
                        user.setGender(item.get("gender").toString());
                        user.setIsleader(item.get("isleader").toString());
                        user.setMobile(item.get("mobile").toString());
                        user.setOrder(item.get("order").toString().replace("[","").replace("]",""));
                        user.setPosition(item.get("position").toString());
                        user.setUserid(item.get("userid").toString());
                        user.setTelephone(item.get("telephone").toString());
                        user.setStatus(item.get("status").toString());
                        user.setQr_code(item.get("qr_code").toString());
                        userUtilList.add(user);
                    }
                }
            }
            return userUtilList;
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            return null;
        }
    }
}

