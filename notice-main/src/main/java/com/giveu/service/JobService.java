package com.giveu.service;

import com.giveu.job.common.dto.QrtzTriggerLogDTO;
import com.giveu.job.common.vo.JobTriggerAppVo;
import com.giveu.notice.common.vo.ResultModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 任务监控通知服务类
 * Created by fox on 2018/10/23.
 */
public interface JobService {

	/**
	 * @Description: 获取列表
	 * @Param:
	 * @return:
	 * @Author: qin
	 * @Date: 2018/8/18
	 */
	void list(HttpServletRequest request, ResultModel resultModel);

	/**
	 * @Description: 增加任务
	 * @Param:
	 * @return:
	 * @Author: qin
	 * @Date: 2018/8/18
	 */
	Integer add(HttpServletRequest request, ResultModel resultModel);


	/**
	 * @Description: 更新任务
	 * @Param:
	 * @return:
	 * @Author: qin
	 * @Date: 2018/8/18
	 */
	Integer update(HttpServletRequest request, ResultModel resultModel);

	/**
	 * @Description: 删除任务
	 * @Param:
	 * @return:
	 * @Author: qin
	 * @Date: 2018/8/18
	 */
	Integer delete(Integer Id);

	/**
	 * @Description:  启用任务
	 * @Param:
	 * @return:
	 * @Author: qin
	 * @Date: 2018/8/19
	 */
	Integer enableById(Integer Id);

	/**
	 * @Description:  禁用任务
	 * @Param:
	 * @return:
	 * @Author: qin
	 * @Date: 2018/8/19
	 */
	Integer disableById(Integer Id);


	List<QrtzTriggerLogDTO> getLogListByJobId(String jobId);

	ResultModel triggerJobByLogId(String logId);
}
