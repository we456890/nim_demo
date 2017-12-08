package com.netease.nim.uikit.common.bean;

import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.Serializable;



/**
 * struts2 将其转换成json的格式
 * 
 * @author zxy
 * 
 */
@SuppressWarnings("all")
public class OperateInfo implements Serializable {

	/** 操作是否成功 **/
	private boolean operateSuccess;
	/** 操作后的信息 **/
	private String operateMessage;
	/** 操作编码 **/
	private String operateCode;
	/** 操作成功后的url **/
	private String operateCallbackUrl;
	/** 操作成功后的url上的参数 **/
	private String operateCallbackUrlParam;
	/**
	 * 添加成功后返回的uuid
	 */
	private String uuid;
	
	public OperateInfo() {
	    super();
	}
	/**
	 * 判断是否为添加方法，设置uuid
	 * @param flag
	 */
	public OperateInfo(boolean flag) {
	    super();
	    if(flag) uuid = StringUtil.getUUID();
	}
	

	public boolean isOperateSuccess() {
		return operateSuccess;
	}

	public void setOperateSuccess(boolean operateSuccess) {
		this.operateSuccess = operateSuccess;
	}

	public String getOperateMessage() {
		return operateMessage;
	}

	public void setOperateMessage(String operateMessage) {
		this.operateMessage = operateMessage;
	}

	public String getOperateCode() {
		return operateCode;
	}

	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}

	public String getOperateCallbackUrl() {
		return operateCallbackUrl;
	}

	public void setOperateCallbackUrl(String operateCallbackUrl) {
		this.operateCallbackUrl = operateCallbackUrl;
	}

	public String getOperateCallbackUrlParam() {
		return operateCallbackUrlParam;
	}

	public void setOperateCallbackUrlParam(String operateCallbackUrlParam) {
		this.operateCallbackUrlParam = operateCallbackUrlParam;
	}
	
	public String getUuid() {
	    return uuid;
	}

	public void setUuid(String uuid) {
	    this.uuid = uuid;
	}

}
