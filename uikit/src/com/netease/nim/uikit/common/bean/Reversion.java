package com.netease.nim.uikit.common.bean;


//@ChineseName("批阅意见")
public class Reversion extends BasePojo{

//	@ChineseName("内容")
	private String reType;
	
//	@ChineseName("详细")
	private String context;
	
//	@ChineseName("批复人")
	private String rever ;
	
//	@ChineseName("关联ID")
	private String convertId;
	
	private String reverName;
	private String reverAccount;
	
	public String getReverName() {
		return reverName;
	}

	public void setReverName(String reverName) {
		this.reverName = reverName;
	}

	public String getReverAccount() {
		return reverAccount;
	}

	public void setReverAccount(String reverAccount) {
		this.reverAccount = reverAccount;
	}

	public String getReType() {
		return reType;
	}

	public void setReType(String reType) {
		this.reType = reType;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getRever() {
		return rever;
	}

	public void setRever(String rever) {
		this.rever = rever;
	}

	public String getConvertId() {
		return convertId;
	}

	public void setConvertId(String convertId) {
		this.convertId = convertId;
	}
	
	
	
}
