package com.giveu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.giveu.common.CommonToken;
import com.giveu.common.CommonUrl;
import com.giveu.dao.*;
import com.giveu.entity.*;
import com.giveu.model.ApiProStatModel;
import com.giveu.model.ApiServerAddressParam;
import com.giveu.model.*;
import com.giveu.notice.common.vo.ResultModel;
import com.giveu.service.MobileService;
import jodd.http.HttpMultiMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fox on 2018/10/30.
 */
@Service
public class MobileServiceImpl implements MobileService {
	private static final Logger logger = LoggerFactory.getLogger(MobileServiceImpl.class);

	@Autowired
	MonitorJobDAO monitorJobDAO;

	@Autowired
	MonitorObjDAO monitorObjDAO;

	@Autowired
	MonitorRecDAO monitorRecDAO;

	@Autowired
	MonitorApiDAO monitorApiDAO;

	@Autowired
	MonitorApiMongoDAO monitorApiMongoDAO;

	Map<String, String> codeUserIdMap = new ConcurrentHashMap<>();

	// CODE个数
	private static int CODE_SIZE = 100;

	@Override
	public String statJob(String code) {

		String userId = getUserId(code);
//		String userId = "fox";

		String jobIdListJson = getJobIdListJson(userId);

		HttpRequest httpRequest = HttpRequest.get(CommonUrl.STAT_JOB_URL);
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("jobIdListJson", jobIdListJson);
		HttpResponse response = httpRequest.send();

		ResultModel resultModel = JSONObject.parseObject(response.bodyText(), ResultModel.class);

		return String.valueOf(resultModel.getData());
	}

	@Override
	public String statObj(String code) {

		String userId = getUserId(code);
//		String userId = "fox";

		String objIdListJson = getObjIdListJson(userId);

		HttpRequest httpRequest = HttpRequest.get(CommonUrl.STAT_OBJ_URL);
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("objIdListJson", objIdListJson);
		HttpResponse response = httpRequest.send();

		ResultModel resultModel = JSONObject.parseObject(response.bodyText(), ResultModel.class);

		return String.valueOf(resultModel.getData());
	}

	@Override
	public String detailObj(String objId) {
		HttpRequest httpRequest = HttpRequest.get(CommonUrl.STAT_OBJ_URL);
		HttpMultiMap multiMap = httpRequest.query();
		List<String> objidList = new ArrayList<>();
		objidList.add(objId);
		String objIdListJson = JSONObject.toJSONString(objidList);
		multiMap.add("objIdListJson", objIdListJson);
		HttpResponse response = httpRequest.send();
		ResultModel resultModel = JSONObject.parseObject(response.bodyText(), ResultModel.class);
		return String.valueOf(resultModel.getData());
	}

	@Override
	public String topJob(String code) {
		String userId = getUserId(code);
//		String userId = "fox";
		String jobIdListJson = getJobIdListJson(userId);
		HttpRequest httpRequest = HttpRequest.get(CommonUrl.TOP_JOB_URL);
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("jobIdListJson", jobIdListJson);
		HttpResponse response = httpRequest.send();
		ResultModel resultModel = JSONObject.parseObject(response.bodyText(), ResultModel.class);
		return String.valueOf(resultModel.getData());
	}

	@Override
	public String topObj(String code) {
		String userId = getUserId(code);
//		String userId = "fox";
		String objIdListJson = getObjIdListJson(userId);
		HttpRequest httpRequest = HttpRequest.get(CommonUrl.TOP_OBJ_URL);
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("objIdListJson", objIdListJson);
		HttpResponse response = httpRequest.send();
		ResultModel resultModel = JSONObject.parseObject(response.bodyText(), ResultModel.class);
		return String.valueOf(resultModel.getData());
	}

