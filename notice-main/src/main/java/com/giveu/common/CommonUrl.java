package com.giveu.common;

/**
 * 公共URL地址
 * Created by fox on 2018/10/30.
 */
public class CommonUrl {


	// 微信OAuth2.0获取人员信息地址 -> code & token
	public static final String WX_OAUTH_USER_INFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";


	// 任务预警统计地址
//	public static final String STAT_JOB_URL = "http://10.14.21.93:9050/we/cat/push/job/log/stat";
//	public static final String STAT_OBJ_URL = "http://10.14.21.93:9050/we/cat/push/monitor/error/log";
//	// 任务主题
//	public static final String TOP_JOB_URL = "http://10.14.21.93:9050/we/cat/push/job/top/stat";
//	// 数据主题
//	public static final String TOP_OBJ_URL = "http://10.14.21.93:9050/we/cat/push/monitor/error/log/top";

	// 任务预警统计地址
//	public static final String STAT_JOB_URL = "http://10.10.11.52:9050/we/cat/push/job/log/stat";
	public static final String STAT_JOB_URL = "http://10.11.13.30:9050/we/cat/push/job/log/stat";
//	public static final String STAT_OBJ_URL = "http://10.10.11.52:9050/we/cat/push/monitor/error/log";
	public static final String STAT_OBJ_URL = "http://10.11.13.30:9050/we/cat/push/monitor/error/log";
	// 任务主题
//	public static final String TOP_JOB_URL = "http://10.10.11.52:9050/we/cat/push/job/top/stat";
	public static final String TOP_JOB_URL = "http://10.11.13.30:9050/we/cat/push/job/top/stat";
	// 数据主题
//	public static final String TOP_OBJ_URL = "http://10.10.11.52:9050/we/cat/push/monitor/error/log/top";
	public static final String TOP_OBJ_URL = "http://10.11.13.30:9050/we/cat/push/monitor/error/log/top";



}
