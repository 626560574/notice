package com.giveu.notice.common.vo;

import java.io.Serializable;

/**
 * @program: ScheduleCenter
 * @description: 任务预警
 * @author: qin
 * @create: 2018-08-18 12:08
 **/

public class WxUserVo implements Serializable{
    private static final long serialVersionUID = -5414394448065561438L;
    private Integer id;

    private String staffno;

    private String name;

    private String phone;

    private String email;

    private String wc;

    private String userid;

    private String partyid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStaffno() {
        return staffno;
    }

    public void setStaffno(String staffno) {
        this.staffno = staffno == null ? null : staffno.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWc() {
        return wc;
    }

    public void setWc(String wc) {
        this.wc = wc == null ? null : wc.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getPartyid() {
        return partyid;
    }

    public void setPartyid(String partyid) {
        this.partyid = partyid == null ? null : partyid.trim();
    }
}
