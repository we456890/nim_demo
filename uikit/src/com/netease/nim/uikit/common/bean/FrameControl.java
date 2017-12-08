package com.netease.nim.uikit.common.bean;

//@ChineseName("流程控制器")
public class FrameControl extends BasePojo {
	
//	@ChineseName("对应ID")
	private String relationId;
	
//	@ChineseName("上一步ID")
	private String lastId;
	
//	@ChineseName("下一步ID")
	private String nextId;
	
//	@ChineseName("是否最后一步")
	private String isLast;
	
//	@ChineseName("处理人")
	private String desinerId;
	private String desinerName;
	private String desinerAccount;
	private String desinerToken;

	public String getDesinerName() {
		return desinerName;
	}

	public void setDesinerName(String desinerName) {
		this.desinerName = desinerName;
	}

	public String getDesinerAccount() {
		return desinerAccount;
	}

	public void setDesinerAccount(String desinerAccount) {
		this.desinerAccount = desinerAccount;
	}

	public String getDesinerToken() {
		return desinerToken;
	}

	public void setDesinerToken(String desinerToken) {
		this.desinerToken = desinerToken;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getLastId() {
		return lastId;
	}

	public void setLastId(String lastId) {
		this.lastId = lastId;
	}

	public String getNextId() {
		return nextId;
	}

	public void setNextId(String nextId) {
		this.nextId = nextId;
	}

	public String getIsLast() {
		return isLast;
	}

	public void setIsLast(String isLast) {
		this.isLast = isLast;
	}

	public String getDesinerId() {
		return desinerId;
	}

	public void setDesinerId(String desinerId) {
		this.desinerId = desinerId;
	}
	
}
