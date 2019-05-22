package com.giveu.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/application.yml")
public class WxConfig {
    /*
    企业id
    */
    @Value("${wechat.corpid}")
    private String corpid;
    /*
     * 应用id
     * */
    @Value("${wechat.agentid}")
    private String agentid;
    /*
     * 应用secret*/
    @Value("${wechat.secret}")
    private String secret;

    /*
     * 部门id*/
    @Value("${wechat.department_id}")
    private String department_id;

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }


    public String getcorpid() {
        return corpid;
    }

    public void setcorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }


}
