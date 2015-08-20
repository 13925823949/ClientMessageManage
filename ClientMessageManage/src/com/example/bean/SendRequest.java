package com.example.bean;

import cn.bmob.v3.BmobObject;

public class SendRequest extends BmobObject{

	private String requestObject;//被请求的Id
	private String requestPerson;//请求人的Id
	private String addFlag;//请求标志，1为发送请求，2为同意请求，3为拒绝请求
	private String objectIds;
	private String requestObjectName;//被请求的姓名
	private String requestPersonName;//请求人的姓名
	
	public SendRequest() {
		super();
	}
	public SendRequest(String requestObject, String requestPerson,
			String addFlag,String objectIds,String requestObjectName,
			String requestPersonName) {
		super();
		this.requestObject = requestObject;
		this.requestPerson = requestPerson;
		this.addFlag = addFlag;
		this.objectIds=objectIds;
		this.requestObjectName=requestObjectName;
		this.requestPersonName=requestPersonName;
	}

	public String getRequestObjectName() {
		return requestObjectName;
	}
	public void setRequestObjectName(String requestObjectName) {
		this.requestObjectName = requestObjectName;
	}
	public String getRequestPersonName() {
		return requestPersonName;
	}
	public void setRequestPersonName(String requestPersonName) {
		this.requestPersonName = requestPersonName;
	}
	public String getObjectIds() {
		return objectIds;
	}
	public void setObjectIds(String objectIds) {
		this.objectIds = objectIds;
	}
	public String getRequestObject() {
		return requestObject;
	}
	public void setRequestObject(String requestObject) {
		this.requestObject = requestObject;
	}
	public String getRequestPerson() {
		return requestPerson;
	}
	public void setRequestPerson(String requestPerson) {
		this.requestPerson = requestPerson;
	}
	public String getAddFlag() {
		return addFlag;
	}
	public void setAddFlag(String addFlag) {
		this.addFlag = addFlag;
	}
	
}
