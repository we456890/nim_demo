package com.netease.nim.uikit.common.bean;

//@ChineseName("报销单")
public class ReimburseList extends BasePojo{

//	@ChineseName("付款事由")
	private String projectName;
	
//	@ChineseName("备注摘要")
	private String bz;
	
//	@ChineseName("金额")
	private String amount;
	
//	@ChineseName("发票时间")
	private String paydate;
	
//	@ChineseName("类别")
	private String stateType;
	
	private String relateData;
	
	public String getStateType() {
		return stateType;
	}

	public void setStateType(String stateType) {
		this.stateType = stateType;
	}

	public String getRelateData() {
		return relateData;
	}

	public String getPaydate() {
		return paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}

	public void setRelateData(String relateData) {
		this.relateData = relateData;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
