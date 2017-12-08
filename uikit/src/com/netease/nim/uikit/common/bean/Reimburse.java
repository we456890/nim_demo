package com.netease.nim.uikit.common.bean;

import java.util.List;

//@ChineseName("报销")
public class Reimburse extends BasePojo{

//	@ChineseName("报销事由")
	private String reimburseTitle;
	
//	@ChineseName("报销时间")
	private String reimurseTime;
	
//	@ChineseName("报销人")
	private String reimurser;
	
//	@ChineseName("报销人ID")
	private String reimurserId;
	
//	@ChineseName("报销单编号")
	private String remiurserJob;
	
//	@ChineseName("报销总金额")
	private String remiurseHole;
	
//	@ChineseName("共付单据")
	private String docs;
	
//	@ChineseName("支付金额")
	private String payCount;
	
	
	//流程控制相关
	private String doner;
	
	private String within;
	
	private String step;
	
	//流程名称
	private String frameTyle;
	//审批记录
	private List<FrameRecord> recordList;
	
	public String getDocs() {
		return docs;
	}

	public void setDocs(String docs) {
		this.docs = docs;
	}

	public String getReimburseTitle() {
		return reimburseTitle;
	}

	public void setReimburseTitle(String reimburseTitle) {
		this.reimburseTitle = reimburseTitle;
	}

	public String getReimurseTime() {
		return reimurseTime;
	}

	public void setReimurseTime(String reimurseTime) {
		this.reimurseTime = reimurseTime;
	}

	public String getReimurser() {
		return reimurser;
	}

	public void setReimurser(String reimurser) {
		this.reimurser = reimurser;
	}

	public String getReimurserId() {
		return reimurserId;
	}

	public void setReimurserId(String reimurserId) {
		this.reimurserId = reimurserId;
	}

	public String getRemiurserJob() {
		return remiurserJob;
	}

	public void setRemiurserJob(String remiurserJob) {
		this.remiurserJob = remiurserJob;
	}

	public String getRemiurseHole() {
		return remiurseHole;
	}

	public void setRemiurseHole(String remiurseHole) {
		this.remiurseHole = remiurseHole;
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

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getFrameTyle() {
		return frameTyle;
	}

	public void setFrameTyle(String frameTyle) {
		this.frameTyle = frameTyle;
	}

	public List<FrameRecord> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<FrameRecord> recordList) {
		this.recordList = recordList;
	}

}
