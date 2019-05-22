package com.giveu.service;

import com.giveu.entity.MonitorRec;
import com.giveu.notice.common.vo.ResultModel;

import javax.servlet.http.HttpServletRequest;

/**
 * 预警对象服务类
 * Created by fox on 2018/10/23.
 */
public interface RecService {
	/**
	 * 获取列表
	 * @param request
	 * @param resultModel
	 */
	void list(HttpServletRequest request, ResultModel resultModel);

	/**
	 * 查询所有预警对象
	 *
	 * @param request
	 * @param resultModel
	 */
	void Search(HttpServletRequest request, ResultModel resultModel);

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
	MonitorRec getById(Integer id);

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
	Integer delete(Integer id);

	/**
	 * 启用
	 * @param Id
	 * @return
	 */
	Integer enableById(Integer id);

	/**
	 * 禁用
	 * @param Id
	 * @return
	 */
	Integer disableById(Integer id);

	/**
	 * 同步微信用户信息
	 * @return
	 */
	Integer syncwechat();
}
