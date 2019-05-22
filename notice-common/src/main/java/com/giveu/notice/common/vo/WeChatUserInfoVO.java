package com.giveu.notice.common.vo;

/**
 * Created by fox on 2018/10/30.
 */
public class WeChatUserInfoVO {
	// 成员userid
	private String userid;
	//成员名称
	private String name;
	//成员所需部门id
	private String department;
	//职务信息
	private String position;
	//手机号码
	private String mobile;
	//性别 0未定义 1男 2女
	private String gender;
	//邮箱
	private String email;
	//头像url
	private String avatar;
	/*    激活状态 1=已激活，2=已禁用，4=未激活
	已激活代表已激活企业微信或已关注微工作台（原企业号）
	。未激活代表既未激活企业微信又未关注微工作台（原企业号）。*/
	private String status;
	/*
	 * 成员启用状态。1表示启用的成员，
	 * 0表示被禁用。
	 * 服务商调用接口不会返回此字段*/
	private String enable;
	//标示是否为上级；第三方仅通讯录应用可获取
	private String isleader;
	//扩展属性，第三方仅通讯录套件可获取
	private String extattr;
	//英文名
	private String english_name;
	//座机
	private String telephone;
	/*
	 * 部门内的排序值，默认为0。
	 * 数量必须和department一致，
	 * 数值越大排序越前面。
	 * 值范围是[0, 2^32)*/
	private String order;
	/*
	 * 员工个人二维码，扫描可添加为外部联系人；
	 * 第三方仅通讯录应用可获取*/
	private String qr_code;
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getIsleader() {
		return isleader;
	}

	public void setIsleader(String isleader) {
		this.isleader = isleader;
	}

	public String getExtattr() {
		return extattr;
	}

	public void setExtattr(String extattr) {
		this.extattr = extattr;
	}

	public String getEnglish_name() {
		return english_name;
	}

	public void setEnglish_name(String english_name) {
		this.english_name = english_name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getQr_code() {
		return qr_code;
	}

	public void setQr_code(String qr_code) {
		this.qr_code = qr_code;
	}
}
