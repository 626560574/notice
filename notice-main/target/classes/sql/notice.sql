-- 任务监控配置表 wx_monitor_job

-- 对象监控配置表 wx_monitor_obj

-- 接口监控配置表 wx_monitor_api

-- 监控人员配置表 wx_monitor_receiver


CREATE TABLE `wx_monitor_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` varchar(36) NOT NULL DEFAULT '' COMMENT '任务ID',
  `job_code` varchar(200) NOT NULL DEFAULT '' COMMENT '任务代码',
  `rec_obj` varchar(2048) NOT NULL DEFAULT '' COMMENT '接收者对象（存JSON格式）',
  `monitor_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1.启用；2.禁用；',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信端任务监控配置表\r\n主要用来配置微信端任务监控人员相关配置';


CREATE TABLE `wx_monitor_obj` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `obj_id` varchar(36) NOT NULL DEFAULT '' COMMENT '对象ID',
  `obj_code` varchar(200) NOT NULL DEFAULT '' COMMENT '对象代码',
  `rec_obj` varchar(2048) NOT NULL DEFAULT '' COMMENT '接收者对象（存JSON格式）',
  `monitor_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1.启用；2.禁用；',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信端对象监控配置表\r\n主要用来配置微信端监控对象人员相关配置';


CREATE TABLE `wx_monitor_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_name` varchar(128) NOT NULL DEFAULT '' COMMENT '项目名称',
  `pro_address` varchar(1024) NOT NULL DEFAULT '' COMMENT '项目地址',
  `rec_obj` varchar(2048) NOT NULL DEFAULT '' COMMENT '接收者对象（存JSON格式）',
  `monitor_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1.启用；2.禁用；',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信端接口监控配置表\r\n主要用来配置微信端接口监控配置人员、项目名称、项目地址相关配置';


CREATE TABLE `wx_monitor_receiver` (
  `id` varchar(36) NOT NULL COMMENT '32位uuid字符串做主键',
  `rec_no` varchar(36) NOT NULL DEFAULT '' COMMENT '接收人工号',
  `rec_name` varchar(126) NOT NULL DEFAULT '' COMMENT '接收人姓名',
  `rec_wx_id` varchar(256) NOT NULL DEFAULT '' COMMENT '接收人微信OPEN_ID',
  `rec_wx_nick` varchar(256) NOT NULL DEFAULT '' COMMENT '接收人微信昵称',
  `rec_phone` varchar(126) NOT NULL DEFAULT '' COMMENT '接收人电话号码',
  `rec_email` varchar(126) NOT NULL DEFAULT '' COMMENT '接收人邮箱地址',
  `rec_dept_no` varchar(36) NOT NULL DEFAULT '' COMMENT '接收人部门编号',
  `rec_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1.启用；2.禁用；',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信端监控人员配置表\r\n主要用来配置接收人信息';