package com.example.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 保存在云服务的好友信息
 * @author Administrator
 *
 */
public class Contacts extends BmobObject {
	
	private String userId;  //用户	
	private ArrayList<String> privateGroup;//私人组名
	private ArrayList<List<List<String>>>  privateFriend;//私人联系人
	private ArrayList<String> clientGroup;//客户组名
	private ArrayList<List<List<String>>> clientFriend;//客户联系人

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
