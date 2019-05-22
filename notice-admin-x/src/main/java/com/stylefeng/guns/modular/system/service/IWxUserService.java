package com.stylefeng.guns.modular.system.service;

import com.giveu.notice.common.vo.MonitorRecVo;
import com.giveu.notice.common.vo.ResultModel;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: ScheduleCenter
 * @description: 企业微信用户
 * @author: qin
 * @create: 2018-08-19 09:02
 **/

public interface IWxUserService {
    /**
     * @Description: 获取列表
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    void list(HttpServletRequest request, List<Map<String, Object>> list) throws IOException;
    /**
     * @Description: 获取编辑时用户列表
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    void editList(HttpServletRequest request, List<Map<String, Object>> list) throws IOException;
    String getListJson();

    /**
     * @Description: 增加
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    ResultModel add(MonitorRecVo monitorRecVo) throws IOException;

    /**
     * @Description: 获取一个
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    MonitorRecVo getById(Integer Id) throws IOException;

    /**
     * @Description: 更新
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    ResultModel update(MonitorRecVo monitorRecVo) throws IOException;

    /**
     * @Description: 删除
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    Integer delete(Integer Id);

    /**
     * @Description: 启用
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/19
     */
    Integer enableById(Integer id);

    /**
     * @Description: 禁用
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/19
     */
    Integer disableById(Integer id);

    /**
     * @Description: 同步微信人员信息
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/19
     */
    Integer syncwechat();
}