	@Override
	public String topApi(String code) {
		List<MonitorApi> monitorApis = getMonitorProjectsByUser(code);
		List<ApiServerAddressParam> params = getApiServerAddressParams(monitorApis);
		Map<String, Integer> mapHostStat = monitorApiMongoDAO.getHostExCount(params);

		TopModel topModel = new TopModel();

		TopApiProFlow topApiProFlow = new TopApiProFlow();
		TopApiProException topApiProException = new TopApiProException();
		TopApiServerFlow topApiServerFlow = new TopApiServerFlow();
		TopApiServerException topApiServerException = new TopApiServerException();

		//开始统计
		Map<Integer, List<String>> proHosts = new HashMap<>();
		Map<Integer, String> mapProName = new HashMap<>();
		for (MonitorApi monitorApi : monitorApis) {
			if (!mapProName.containsKey(monitorApi.getId())) {
				mapProName.put(monitorApi.getId(), monitorApi.getProName());
			}
			List<String> hosts = JSONObject.parseArray(monitorApi.getProAddress(), String.class);
			if (hosts == null || hosts.size() == 0) {
				continue;
			}
			proHosts.put(monitorApi.getId(), hosts);
		}

		Map<Integer, Integer> mapProStat = new HashMap<>();
		Map<String, Integer> mapHostExStat = new HashMap<>();
		for (Map.Entry<String, Integer> entry : mapHostStat.entrySet()) {
			//按服务器统计
			if (entry.getKey().indexOf(":") > 0) {
				String host = entry.getKey().split(":")[0];
				if (!mapHostExStat.containsKey(host)) {
					mapHostExStat.put(host, 0);
				}
				mapHostExStat.put(host, mapHostExStat.get(host) + entry.getValue());
			}
			//按项目统计
			Integer currentProId = 0;
			for (Map.Entry<Integer, List<String>> hentry : proHosts.entrySet()) {
				if (hentry.getValue().contains(entry.getKey())) {
					currentProId = hentry.getKey();
					break;
				}
			}
			if (!mapProStat.containsKey(currentProId)) {
				mapProStat.put(currentProId, 0);
			}
			mapProStat.put(currentProId, mapProStat.get(currentProId) + entry.getValue());
		}

		//按系统开始统计异常
		Integer maxCountProId = 0;
		Integer minCountProId = 0;
		Integer proSumCount = 0;
		topApiProException.setProExcMaxCount(0);
		topApiProException.setProExcMinCount(0);

		for (Map.Entry<Integer, Integer> entry : mapProStat.entrySet()) {
			proSumCount += entry.getValue();

			if (entry.getValue() > topApiProException.getProExcMaxCount()) {
				maxCountProId = entry.getKey();
				topApiProException.setProExcMaxCount(entry.getValue());
			}
			if (topApiProException.getProExcMinCount().equals(0)) {
				minCountProId = entry.getKey();
				topApiProException.setProExcMinCount(entry.getValue());
			}
			if (entry.getValue() < topApiProException.getProExcMinCount()) {
				minCountProId = entry.getKey();
				topApiProException.setProExcMinCount(entry.getValue());
			}
		}
		topApiProException.setProCout(monitorApis.size());
		topApiProException.setDays(30);
		topApiProException.setExcCount(proSumCount);
		topApiProException.setProExcMaxName(mapProName.get(maxCountProId));
		topApiProException.setProExcMinName(mapProName.get(minCountProId));
		//--------------------------------------------------
		//按服务器开始统计异常
		Integer hostSumCount = 0;

		topApiServerException.setInvExcMaxSerCount(0);
		topApiServerException.setInvExcMinSerCount(0);
		for (Map.Entry<String, Integer> entry : mapHostExStat.entrySet()) {
			hostSumCount += entry.getValue();

			//统计系统异常
			if (entry.getValue() > topApiServerException.getInvExcMaxSerCount()) {
				topApiServerException.setInvExcMaxSerName(entry.getKey());
				topApiServerException.setInvExcMaxSerCount(entry.getValue());
			}
			if (topApiServerException.getInvExcMinSerCount().equals(0)) {
				topApiServerException.setInvExcMinSerName(entry.getKey());
				topApiServerException.setInvExcMinSerCount(entry.getValue());
			}
			if (entry.getValue() < topApiServerException.getInvExcMinSerCount()) {
				topApiServerException.setInvExcMinSerName(entry.getKey());
				topApiServerException.setInvExcMinSerCount(entry.getValue());
			}
		}
		topApiServerException.setSerCout(mapHostExStat.keySet().size());
		topApiServerException.setInvExcCount(hostSumCount);
		//--------------------------------------------------


		topModel.setTopApiProFlow(topApiProFlow);
		topModel.setTopApiProException(topApiProException);
		topModel.setTopApiServerFlow(topApiServerFlow);
		topModel.setTopApiServerException(topApiServerException);

		String topModelJson = JSONObject.toJSONString(topModel);

		return topModelJson;
	}

