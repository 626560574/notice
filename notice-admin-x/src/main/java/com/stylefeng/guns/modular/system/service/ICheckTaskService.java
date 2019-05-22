package com.stylefeng.guns.modular.system.service;

import com.giveu.notice.common.dto.JobMonitorDTO;
import com.giveu.notice.common.vo.CheckTaskVo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: ScheduleCenter
 * @description: 任务预警
 * @author: qin
 * @create: 2018-08-18 12:03
 **/

public interface ICheckTaskService {

    String getListJson();

    /**
     * @Description: 获取列表
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    void list(HttpServletRequest request, List<Map<String, Object>> list) throws IOException;

    /**
     * @Description: 增加任务
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    Integer add(JobMonitorDTO jobMonitorDTO) throws IOException;

    /**
     * @Description: 获取一个任务
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */

    CheckTaskVo getById(String Id,String name,String servicemac) throws IOException;

    /**
     * @Description: 更新任务
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    Integer update(CheckTaskVo checkTaskVo) throws IOException;

    /**
     * @Description: 删除任务
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    Integer delete(Integer Id);

    /**
     * @Description: 启用任务
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/19
     */
    Integer enableById(Integer id);

    /**
     * @Description: 禁用任务
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/19
     */
    Integer disableById(Integer id);
}
