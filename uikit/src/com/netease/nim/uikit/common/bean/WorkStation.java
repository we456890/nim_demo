package com.netease.nim.uikit.common.bean;

import java.util.List;

//@ChineseName("工作台")
public class WorkStation<E>{

//	@ChineseName("模块")
	private String modle;
	
//	@ChineseName("数据数")
	private String number;
	
//	@ChineseName("数据列表")
	private List<E> showData;

	public String getModle() {
		return modle;
	}

	public void setModle(String modle) {
		this.modle = modle;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<E> getShowData() {
		return showData;
	}

	public void setShowData(List<E> showData) {
		this.showData = showData;
	}

	
	
	
}