	@Override
	public List<MonitorApi> getMonitorProjectsByUser(String code) {
		List<MonitorApi> list = new ArrayList<>();
		if (code == null || code.isEmpty())
			return list;
		String userId = getUserId(code);
		//String userId = code;
		List<MonitorRec> monitorRecList = monitorRecDAO.getListExcludeDisable();
		MonitorRec monitorRec = null;
		for (MonitorRec rec : monitorRecList) {
			if (rec.getRecWxId() != null && rec.getRecWxId().equals(userId)) {
				monitorRec = rec;
				break;
			}
		}
		if (monitorRec == null)
			return list;

		List<MonitorApi> monitorApis = monitorApiDAO.getAll(null, 1);
		for (MonitorApi api : monitorApis) {
			List<MonitorRec> recList = JSONObject.parseArray(api.getRecObj(), MonitorRec.class);
			if (recList == null || recList.size() == 0) {
				continue;
			}
			for (MonitorRec rec : recList) {
				if (rec.getId().equals(monitorRec.getId())) {
					list.add(api);
					break;
				}
			}
		}
		return list;
	}

	@Override
	public List<ApiProStatModel> getApiStatDetail(Integer proId, String code) {
		List<ApiProStatModel> list = new ArrayList<>();
//		if (code == null || code.isEmpty())
//			return list;
//		String userId = getUserId(code);
//		List<MonitorRec> monitorRecList = monitorRecDAO.getListExcludeDisable();
//		MonitorRec monitorRec = monitorRecDAO.getByWxId(userId);
//		if (monitorRec == null || monitorRec.getRecWxId() == null || monitorRec.getRecStatus() == 2) {
//			return list;
//		}
		List<ApiServerAddressParam> params = getApiServerAddressParams(proId);
		if (params.size() == 0) {
			return list;
		}

		list = monitorApiMongoDAO.getApiStat(params);
		return list;
	}

	/**
	 * 获取指定接口的异常信息列表 每次取20条
	 *
	 * @param proId     项目ID
	 * @param apiPath   接口地址
	 * @param pageIndex 分页索引0开始
	 * @return
	 */
	@Override
	public List<ApiSimpleModel> getApiListByProAndApi(Integer proId, String apiPath, Integer pageIndex) {
		List<ApiSimpleModel> list = new ArrayList<>();
		List<ApiServerAddressParam> params = getApiServerAddressParams(proId);
		if (params.size() == 0) {
			return list;
		}
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		list = monitorApiMongoDAO.getApiList(params, apiPath, pageIndex, 20);
		return list;
	}

	public String getUserId(String code) {
		String userId = codeUserIdMap.get(code);
		if (userId != null) {
			return userId;
		}
		HttpRequest httpRequest = HttpRequest.get(CommonUrl.WX_OAUTH_USER_INFO_URL);
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("access_token", CommonToken.token);
		multiMap.add("code", code);
		HttpResponse response = httpRequest.send();
		JSONObject jsonObject = JSONObject.parseObject(response.bodyText());
		userId = String.valueOf(jsonObject.get("UserId"));

		if (codeUserIdMap.size() >= CODE_SIZE) {
			codeUserIdMap.clear();
		}
		codeUserIdMap.put(code, userId);
		return userId;
	}

	@Override
	public ApiFullModel getById(String apiId) {
		return monitorApiMongoDAO.getById(apiId);
	}

