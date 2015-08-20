package com.example.bean;

import cn.bmob.v3.BmobObject;

public class SendRequest extends BmobObject{

	private String requestObject;//�������Id
	private String requestPerson;//�����˵�Id
	private String addFlag;//�����־��1Ϊ��������2Ϊͬ������3Ϊ�ܾ�����
	private String objectIds;
	private String requestObjectName;//�����������
	private String requestPersonName;//�����˵�����
	
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
