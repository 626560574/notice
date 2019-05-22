package com.giveu.model;

/**
 * @program: ScheduleCenter
 * @description: 文本消息
 * @author: qin
 * @create: 2018-08-21 19:44
 **/

public class Text {
    //是    消息内容，最长不超过2048个字节
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
