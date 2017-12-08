package com.netease.nim.uikit.common.bean;

//@ChineseName("流程审批记录")
public class FrameRecord extends BasePojo{

	  //关联流程ID
	  private String concatFrame;
	  
	  //关联数据ID
	  private String concatData;
	  
	  //审批人名称
	  private String processor;
	  
	  //审批人ID
	  private String processorId;
	  
	  //审批内容
	  private String context;
	  
	  //审批结果
	  private String chosen;
	  
	  //审批人ACCID
	  private String processorAccount;
	  
	  

	public String getProcessorAccount() {
		return processorAccount;
	}

	public void setProcessorAccount(String processorAccount) {
		this.processorAccount = processorAccount;
	}

	public String getConcatFrame() {
		return concatFrame;
	}

	public void setConcatFrame(String concatFrame) {
		this.concatFrame = concatFrame;
	}

	public String getConcatData() {
		return concatData;
	}

	public void setConcatData(String concatData) {
		this.concatData = concatData;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getProcessorId() {
		return processorId;
	}

	public void setProcessorId(String processorId) {
		this.processorId = processorId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getChosen() {
		return chosen;
	}

	public void setChosen(String chosen) {
		this.chosen = chosen;
	}
	  
	  
}
