package com.giveu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.giveu.core.Wechat.SendMsgUtil;
import com.giveu.dao.MonitorApiDAO;
import com.giveu.dao.MonitorJobDAO;
import com.giveu.dao.MonitorObjDAO;
import com.giveu.dao.MonitorRecDAO;
import com.giveu.entity.MonitorApi;
import com.giveu.entity.MonitorJob;
import com.giveu.entity.MonitorObj;
import com.giveu.entity.MonitorRec;
import com.giveu.job.common.util.DateUtil;
import com.giveu.model.ApiModel;
import com.giveu.model.ApiPushMesRequestParam;
import com.giveu.notice.common.info.CommonMessage;
import com.giveu.notice.common.vo.ResultModel;
import com.giveu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by fox on 2018/10/27.
 */
@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	MonitorJobDAO monitorJobDAO;

	@Autowired
	MonitorObjDAO monitorObjDAO;

	@Autowired
	MonitorApiDAO monitorApiDAO;

	@Autowired
	MonitorRecDAO monitorRecDAO;

	@Autowired
	SendMsgUtil sendMsgUtil;

//	private static String REPLACE_JOB_ID = "jobId";

	//	private static String JOB_DETAIL_URL = "<a href='http://noticecenter.jyfq.com/dataMobile/getDetail/%s'>详情</a>";
