package com.giveu.service;


import com.giveu.model.ApiPushMesRequestParam;
import com.giveu.notice.common.vo.ResultModel;

/**
 * Created by fox on 2018/10/27.
 */
public interface MessageService {

	void pushJob(String jobId, String jobName, String message, ResultModel resultModel);

	void pushObj(String objId, String objName, String message, ResultModel resultModel);

	ResultModel pushApi(String json);

	/**
	 * 推送自定义消息至微信端
	 * @param param
	 * @return
	 */
	ResultModel pushMes(ApiPushMesRequestParam param);
}
