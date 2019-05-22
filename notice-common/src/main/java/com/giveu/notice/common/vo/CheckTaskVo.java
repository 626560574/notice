package com.giveu.notice.common.vo;

import java.io.Serializable;

/**
 * @program: ScheduleCenter
 * @description: 任务预警
 * @author: qin
 * @create: 2018-08-18 12:08
 **/

public class CheckTaskVo implements Serializable{
    private static final long serialVersionUID = -5414391138065561438L;
    private String id;

    private String name;

    private String taskno;

    private String servicemac;

    private String staffno;

    private String createtime;

    private String updatetime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTaskno() {
        return taskno;
    }

    public void setTaskno(String taskno) {
        this.taskno = taskno == null ? null : taskno.trim();
    }

    public String getServicemac() {
        return servicemac;
    }

    public void setServicemac(String servicemac) {
        this.servicemac = servicemac == null ? null : servicemac.trim();
    }

    public String getStaffno() {
        return staffno;
    }

    public void setStaffno(String staffno) {
        this.staffno = staffno == null ? null : staffno.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
