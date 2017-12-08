package com.netease.nim.uikit.common.bean;

import java.util.List;

//@ChineseName("请假申请")
public class Holiday extends BasePojo{

//	@ChineseName("申请人")
	private String prayEr;
	
//	@ChineseName("申请人ID")
	private String prayErId;
	
//	@ChineseName("请假时间")
	private String prayDays;
	
//	@ChineseName("请假理由")
	private String reason;
	
//	@ChineseName("开始时间")
	private String fromDays;
	
//	@ChineseName("结束时间")
	private String toDays;
	
//	@ChineseName("请假类型")
	private String type;
	
	private String doner;
	
	private String within;
	
	private String step;
	
	private List<FrameRecord> recordList;
	
	private String accid;
	
	public String getAccid() {
		return accid;
	}

	public void setAccid(String accid) {
		this.accid = accid;
	}

	public List<FrameRecord> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<FrameRecord> recordList) {
		this.recordList = recordList;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getDoner() {
		return doner;
	}

	public void setDoner(String doner) {
		this.doner = doner;
	}

	public String getWithin() {
		return within;
	}

	public void setWithin(String within) {
		this.within = within;
	}

	public String getPrayEr() {
		return prayEr;
	}

	public void setPrayEr(String prayEr) {
		this.prayEr = prayEr;
	}

	public String getPrayErId() {
		return prayErId;
	}

	public void setPrayErId(String prayErId) {
		this.prayErId = prayErId;
	}

	public String getPrayDays() {
		return prayDays;
	}

	public void setPrayDays(String prayDays) {
		this.prayDays = prayDays;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getFromDays() {
		return fromDays;
	}

	public void setFromDays(String fromDays) {
		this.fromDays = fromDays;
	}

	public String getToDays() {
		return toDays;
	}

	public void setToDays(String toDays) {
		this.toDays = toDays;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
