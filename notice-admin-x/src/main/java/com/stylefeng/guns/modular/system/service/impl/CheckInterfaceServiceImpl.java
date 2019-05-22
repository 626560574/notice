package com.stylefeng.guns.modular.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giveu.notice.common.vo.CheckInterfaceVo;
import com.giveu.notice.common.vo.MonitorApiVo;
import com.giveu.notice.common.vo.ResultModel;
import com.stylefeng.guns.modular.system.model.ReceiverEntity;
import com.stylefeng.guns.modular.system.service.ICheckInterfaceService;
import jodd.http.HttpMultiMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static com.stylefeng.guns.modular.system.service.impl.WxUserServiceImpl.staffnoList;

/**
 * @program: ScheduleCenter
 * @description: 接口预警实现类
 * @author: qin
 * @create: 2018-08-18 12:12
 **/
@Service
public class CheckInterfaceServiceImpl implements ICheckInterfaceService {
	private static final int OK = 200;

	//    private static final String dataUrl = "http://noticecenter.jyfq.com/";
//	private static final String dataUrl = "http://localhost:9000/";
	private static final String dataUrl = "http://localhost/";

	/**
	 * @Description: 获取列表
	 * @Param:
	 * @return:
	 * @Author: qin
	 * @Date: 2018/8/18
	 */
	@Override
	public void list(HttpServletRequest request, List<Map<String, Object>> list) throws IOException {
		String proName = request.getParameter("proName");
		String monitorStatus = request.getParameter("monitorStatus");

		HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkInterface/list");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("proName", proName);
		multiMap.add("monitorStatus", monitorStatus);
		HttpResponse response = httpRequest.send();

		ObjectMapper mapper = new ObjectMapper();
		String result = response.bodyText();
		ResultModel resultModel = JSONObject.parseObject(result, ResultModel.class);
		List<MonitorApiVo> monitorApiVoList = JSONObject.parseArray(String.valueOf(resultModel.getData()), MonitorApiVo.class);

		for (MonitorApiVo m : monitorApiVoList) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", m.getId());
			map.put("proName", m.getProName());
			map.put("proAddress", m.getProAddress());
			map.put("recObj", m.getRecObj());
			map.put("createTime", m.getCreateTime());
			map.put("updateTime", m.getUpdateTime());
			map.put("monitorStatusDesc", m.getMonitorStatusDesc());
			list.add(map);
		}
	}

	/**
	 * @Description: 增加
	 * @Param:
	 * @return:
	 * @Author: qin
	 * @Date: 2018/8/18
	 */
	@Override
	public ResultModel add(CheckInterfaceVo checkInterfaceVo) throws IOException {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkInterface/add");
		HttpMultiMap multiMap = httpRequest.query();
		String id = checkInterfaceVo.getId();
		String name = checkInterfaceVo.getName();
		String servicemac = checkInterfaceVo.getServicemac();
		String staffno = checkInterfaceVo.getStaffno();
		String createtime = checkInterfaceVo.getCreatetime();
		String updatetime = checkInterfaceVo.getUpdatetime();
		String status = checkInterfaceVo.getStatus();

		ResultModel resultModel = new ResultModel();
		//解析服务器地址
		servicemac = servicemac.trim()
				.replaceAll("：", ":");
		String[] ipAddress = servicemac.split("\n");
		//此处暂时不做IP及端口有效性的验证，因为该配置操作只有管理员可以操作
		if (ipAddress.length == 0) {
			resultModel.SetFaild("请配置服务器地址", null);
			return resultModel;
		}
		//移除数组中的空元素
		List<String> list = new ArrayList<>(Arrays.asList(ipAddress));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals("")) {
				list.remove(i);
				i--;
			}
		}
		servicemac = JSON.toJSONString(list);

		multiMap.add("id", id);
		multiMap.add("name", name);
		multiMap.add("servicemac", servicemac);
		multiMap.add("staffno", staffno);
		multiMap.add("createtime", createtime);
		multiMap.add("updatetime", updatetime);
		multiMap.add("status", status);
		HttpResponse response = httpRequest.send();
		ObjectMapper mapper = new ObjectMapper();
		String result = response.bodyText();
		resultModel = mapper.readValue(result, ResultModel.class);
		return resultModel;
	}

	@Override
	public ResultModel add(MonitorApiVo monitorApiVo) throws IOException {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkInterface/add");
		HttpMultiMap multiMap = httpRequest.query();
		String proName = monitorApiVo.getProName();
		String recObj = monitorApiVo.getRecObj();
		Integer monitorStatus = monitorApiVo.getMonitorStatus();
		String proAddress = monitorApiVo.getProAddress();

		ResultModel resultModel = new ResultModel();
		//解析服务器地址
		proAddress = proAddress.trim()
				.replaceAll("：", ":");
		String[] ipAddress = proAddress.split("\n");
		//此处暂时不做IP及端口有效性的验证，因为该配置操作只有管理员可以操作
		if (ipAddress.length == 0) {
			resultModel.SetFaild("请配置服务器地址", null);
			return resultModel;
		}
		//移除数组中的空元素
		List<String> list = new ArrayList<>(Arrays.asList(ipAddress));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals("")) {
				list.remove(i);
				i--;
			}
		}
		if (list != null && list.size() > 0) {
			//proAddress = list.toString();
			proAddress = JSON.toJSONString(list);
		}
		//解析预警对象
		if (recObj != null && !recObj.isEmpty()) {
			//解析预警对象
			List<ReceiverEntity> receivers = JSON.parseArray(recObj, ReceiverEntity.class);
			receivers.removeIf(r -> r.getId() == null || r.getId().isEmpty());

			recObj = JSON.toJSONString(receivers);
		}

		multiMap.add("proName", proName);
		multiMap.add("proAddress", proAddress);
		multiMap.add("recObj", recObj);
		multiMap.add("monitorStatus", monitorStatus);
		HttpResponse response = httpRequest.send();
		ObjectMapper mapper = new ObjectMapper();
		String result = response.bodyText();
		resultModel = mapper.readValue(result, ResultModel.class);
		return resultModel;
	}

	/**
	 * @Description: 获取一个
	 * @Param:
	 * @return:
	 * @Author: qin
	 * @Date: 2018/8/18
	 */
	@Override
	public MonitorApiVo getById(Integer id) throws IOException {

		HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkInterface/getById");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("id", id);
		HttpResponse response = httpRequest.send();
		ObjectMapper mapper = new ObjectMapper();
		String result = response.bodyText();
		MonitorApiVo monitorApiVo = mapper.readValue(result, MonitorApiVo.class);
		staffnoList = monitorApiVo.getRecObj();
		return monitorApiVo;
	}

	/**
	 * @Description: 更新
	 * @Param:
	 * @return:
	 * @Author: qin
	 * @Date: 2018/8/18
	 */
	@Override
	public ResultModel update(MonitorApiVo monitorApiVo) throws IOException {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkInterface/update");

		HttpMultiMap multiMap = httpRequest.query();
		Integer id = monitorApiVo.getId();
		String proName = monitorApiVo.getProName();
		String recObj = monitorApiVo.getRecObj();
		Integer monitorStatus = monitorApiVo.getMonitorStatus();
		String proAddress = monitorApiVo.getProAddress();

		ResultModel resultModel = new ResultModel();
		if (id <= 0) {
			resultModel.SetFaild("获取参数失败", null);
			return resultModel;
		}
		//解析服务器地址
		proAddress = proAddress.trim()
				.replaceAll("：", ":");
		String[] ipAddress = proAddress.split("\n");
		//此处暂时不做IP及端口有效性的验证，因为该配置操作只有管理员可以操作
		if (ipAddress.length == 0) {
			resultModel.SetFaild("请配置服务器地址", null);
			return resultModel;
		}
		//移除数组中的空元素
		List<String> list = new ArrayList<>(Arrays.asList(ipAddress));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals("")) {
				list.remove(i);
				i--;
			}
		}
		if (list != null && list.size() > 0) {
			//proAddress = list.toString();
			proAddress = JSON.toJSONString(list);
		}
		//解析预警对象
		if (recObj != null && !recObj.isEmpty()) {
			//解析预警对象
			List<ReceiverEntity> receivers = JSON.parseArray(recObj, ReceiverEntity.class);
			receivers.removeIf(r -> r.getId() == null || r.getId().isEmpty());

			recObj = JSON.toJSONString(receivers);
		}

		multiMap.add("id", id);
		multiMap.add("proName", proName);
		multiMap.add("proAddress", proAddress);
		multiMap.add("recObj", recObj);
		multiMap.add("monitorStatus", monitorStatus);

		HttpResponse response = httpRequest.send();
		ObjectMapper mapper = new ObjectMapper();
		String result = response.bodyText();
		resultModel = mapper.readValue(result, ResultModel.class);
		return resultModel;
	}

	/**
	 * @Description: 删除
	 * @Param:
	 * @return:
	 * @Author: qin
	 * @Date: 2018/8/18
	 */
	@Override
	public Integer delete(Integer id) {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkInterface/delete");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("id", id);
		HttpResponse response = httpRequest.send();
		return null;
	}

	@Override
	public String getListJson() {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkInterface/list");
		HttpMultiMap multiMap = httpRequest.query();
//        multiMap.add("appName", "");
//        multiMap.add("appKey", "");
//        multiMap.add("appStatus", 0);
		HttpResponse response = httpRequest.send();
		String result = response.bodyText();
		return result;
	}

	@Override
	public Integer enableById(Integer id) {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkInterface/enable/id");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("id", id);
		HttpResponse response = httpRequest.send();
		return null;
	}

	@Override
	public Integer disableById(Integer id) {
		HttpRequest httpRequest = HttpRequest.post(dataUrl + "checkInterface/disable/id");
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("id", id);
		HttpResponse response = httpRequest.send();
		return null;
	}
}
