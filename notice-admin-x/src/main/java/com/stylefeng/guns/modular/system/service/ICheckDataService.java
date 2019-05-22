package com.stylefeng.guns.modular.system.service;

import com.giveu.notice.common.dto.ObjMonitorDTO;
import com.giveu.notice.common.vo.CheckDataVo;
import com.giveu.notice.common.vo.CheckTaskVo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: ScheduleCenter
 * @description: 数据预警
 * @author: qin
 * @create: 2018-08-18 12:03
 **/

public interface ICheckDataService {

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
     * @Description: 增加
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    Integer add(ObjMonitorDTO objMonitorDTO) throws IOException;

    /**
     * @Description: 获取
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */

    CheckDataVo getById(Integer Id) throws IOException;

    /**
     * @Description: 更新
     * @Param:
     * @return:
     * @Author: qin
     * @Date: 2018/8/18
     */
    Integer update(CheckDataVo checkDataVo) throws IOException;

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
}
