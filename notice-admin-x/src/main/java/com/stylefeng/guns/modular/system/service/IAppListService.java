package com.stylefeng.guns.modular.system.service;

import com.giveu.notice.common.vo.AppVo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 应用列表Service
 *
 * @author fengshuonan
 * @Date 2018-07-03 17:28:13
 */
public interface IAppListService {


	void list(HttpServletRequest request, List<Map<String, Object>> list) throws IOException;

	String getListJson();

	Integer addApp(AppVo appVo) throws IOException;

	Integer updAppById(AppVo appVo) throws IOException;

	AppVo getAppVoById(String id) throws IOException;

	Integer delAppById(String id);
	Integer enableById(String id);
	Integer disableById(String id);

}
