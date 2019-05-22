package com.stylefeng.guns.modular.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giveu.notice.common.vo.MonitorRecVo;
import com.giveu.notice.common.vo.ResultModel;
import com.stylefeng.guns.modular.system.service.IWxUserService;
import jodd.http.HttpMultiMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: ScheduleCenter
 * @description: 企业微信用户
 * @author: qin
 * @create: 2018-08-19 09:04
 **/
@Service
public class WxUserServiceImpl implements IWxUserService {
	public static String staffnoList = null;
	private static final int OK = 200;

	//    private static final String dataUrl = "http://noticecenter.jyfq.com/";
//	private static final String dataUrl = "http://localhost:9000/";
	private static final String dataUrl = "http://localhost/";

	/**
	 * @Description: 获取编辑列表
	 * @Param:
	 * @return:
	 * @Author: qin
	 * @Date: 2018/8/18
	 */
	@Override
	public void editList(HttpServletRequest request, List<Map<String, Object>> list) throws IOException {

		setUserList(list, "");

	}

	/**
	 * @Description: 获取用户列表
	 * @Param:
	 * @return:
	 * @Author: qin
	 * @Date: 2018/8/18
	 */
	@Override
	public void list(HttpServletRequest request, List<Map<String, Object>> list) throws IOException {
		String searchKey = request.getParameter("searchKey");
		if (searchKey == null)
			searchKey = "";
		searchKey = searchKey.trim();
		setUserList(list, searchKey);
	}


	@Override
	public String getListJson() {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "wxUser/list");
		HttpResponse response = httpRequest.send();
		String result = response.bodyText();
		return result;
	}

	void setUserList(List<Map<String, Object>> list, String searchKey) {
		String actionUrl = "wxUser/list";
		if (searchKey != null && searchKey != "")
			actionUrl = "wxUser/Search";
		HttpRequest httpRequest = HttpRequest.post(dataUrl + actionUrl);
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("recNo", searchKey);
		multiMap.add("recName", searchKey);
		multiMap.add("recPhone", searchKey);
		HttpResponse response = httpRequest.send();

		String result = response.bodyText();
		ResultModel resultModel = JSONObject.parseObject(result, ResultModel.class);
		List<MonitorRecVo> monitorRecVoList = JSONObject.parseArray(String.valueOf(resultModel.getData()), MonitorRecVo.class);

		for (MonitorRecVo m : monitorRecVoList) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", m.getId());
			map.put("recNo", m.getRecNo());
			map.put("recName", m.getRecName());
			map.put("recWxId", m.getRecWxId());
			map.put("recWxNick", m.getRecWxNick());
			map.put("recPhone", m.getRecPhone());
			map.put("recEmail", m.getRecEmail());
			map.put("recDeptNo", m.getRecDeptNo());
			map.put("recStatusDesc", m.getRecStatusDesc());
			map.put("createTimeDesc", m.getCreateTimeDesc());
			map.put("updateTimeDesc", m.getUpdateTimeDesc());
			list.add(map);
		}
	}

	@Override
	public ResultModel add(MonitorRecVo monitorRecVo) throws IOException {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "wxUser/add");
		HttpMultiMap multiMap = httpRequest.query();
		String recNo = monitorRecVo.getRecNo();
		String recName = monitorRecVo.getRecName();
		String recPhone = monitorRecVo.getRecPhone();
		String recEmail = monitorRecVo.getRecEmail();
		Integer recStatus = monitorRecVo.getRecStatus();

		multiMap.add("recNo", recNo);
		multiMap.add("recName", recName);
		multiMap.add("recEmail", recEmail);
		multiMap.add("recPhone", recPhone);
		multiMap.add("recStatus", recStatus);
		HttpResponse response = httpRequest.send();
		ObjectMapper mapper = new ObjectMapper();
		String result = response.bodyText();
		ResultModel resultModel = mapper.readValue(result, ResultModel.class);
		return resultModel;
	}

	@Override
	public MonitorRecVo getById(Integer id) throws IOException {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "wxUser/getById");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("id", id);
		HttpResponse response = httpRequest.send();
		ObjectMapper mapper = new ObjectMapper();
		String result = response.bodyText();
		MonitorRecVo monitorRecVo = mapper.readValue(result, MonitorRecVo.class);
		return monitorRecVo;
	}

	@Override
	public ResultModel update(MonitorRecVo monitorRecVo) throws IOException {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "wxUser/edit");
		HttpMultiMap multiMap = httpRequest.query();
		Integer id = Integer.valueOf(monitorRecVo.getId());
		String recNo = monitorRecVo.getRecNo();
		String recName = monitorRecVo.getRecName();
		String recPhone = monitorRecVo.getRecPhone();
		String recEmail = monitorRecVo.getRecEmail();
		Integer recStatus = monitorRecVo.getRecStatus();

		multiMap.add("id", id);
		multiMap.add("recNo", recNo);
		multiMap.add("recName", recName);
		multiMap.add("recEmail", recEmail);
		multiMap.add("recPhone", recPhone);
		multiMap.add("recStatus", recStatus);
		HttpResponse response = httpRequest.send();
		ObjectMapper mapper = new ObjectMapper();
		String result = response.bodyText();
		ResultModel resultModel = mapper.readValue(result, ResultModel.class);
		return resultModel;
	}

	@Override
	public Integer delete(Integer id) {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "wxUser/del");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("id", id);
		HttpResponse response = httpRequest.send();
		return null;
	}

	@Override
	public Integer enableById(Integer id) {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "wxUser/enable/id");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("id", id);
		HttpResponse response = httpRequest.send();
		return null;
	}

	@Override
	public Integer disableById(Integer id) {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "wxUser/disable/id");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("id", id);
		HttpResponse response = httpRequest.send();
		return null;
	}

	@Override
	public Integer syncwechat() {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "wxUser/syncwechat");
		HttpResponse response = httpRequest.send();
		String result = response.bodyText();
		Integer affect = NumberUtils.toInt(result,0);
		return affect;
	}
}
