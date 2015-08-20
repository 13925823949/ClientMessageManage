package com.example.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

public class UserID extends BmobObject {
    
    private String userId;
    private String userName;
    private String userpwd;
    

	public String getUserId() {
		return userId;
	}
	public void setUserId(String uId) {
		this.userId = uId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

    

}
