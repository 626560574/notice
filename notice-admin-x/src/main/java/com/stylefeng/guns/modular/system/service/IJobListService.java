package com.stylefeng.guns.modular.system.service;

import com.giveu.notice.common.vo.JobTriggerAppVo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 任务列表Service
 *
 * @author fengshuonan
 * @Date 2018-07-03 17:03:27
 */
public interface IJobListService {

	void list(HttpServletRequest request, List<Map<String, Object>> list) throws IOException;

	Integer addJob(JobTriggerAppVo jobTriggerAppVo) throws IOException;

	Integer delJobByCode(String jobCode, String appKey) throws IOException;

	Integer triggerByLogId(String jobLogListId) throws IOException;

}
