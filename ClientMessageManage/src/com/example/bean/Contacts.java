package com.example.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * �������Ʒ���ĺ�����Ϣ
 * @author Administrator
 *
 */
public class Contacts extends BmobObject {
	
	private String userId;  //�û�	
	private ArrayList<String> privateGroup;//˽������
	private ArrayList<List<List<String>>>  privateFriend;//˽����ϵ��
	private ArrayList<String> clientGroup;//�ͻ�����
	private ArrayList<List<List<String>>> clientFriend;//�ͻ���ϵ��

	public ArrayList<List<List<String>>>  getPrivateFriend() {
		return privateFriend;
	}
	public void setPrivateFriend(ArrayList<List<List<String>>> privateFriend) {
		this.privateFriend = privateFriend;
	}
	public ArrayList<String> getPrivateGroup() {
		return privateGroup;
	}
	public void setPrivateGroup(ArrayList<String> privateGroup) {
		this.privateGroup = privateGroup;
	}
	public ArrayList<String> getClientGroup() {
		return clientGroup;
	}
	public void setClientGroup(ArrayList<String> clientGroup) {
		this.clientGroup = clientGroup;
	}
	public ArrayList<List<List<String>>>  getClientFriend() {
		return clientFriend;
	}
	public void setClientFriend(ArrayList<List<List<String>>> clientFriend) {
		this.clientFriend = clientFriend;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Contacts() {
		super();
	}
  

		

}
