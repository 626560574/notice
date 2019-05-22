package com.giveu.component;

import org.springframework.stereotype.Component;

@Component
public class Token {
    private String access_token; //获取到的access_token
    private int expires_in; //有效时间（两个小时，7200s）
    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }



    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