	/**
	 * 获得当前用户下有权限的任务ID
	 *
	 * @param userId
	 * @return
	 */
	private String getJobIdListJson(String userId) {

		List<MonitorJob> monitorJobList = monitorJobDAO.getListExcludeDisable();
		List<MonitorRec> monitorRecList = monitorRecDAO.getListExcludeDisable();

		Map<Integer, String> map = new HashMap<>();

		for (MonitorRec rec : monitorRecList) {
			map.put(rec.getId(), rec.getRecWxId());
		}

		List<String> jobIdList = new ArrayList<>();

		for (MonitorJob m : monitorJobList) {
			List<MonitorRec> recList = JSONObject.parseArray(m.getRecObj(), MonitorRec.class);
			if (recList == null || recList.size() == 0) {
				continue;
			}
			for (MonitorRec rec : recList) {
				String recWxId = map.get(rec.getId());
				if (recWxId == null) {
					continue;
				}
				if (recWxId.equals(userId)) {
					jobIdList.add(m.getJobId());
					break;
				}

			}
		}

		if (jobIdList.size() == 0) {
			return JSONObject.toJSONString(jobIdList);
		}

		return JSONObject.toJSONString(jobIdList);
	}

	/**
	 * 获得当前用户下有权限的数据对象ID
	 *
	 * @param userId
	 * @return
	 */
	private String getObjIdListJson(String userId) {

		List<MonitorRec> monitorRecList = monitorRecDAO.getListExcludeDisable();
		List<MonitorObj> monitorObjList = monitorObjDAO.getListExcludeDisable();

		Map<Integer, String> map = new HashMap<>();

		for (MonitorRec rec : monitorRecList) {
			map.put(rec.getId(), rec.getRecWxId());
		}

		List<String> objIdList = new ArrayList<>();

		for (MonitorObj m : monitorObjList) {
			List<MonitorRec> recList = JSONObject.parseArray(m.getRecObj(), MonitorRec.class);
			if (recList == null || recList.size() == 0) {
				continue;
			}
			for (MonitorRec rec : recList) {
				String recWxId = map.get(rec.getId());
				if (recWxId == null) {
					continue;
				}
				if (recWxId.equals(userId)) {
					objIdList.add(m.getObjId());
					break;
				}

			}
		}

		if (objIdList.size() == 0) {
			return JSONObject.toJSONString(objIdList);
		}

		return JSONObject.toJSONString(objIdList);
	}

	/**
	 * 获取接口项目中的IP及端口集合做查询条件
	 *
	 * @param proId
	 * @return
	 */
	private List<ApiServerAddressParam> getApiServerAddressParams(Integer proId) {
		List<ApiServerAddressParam> params = new ArrayList<>();
		MonitorApi monitorApi = monitorApiDAO.getById(proId);
		if (monitorApi == null || monitorApi.getMonitorStatus() == 2) {
			return params;
		}
		List<MonitorApi> list = new ArrayList<>();
		list.add(monitorApi);
		params = getApiServerAddressParams(list);
		return params;
	}

	private List<ApiServerAddressParam> getApiServerAddressParams(List<MonitorApi> monitorApis) {
		List<ApiServerAddressParam> params = new ArrayList<>();
		for (MonitorApi monitorApi : monitorApis) {
			List<String> hosts = JSONObject.parseArray(monitorApi.getProAddress(), String.class);
			if (hosts == null || hosts.size() == 0) {
				return params;
			}
			for (String h : hosts) {
				if (h.indexOf(":") < 0) {
					continue;
				}
				String[] tempHost = h.split(":");
				if (tempHost[0].equals("")) {
					continue;
				}
				try {
					ApiServerAddressParam param = new ApiServerAddressParam();
					param.setAddress(tempHost[0]);
					param.setPort(Integer.parseInt(tempHost[1]));
					params.add(param);
				} catch (NumberFormatException ex) {
					logger.error(ex.getStackTrace().toString());
				}
			}
		}
		return params;
	}
}
