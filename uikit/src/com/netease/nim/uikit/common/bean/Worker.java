package com.netease.nim.uikit.common.bean;

//@ChineseName("员工")
public class Worker extends BasePojo{
	
//	@ChineseName("员工姓名")
	private String name;
	
//	@ChineseName("头像")
	private String header;
	
//	@ChineseName("邮箱地址")
	private String email;
	
//	@ChineseName("手机")
	private String mobile;
	
//	@ChineseName("所在部门")
	private String dept;
	
//	@ChineseName("职位")
	private String position;
	
//	@ChineseName("状态")
	private String ownerIntur;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getOwnerIntur() {
		return ownerIntur;
	}

	public void setOwnerIntur(String ownerIntur) {
		this.ownerIntur = ownerIntur;
	}
	
	
	
}
