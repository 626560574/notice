package com.giveu.controller;

import com.giveu.model.ApiPushMesRequestParam;
import com.giveu.notice.common.info.CommonMessage;
import com.giveu.notice.common.vo.ResultModel;
import com.giveu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 消息控制层
 * Created by fox on 2018/10/27.
 */
@RestController
@RequestMapping("/message")
public class MessageController {

	@Autowired
	MessageService messageService;


	@RequestMapping("/push/job")
	ResultModel pushJob(@RequestParam("id") String jobId, @RequestParam("name") String jobName, @RequestParam("message") String message) {
		ResultModel resultModel = new ResultModel();
		resultModel.setCode(CommonMessage.OK_CODE);
		messageService.pushJob(jobId, jobName, message, resultModel);
		return resultModel;
	}

	@RequestMapping("/push/obj")
	ResultModel pushObj(@RequestParam("id") String objId, @RequestParam("name") String objName, @RequestParam("message") String message) {
		ResultModel resultModel = new ResultModel();
		resultModel.setCode(CommonMessage.OK_CODE);
		messageService.pushObj(objId, objName, message, resultModel);
		return resultModel;
	}

	@RequestMapping("/push/api")
	ResultModel pushApi(HttpServletRequest request) throws IOException {
		StringBuffer sb = new StringBuffer();
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String s = "";
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}
		String apiModelJson = sb.toString();

		return messageService.pushApi(apiModelJson);
	}

	@RequestMapping("/push/mes")
	@ResponseBody
	ResultModel pushMes(@RequestBody ApiPushMesRequestParam param) throws IOException {
		return messageService.pushMes(param);
	}
}
