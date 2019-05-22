package com.stylefeng.guns.modular.system.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giveu.notice.common.vo.AppVo;
import com.giveu.notice.common.vo.ResultModel;
import com.stylefeng.guns.modular.system.service.IAppListService;
import jodd.http.HttpMultiMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用列表Dao
 *
 * @author fengshuonan
 * @Date 2018-07-03 17:28:13
 */
@Service
public class AppListServiceImpl implements IAppListService {

	private static final int OK = 200;

	private static final String dataUrl = "http://localhost:8765/";


	@Override
	public void list(HttpServletRequest request, List<Map<String, Object>> list) throws IOException {

		String appName = request.getParameter("appName");
		String appKey = request.getParameter("appKey");
		String appStatus = request.getParameter("appStatus");

		HttpRequest httpRequest = HttpRequest.post(dataUrl + "job/app/list");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("appName", appName);
		multiMap.add("appStatus", appStatus);
		multiMap.add("appKey", appKey);
		HttpResponse response = httpRequest.send();

		ObjectMapper mapper = new ObjectMapper();
		String result = response.bodyText();
		ResultModel resultModel = mapper.readValue(result, ResultModel.class);
		List<AppVo> appVoList = null;
		if (resultModel.getCode() == OK) {
			appVoList = (ArrayList<AppVo>)resultModel.getData();

		}

		for (int i = 0; i < appVoList.size(); i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", ((Map)appVoList.get(i)).get("id"));
			map.put("appName", ((Map)appVoList.get(i)).get("appName"));
			map.put("appDesc", ((Map)appVoList.get(i)).get("appDesc"));
			map.put("appStatus", ((Map)appVoList.get(i)).get("appStatus"));
			map.put("appStatusDesc", ((Map)appVoList.get(i)).get("appStatusDesc"));
			map.put("appCreateTime", ((Map)appVoList.get(i)).get("appCreateTime"));
			map.put("appUpdateTime", ((Map)appVoList.get(i)).get("appUpdateTime"));
			map.put("appAuthor", ((Map)appVoList.get(i)).get("appAuthor"));
			map.put("appEmail", ((Map)appVoList.get(i)).get("appEmail"));
			map.put("appPhone", ((Map)appVoList.get(i)).get("appPhone"));
			map.put("appKey", ((Map)appVoList.get(i)).get("appKey"));
			map.put("appSecret", ((Map)appVoList.get(i)).get("appSecret"));
			list.add(map);
		}
	}

	@Override
	public String getListJson() {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "job/app/list");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("appName", "");
		multiMap.add("appKey", "");
		multiMap.add("appStatus", 0);
		HttpResponse response = httpRequest.send();
		String result = response.bodyText();
		return result;
	}

	@Override
	public Integer addApp(AppVo appVo) throws IOException {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "job/app/add");
		HttpMultiMap multiMap = httpRequest.query();
		String appName = appVo.getAppName();
		Integer appStatus = appVo.getAppStatus();
		String appKey = appVo.getAppKey();
		String appSecret = appVo.getAppSecret();
		multiMap.add("appName", appName);
		multiMap.add("appStatus", appStatus);
		multiMap.add("appKey", appKey);
		multiMap.add("appSecret", appSecret);

		HttpResponse response = httpRequest.send();
		ObjectMapper mapper = new ObjectMapper();
		String result = response.bodyText();
		ResultModel resultModel = mapper.readValue(result, ResultModel.class);
		return resultModel.getCode();
	}

	@Override
	public Integer updAppById(AppVo appVo) throws IOException {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "job/app/upd/id");
		HttpMultiMap multiMap = httpRequest.query();
		String appName = appVo.getAppName();
		Integer appStatus = appVo.getAppStatus();
		String appKey = appVo.getAppKey();
		String appSecret = appVo.getAppSecret();
		String id = appVo.getId();
		multiMap.add("id", id);
		multiMap.add("appName", appName);
		multiMap.add("appStatus", appStatus);
		multiMap.add("appKey", appKey);
		multiMap.add("appSecret", appSecret);

		HttpResponse response = httpRequest.send();
		ObjectMapper mapper = new ObjectMapper();
		String result = response.bodyText();
		ResultModel resultModel = mapper.readValue(result, ResultModel.class);
		return resultModel.getCode();
	}

	@Override
	public AppVo getAppVoById(String appId) throws IOException {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "job/app/get/id");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("appId", appId);
		HttpResponse response = httpRequest.send();
		ObjectMapper mapper = new ObjectMapper();
		String result = response.bodyText();
		AppVo appVo = mapper.readValue(result, AppVo.class);

		return appVo;
	}

	@Override
	public Integer delAppById(String id) {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "job/app/del/id");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("appId", id);
		HttpResponse response = httpRequest.send();
		return null;
	}

	@Override
	public Integer enableById(String id) {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "job/app/enable/id");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("appId", id);
		HttpResponse response = httpRequest.send();
		return null;
	}

	@Override
	public Integer disableById(String id) {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "job/app/disable/id");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("appId", id);
		HttpResponse response = httpRequest.send();
		return null;
	}
}
