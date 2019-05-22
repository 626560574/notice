package com.giveu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.giveu.dao.MonitorObjDAO;
import com.giveu.entity.MonitorObj;
import com.giveu.job.common.vo.MonitorObjectVo;
import com.giveu.notice.common.dto.ObjMonitorDTO;
import com.giveu.notice.common.info.CommonMessage;
import com.giveu.notice.common.vo.ResultModel;
import com.giveu.service.ObjService;
import jodd.http.HttpMultiMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fox on 2018/10/23.
 */
@Service
public class ObjServiceImpl implements ObjService {

//	private static final String OBJ_LIST_URL = "http://10.10.11.52:9040/monitor/list";
	private static final String OBJ_LIST_URL = "http://10.11.13.30:9040/monitor/list";

	@Autowired
	MonitorObjDAO monitorObjDAO;

	@Override
	public void list(HttpServletRequest request, ResultModel resultModel) {

		String objName = request.getParameter("objName");

		MonitorObj monitorObj = new MonitorObj();

		List<MonitorObj> monitorObjList = monitorObjDAO.getList(monitorObj);


		HttpRequest httpRequest = HttpRequest.post(OBJ_LIST_URL);
		HttpMultiMap multiMap = httpRequest.query();
		multiMap.add("objName", objName);
		HttpResponse response = httpRequest.send();
		String result = response.bodyText();
		ResultModel model = JSONObject.parseObject(result, ResultModel.class);
		List<MonitorObjectVo> monitorObjectVoList = JSONObject.parseArray(String.valueOf(model.getData()), MonitorObjectVo.class);

		List<ObjMonitorDTO> objMonitorDTOList = new ArrayList<>();

		for (MonitorObjectVo o : monitorObjectVoList) {
			ObjMonitorDTO objMonitorDTO = new ObjMonitorDTO();
			objMonitorDTO.setObjId(o.getId());
			objMonitorDTO.setObjCode(o.getObjCode());
			objMonitorDTO.setObjName(o.getObjName());
			for (MonitorObj m : monitorObjList) {
				if (m.getObjId().equals(o.getId())) {
					objMonitorDTO.setId(m.getId());
					objMonitorDTO.setRecObj(m.getRecObj());
					objMonitorDTO.setMonitorStatus(m.getMonitorStatus());
					objMonitorDTO.setCreateTime(m.getCreateTime());
					objMonitorDTO.setUpdateTime(m.getUpdateTime());

				}
			}
			objMonitorDTOList.add(objMonitorDTO);
		}

		resultModel.setCode(CommonMessage.OK_CODE);
		resultModel.setMessage(CommonMessage.OK_DESC);
		resultModel.setData(objMonitorDTOList);

	}

	@Override
	public Integer add(HttpServletRequest request, ResultModel resultModel) {
		String id = request.getParameter("id");
		String objId = request.getParameter("objId");
		String objCode = request.getParameter("objCode");
		String recObj = request.getParameter("recObj");
		Integer monitorStatus = NumberUtils.toInt(request.getParameter("monitorStatus"), 1);

		MonitorObj monitorObj = new MonitorObj();

		monitorObj.setId(NumberUtils.toInt(id, 0));
		monitorObj.setObjId(objId);
		monitorObj.setObjCode(objCode);
		monitorObj.setRecObj(recObj);
		monitorObj.setMonitorStatus(monitorStatus);

		if (!monitorObj.getId().equals(0)) {
			return monitorObjDAO.upd(monitorObj);
		}

		return monitorObjDAO.add(monitorObj);
	}

	@Override
	public Integer update(HttpServletRequest request, ResultModel resultModel) {
		return null;
	}

	@Override
	public Integer delete(Integer id) {
		return null;
	}

	@Override
	public Integer enableById(Integer id) {
		return null;
	}

	@Override
	public Integer disableById(Integer id) {
		return null;
	}
}