//	private static String OBJ_DETAIL_URL = "<a href='http://noticecenter.jyfq.com/taskMobile/getList//%s'>详情</a>";
	private static String JOB_DETAIL_URL = "<a href='http://noticecenter.jyfq.com/mobile/log/list?jobId=%s'>详情</a>";
	private static String OBJ_DETAIL_URL = "<a href='http://noticecenter.jyfq.com/mobile/detail/obj?objId=%s'>详情</a>";
	private static String API_LIST_URL = "<a href='http://noticecenter.jyfq.com/mobile/stat/apiList?proId=%s&apiPath=%s'>列表</a>";


	private static final String NOTICE_TITLE = "预警通知";
	private static final String LINE_FEED = "\n";
	private static final String TYPE_TITLE = "类别";
	private static final String COLON = "：";
	private static final String MONITOR_JOB_TITLE = "任务预警";
	private static final String MONITOR_OBJ_TITLE = "数据预警";
	private static final String MONITOR_API_TITLE = "接口预警";
	private static final String MONITOR_JOB_NAME_TITLE = "任务名称";
	private static final String MONITOR_OBJ_NAME_TITLE = "数据名称";
	private static final String MONITOR_API_NAME_TITLE = "系统名称";
	private static final String MONITOR_INFO_TITLE = "预警信息";
	private static final String MONITOR_SERVER_ADDRESS = "服务器地址";

	// 竖杠
	private static final String VERTICAL_BAR_SYMBOL = "|";


	@Override
	public void pushJob(String jobId, String jobName, String message, ResultModel resultModel) {
		MonitorJob monitorJob = monitorJobDAO.getByJobId(jobId);

		if (monitorJob == null) {
			resultModel.setCode(CommonMessage.TABLE_NOT_FOUND_ERROR_CODE);
			resultModel.setMessage(CommonMessage.TABLE_NOT_FOUND_ERROR_DESC);
			return;
		}

		String recObjJson = monitorJob.getRecObj();
		String receivers = getPushReceivers(recObjJson);

		StringBuilder sb = new StringBuilder();
		sb.append(NOTICE_TITLE);
		sb.append(LINE_FEED);
		sb.append(DateUtil.dateToStrLong(new Date()));
		sb.append(LINE_FEED);
		sb.append(TYPE_TITLE);
		sb.append(COLON);
		sb.append(MONITOR_JOB_TITLE);
		sb.append(LINE_FEED);
		sb.append(MONITOR_JOB_NAME_TITLE);
		sb.append(COLON);
		sb.append(jobName + "(" + monitorJob.getJobCode() + ")");
		sb.append(LINE_FEED);
		sb.append(MONITOR_INFO_TITLE);
		sb.append(COLON);
		sb.append(message);
		sb.append(LINE_FEED);
		String url = String.format(JOB_DETAIL_URL, monitorJob.getJobId());
		sb.append(url);
		String text = sb.toString();

		JSONObject jsonObject = sendMsgUtil.sendMsg(text, receivers);
	}

	@Override
	public void pushObj(String objId, String objName, String message, ResultModel resultModel) {
		MonitorObj monitorObj = monitorObjDAO.getByObjId(objId);
		if (monitorObj == null) {
			resultModel.setCode(CommonMessage.TABLE_NOT_FOUND_ERROR_CODE);
			resultModel.setMessage(CommonMessage.TABLE_NOT_FOUND_ERROR_DESC);
			return;
		}

		String recObjJson = monitorObj.getRecObj();
		String receivers = getPushReceivers(recObjJson);

		StringBuilder sb = new StringBuilder();
		sb.append(NOTICE_TITLE);
		sb.append(LINE_FEED);
		sb.append(DateUtil.dateToStrLong(new Date()));
		sb.append(LINE_FEED);
		sb.append(TYPE_TITLE);
		sb.append(COLON);
		sb.append(MONITOR_OBJ_TITLE);
		sb.append(LINE_FEED);
		sb.append(MONITOR_OBJ_NAME_TITLE);
		sb.append(COLON);
		sb.append(objName);
		sb.append(LINE_FEED);
		sb.append(MONITOR_INFO_TITLE);
		sb.append(COLON);
		sb.append(message);
		sb.append(LINE_FEED);
		String url = String.format(OBJ_DETAIL_URL, monitorObj.getObjId());
		sb.append(url);
		String text = sb.toString();

		JSONObject jsonObject = sendMsgUtil.sendMsg(text, receivers);
	}

	@Override
	public ResultModel pushApi(String json) {

		ResultModel resultModel = new ResultModel();

		List<ApiModel> apiModelList = JSONObject.parseArray(json, ApiModel.class);

		List<MonitorApi> monitorApiList = monitorApiDAO.getListExcludeDisable();

		for (MonitorApi m : monitorApiList) {
			String addressListJson = m.getProAddress();
			List<String> addressList = JSONObject.parseArray(addressListJson, String.class);
			for (String address : addressList) {
				for (ApiModel api : apiModelList) {
					String adr = api.getHost() + ":" + api.getPort();
					if (adr.equals(address)) {
						String recObjJson = m.getRecObj();
						pushApiX(address, m, api);
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			}
		}

		resultModel.setCode(CommonMessage.OK_CODE);

		return resultModel;

	}

	@Override
	public ResultModel pushMes(ApiPushMesRequestParam param) {
		ResultModel resultModel = new ResultModel();
		resultModel.setCode(CommonMessage.PAR_NOT_NULL_CODE);
		if (param.getReceiver() == null || param.getReceiver().size() == 0) {
			resultModel.setMessage("接收信息对象 为空");
			resultModel.setData("");
			return resultModel;
		}
		if (param.getCategory() == null || param.getCategory().equals("")) {
			resultModel.setMessage("信息类别 为空");
			resultModel.setData("");
			return resultModel;
		}
		if (param.getCategory().length() > 20) {
			resultModel.setMessage("信息类别过长 为空");
			resultModel.setData("");
			return resultModel;
		}
		if (param.getMessage() == null || param.getMessage().equals("")) {
			resultModel.setMessage("信息 为空");
			resultModel.setData("");
			return resultModel;
		}
		if (param.getMessage().length() > 100) {
			resultModel.setMessage("信息长度过长 为空");
			resultModel.setData("");
			return resultModel;
		}
		if (param.getDetailUrl() == null) {
			param.setDetailUrl("");
		}

		List<MonitorRec> recList = monitorRecDAO.getListExcludeDisable();
		if (recList.size() == 0) {
			resultModel.setMessage("信息接收者未注册至企业微信");
			resultModel.setData("");
			return resultModel;
		}

		List<String> wxUserIds = new ArrayList<>();
		List<String> outReceiver = new ArrayList<>();
		for (String r : param.getReceiver()) {
			if (r == null || r.equals("")) {
				continue;
			}
			Boolean isHas = false;
			for (MonitorRec rec : recList) {
				if (rec.getRecNo().toLowerCase().equals(r.toLowerCase())) {
					wxUserIds.add(rec.getRecWxId());
					isHas = true;
					break;
				}
			}
			if (!isHas) {
				outReceiver.add(r);
			}
		}
		if (wxUserIds.size() == 0) {
			resultModel.setMessage("信息接收者均未注册至企业微信");
			resultModel.setData("");
			return resultModel;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("自定义消息通知");
		sb.append(LINE_FEED);
		sb.append(DateUtil.dateToStrLong(new Date()));
		sb.append(LINE_FEED);
		sb.append(TYPE_TITLE);
		sb.append(COLON);
		sb.append(param.getCategory());
		sb.append(LINE_FEED);
		sb.append("信息内容");
		sb.append(COLON);
		sb.append(param.getMessage());
		if (!param.getDetailUrl().equals("")) {
			sb.append(LINE_FEED);
			sb.append("<a href='" + param.getDetailUrl() + "'>详情</a>");
		}
		String text = sb.toString();
		JSONObject jsonObject = sendMsgUtil.sendMsg(text, String.join(VERTICAL_BAR_SYMBOL, wxUserIds));
		resultModel.setCode(CommonMessage.OK_CODE);
		if (outReceiver.size() > 0) {
			resultModel.setMessage(String.format("信息发送成功，[%s]等工号不存在或未注册企业微信", String.join(",", outReceiver)));
		} else {
			resultModel.setMessage("信息发送成功");
		}
		resultModel.setData("");
		return resultModel;
	}

	private void pushApiX(String address, MonitorApi monitorApi, ApiModel api) {

		String recObjJson = monitorApi.getRecObj();
		String receivers = getPushReceivers(recObjJson);

		StringBuilder sb = new StringBuilder();
		sb.delete(0, sb.length());

		sb.append(NOTICE_TITLE);
		sb.append(LINE_FEED);
		sb.append(DateUtil.dateToStrLong(new Date()));
		sb.append(LINE_FEED);
		sb.append(TYPE_TITLE);
		sb.append(COLON);
		sb.append(MONITOR_API_TITLE + "(" + monitorApi.getProName() + ")");
		sb.append(LINE_FEED);
		sb.append(MONITOR_SERVER_ADDRESS);
		sb.append(COLON);
		sb.append(address);
		sb.append(LINE_FEED);
		sb.append(MONITOR_API_NAME_TITLE);
		sb.append(COLON);
		sb.append(monitorApi.getProName());
		sb.append(LINE_FEED);
		sb.append(MONITOR_INFO_TITLE);
		sb.append(COLON);
		sb.append(api.getApi());
		sb.append(LINE_FEED);
		String url = String.format(API_LIST_URL, monitorApi.getId(), api.getApi());
		sb.append(url);
		String text = sb.toString();

		JSONObject jsonObject = sendMsgUtil.sendMsg(text, receivers);
	}


	String getPushReceivers(String recObjJson) {

		List<MonitorRec> monitorRecList = JSONObject.parseArray(recObjJson, MonitorRec.class);

		List<MonitorRec> recList = monitorRecDAO.getListExcludeDisable();

		Map<Integer, String> map = new HashMap<>();
		for (MonitorRec rec : recList) {
			map.put(rec.getId(), rec.getRecWxId());
		}

		StringBuilder sb = new StringBuilder();
		for (MonitorRec rec : monitorRecList) {
			String wxId = map.get(rec.getId());
			if (wxId != null) {
				sb.append(wxId);
				sb.append(VERTICAL_BAR_SYMBOL);
			}
		}

		return sb.toString();
	}
}
