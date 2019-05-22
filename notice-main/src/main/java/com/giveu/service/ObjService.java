package com.giveu.service;

import com.giveu.notice.common.vo.ResultModel;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据对象监控通知服务类
 * Created by fox on 2018/10/23.
 */
public interface ObjService {

	void list(HttpServletRequest request, ResultModel resultModel);

	Integer add(HttpServletRequest request, ResultModel resultModel);

	Integer update(HttpServletRequest request, ResultModel resultModel);

	Integer delete(Integer Id);

	Integer enableById(Integer Id);

	Integer disableById(Integer Id);
}
