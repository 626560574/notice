package com.giveu.service;

import com.giveu.entity.ApiFullModel;
import com.giveu.entity.MonitorApi;
import com.giveu.model.ApiProStatModel;
import com.giveu.entity.ApiSimpleModel;

import java.util.List;

/**
 * Created by fox on 2018/10/30.
 */
public interface MobileService {

	String statJob(String code);

	String statObj(String code);

	String detailObj(String objId);

	String topJob(String code);

	String topObj(String code);

	String topApi(String code);

	/**
	 * 获取用户相关联的监控项目
	 *
	 * @param code 微信code用来获取userid
	 */
	List<MonitorApi> getMonitorProjectsByUser(String code);

	/**
	 * 获取具体某个项目的监控数据
	 *
	 * @param proId 项目ID
	 * @param code  微信code用来验证登陆安全的，暂不启用可传空
	 * @return
	 */
	List<ApiProStatModel> getApiStatDetail(Integer proId, String code);

	/**
	 * 获取指定接口的异常信息列表 每次取20条
	 *
	 * @param proId           项目ID
	 * @param apiPath         接口地址
	 * @param pageIndex 	  分页索引0开始
	 * @return
	 */
	List<ApiSimpleModel> getApiListByProAndApi(Integer proId, String apiPath, Integer pageIndex);

	/**
	 * 根据ID获取API日志详情
	 * @param apiId
	 * @return
	 */
	ApiFullModel getById(String apiId);
}
