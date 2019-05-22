package com.giveu.dao;

import com.giveu.entity.ApiFullModel;
import com.giveu.model.ApiProStatModel;
import com.giveu.model.ApiServerAddressParam;
import com.giveu.entity.ApiSimpleModel;
import com.giveu.model.TopApiProFlow;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by fox on 2018/11/1.
 */
@Component
public interface MonitorApiMongoDAO {
	/**
	 * 根据服务器地址统计接口异常次数
	 *
	 * @param params
	 * @return
	 */
	List<ApiProStatModel> getApiStat(List<ApiServerAddressParam> params);

	/**
	 * 获取指定系统的接口异常信息列表
	 *
	 * @param params      指定系统的接口地址集合
	 * @param path        action接口名称
	 * @param pageIndex   分页索引0开始
	 * @param rows        条数
	 * @return
	 */
	List<ApiSimpleModel> getApiList(List<ApiServerAddressParam> params, String path, Integer pageIndex, Integer rows);

	/**
	 * 根据api日志ID获取API详情
	 * @param apiId APIid
	 * @return
	 */
	ApiFullModel getById(String apiId);

	/**
	 * 获取近30日 系统异常数量统计数据
	 * @param params
	 * @return KEY:"IP:PORT: VALUE:count
	 */
	Map<String, Integer> getHostExCount(List<ApiServerAddressParam> params);
}
