package com.giveu.service;

import com.giveu.entity.MonitorApi;
import com.giveu.notice.common.vo.ResultModel;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口监控通知服务类
 * Created by fox on 2018/10/23.
 */
public interface ApiService {

	/**
	 * 获取列表
	 * @param request
	 * @param resultModel
	 */
	void list(HttpServletRequest request, ResultModel resultModel);

	/**
	 * 新增接口
	 * @param request
	 * @param resultModel
	 * @return
	 */
	Integer add(HttpServletRequest request, ResultModel resultModel);

	/**
	 * 获取接口配置
	 * @param Id
	 * @return
	 */
	MonitorApi getById(Integer Id);

	/**
	 * 更新接口配置
	 * @param request
	 * @param resultModel
	 * @return
	 */
	Integer update(HttpServletRequest request, ResultModel resultModel);

	/**
	 * 删除接口配置
	 * @param Id
	 * @return
	 */
	Integer delete(Integer Id);

	/**
	 * 启用
	 * @param Id
	 * @return
	 */
	Integer enableById(Integer Id);

	/**
	 * 禁用
	 * @param Id
	 * @return
	 */
	Integer disableById(Integer Id);
}
