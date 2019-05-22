package com.giveu.core.Data;

import com.giveu.component.Token;
import com.giveu.component.Url;
import com.giveu.component.WxConfig;
import com.giveu.core.Http.HttpRequest;
import com.giveu.entity.WxCheckData;
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
 * @description: 从韩斌接口处获取数据
 * @author: qin
 * @create: 2018-08-21 17:07
 **/
@Component
public class DataUtil {
    @Autowired
    Url url;
    @Autowired
    WxConfig wxConfig;
    @Autowired
    HttpRequest httpRequest;
    @Autowired
    Token token;

    //定义一个全局的记录器，通过LoggerFactory获取
    private static final Logger logger = LoggerFactory.getLogger(DataUtil.class);

    /**
     * @Description:获取所有数据信息
     * @Param: []
     * @return: toeken
     * @Author: qin
     * @Date: 2018/8/15
     */
    public List<WxCheckData> getData() {
        try {
            List<WxCheckData> wxCheckDataList = new ArrayList<WxCheckData>();
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            String json = httpRequest.HttpPost(url.getDataListUrl(), map);
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
                        WxCheckData wxCheckData = new WxCheckData();
                        wxCheckData.setName(item.get("objName").toString());
                        wxCheckData.setIdentification(item.get("id").toString());
                        wxCheckData.setComment(item.get("objDesc").toString());
                        wxCheckDataList.add(wxCheckData);
                    }
                }
            }
            return wxCheckDataList;
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            return null;
        }
    }
}
