package com.giveu.service.impl;

import com.giveu.dao.MonitorApiDAO;
import com.giveu.dao.MonitorApiMongoDAO;
import com.giveu.entity.MonitorApi;
import com.giveu.model.ApiProStatModel;
import com.giveu.model.ApiServerAddressParam;
import com.giveu.model.ApiStatModel;
import com.giveu.notice.common.info.CommonMessage;
import com.giveu.notice.common.vo.ResultModel;
import com.giveu.service.ApiService;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 接口监控通知服务实现
 * Created by fox on 2018/10/23.
 */
@Service
public class ApiServiceImpl implements ApiService {

	private static final Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);

	@Autowired
	MonitorApiDAO monitorApiDAO;

	@Override
	public void list(HttpServletRequest request, ResultModel resultModel) {
		String proName = request.getParameter("proName");
		Integer monitorStatus = NumberUtils.toInt(request.getParameter("monitorStatus"), 0);

		List<MonitorApi> list = monitorApiDAO.getAll(proName, monitorStatus);
		resultModel.setCode(CommonMessage.OK_CODE);
		resultModel.setMessage(CommonMessage.OK_DESC);
		resultModel.setData(list);

	}

	@Override
	public Integer add(HttpServletRequest request, ResultModel resultModel) {
		String proName = request.getParameter("proName");
		String proAddress = request.getParameter("proAddress");
		String recObj = request.getParameter("recObj");
		Integer monitorStatus = NumberUtils.toInt(request.getParameter("monitorStatus"), 1);
		MonitorApi monitorApi = new MonitorApi();
		monitorApi.setProName(proName);
		monitorApi.setProAddress(proAddress);
		monitorApi.setRecObj(recObj);
		monitorApi.setMonitorStatus(monitorStatus);
		int affect = monitorApiDAO.add(monitorApi);
		isSuccess(affect, resultModel);
		return affect;
	}

	@Override
	public MonitorApi getById(Integer id) {
		return monitorApiDAO.getById(id);
	}

	@Override
	public Integer update(HttpServletRequest request, ResultModel resultModel) {
		Integer id = NumberUtils.toInt(request.getParameter("id"), 0);
		String proName = request.getParameter("proName");
		String proAddress = request.getParameter("proAddress");
		String recObj = request.getParameter("recObj");
		Integer monitorStatus = NumberUtils.toInt(request.getParameter("monitorStatus"), 1);
		if (id == 0) {
			isSuccess(id, resultModel);
			return id;
		}

		MonitorApi monitorApi = new MonitorApi();
		monitorApi.setId(id);
		monitorApi.setProName(proName);
		monitorApi.setProAddress(proAddress);
		monitorApi.setRecObj(recObj);
		monitorApi.setMonitorStatus(monitorStatus);
		int affect = monitorApiDAO.upd(monitorApi);
		isSuccess(affect, resultModel);
		return affect;
	}

	@Override
	public Integer delete(Integer id) {
		return monitorApiDAO.del(id);
	}

	@Override
	public Integer enableById(Integer id) {
		return monitorApiDAO.enableById(id);
	}

	@Override
	public Integer disableById(Integer id) {
		return monitorApiDAO.disableById(id);
	}

	void isSuccess(int affectRows, ResultModel resultModel) {
		if (affectRows == 1) {
			resultModel.setCode(CommonMessage.OK_CODE);
			resultModel.setMessage(CommonMessage.OK_DESC);
		} else {
			resultModel.setCode(CommonMessage.SER_UNKNOW_ERROR_CODE);
			resultModel.setMessage(CommonMessage.SER_UNKNOW_ERROR_DESC);
		}
	}
}
