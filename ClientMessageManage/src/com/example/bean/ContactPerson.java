package com.example.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

/**
 *  �����ڱ��صĺ�����Ϣ�����ڻ���
 *  �������л�����ʹ��get set
 * @author Administrator
 *
 */
public class ContactPerson implements Serializable{
	
private static final long serialVersionUID = 1L;
  
    private String privateGroup;//˽������
    private String privateFriend;//˽����ϵ��
	private String clientGroup;//�ͻ�����
	private String clientFriend;//�ͻ���ϵ��
	private String objectIds;//����objectId���ڸ���
	private String userId;  //�û�
	
	
	public ContactPerson() {
		super();
	}

	public ContactPerson(String privateGroup, String privateFriend,
			String clientGroup, String clientFriend, String objectIds,
			String userId) {
		this.privateGroup = privateGroup;
		this.privateFriend = privateFriend;
		this.clientGroup = clientGroup;
		this.clientFriend = clientFriend;
		this.objectIds = objectIds;
		this.userId = userId;	
		Log.i("���privateFriend", ""+privateFriend);	
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getObjectIds() {
		return objectIds;
	}

	public void setObjectIds(String objectIds) {
		this.objectIds = objectIds;
	}
	
	public String getPrivateGroup(){
		return privateGroup;
	}
	public String getPrivateFriend(){
		return privateFriend;
	}
	public String getClientGroup(){
		return clientGroup;
	}
	public String getClientFriend(){
		return clientFriend;
	}
	

//˽��
	/**
	 * ���л�
	 * @param privateGroup
	 * @throws IOException
	 */
   public void  setPrivateGroups(ArrayList<String> privateGroup)throws IOException{
	
	   ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(privateGroup);
		String serStr = bos.toString("ISO-8859-1");
		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");			
		oos.flush();
		oos.close();
		bos.close();		
	this.privateGroup=serStr;  
   }
   
   /**
    * �����л�
    * @return privateGroup
    * @throws IOException
    * @throws ClassNotFoundException
    */
   public ArrayList<String> getPrivateGroups()throws 
   IOException,ClassNotFoundException {
    
    String redStr = java.net.URLDecoder.decode(privateGroup, "UTF-8");
    ByteArrayInputStream bis = new ByteArrayInputStream(
   	redStr.getBytes("ISO-8859-1"));
    ObjectInputStream ois = new ObjectInputStream(bis);
    ArrayList<String> privateGroup = (ArrayList<String>) ois.readObject();
    ois.close();
    bis.close();
   Log.i("���privateGroup", ""+privateGroup);
   return privateGroup;
   }
   

   
   public void setPrivateFriends(ArrayList<List<List<String>>> 
   privateFriend)throws IOException{

   		ByteArrayOutputStream bos = new ByteArrayOutputStream();
   		ObjectOutputStream oos = new ObjectOutputStream(bos);
   		oos.writeObject(privateFriend);
   		String serStr = bos.toString("ISO-8859-1");
   		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");			
   		oos.flush();
   		oos.close();
   		bos.close();		
   	this.privateFriend=serStr;
   }

   
    public ArrayList<List<List<String>>>  getPrivateFriends()throws 
    IOException,ClassNotFoundException {
    
    String redStr = java.net.URLDecoder.decode(privateFriend, "UTF-8");
    ByteArrayInputStream bis = new ByteArrayInputStream(
   	redStr.getBytes("ISO-8859-1"));
    ObjectInputStream ois = new ObjectInputStream(bis);
    ArrayList<List<List<String>>> privateFriend = (ArrayList<List<List<String>>>) ois.readObject();
    ois.close();
    bis.close();

    return privateFriend;
    }
   
   
     
   //�ͻ�
   public void setClientGroups(ArrayList<String> clientGroup)
		   throws IOException{

   		ByteArrayOutputStream bos = new ByteArrayOutputStream();
   		ObjectOutputStream oos = new ObjectOutputStream(bos);
   		oos.writeObject(clientGroup);
   		String serStr = bos.toString("ISO-8859-1");
   		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");			
   		oos.flush();
   		oos.close();
   		bos.close();  		
   	this.clientGroup=serStr;
   
   }

   
    public ArrayList<String> getClientGroups()throws 
    IOException,ClassNotFoundException {
    
    String redStr = java.net.URLDecoder.decode(clientGroup, "UTF-8");
    ByteArrayInputStream bis = new ByteArrayInputStream(
   	redStr.getBytes("ISO-8859-1"));
    ObjectInputStream ois = new ObjectInputStream(bis);
    ArrayList<String> clientGroup = (ArrayList<String>) ois.readObject();
    ois.close();
    bis.close();

    return clientGroup;
    }

      public void  setClientFriends(ArrayList<List<List<String>>>  clientFriend)throws IOException{
   	
   	   ByteArrayOutputStream bos = new ByteArrayOutputStream();
   		ObjectOutputStream oos = new ObjectOutputStream(bos);
   		oos.writeObject(clientFriend);
   		String serStr = bos.toString("ISO-8859-1");
   		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");			
   		oos.flush();
   		oos.close();
   		bos.close();		
   	this.clientFriend=serStr;  
      }
      
      public ArrayList<List<List<String>>>  getClientFriends()throws 
      IOException,ClassNotFoundException {
       
       String redStr = java.net.URLDecoder.decode(clientFriend, "UTF-8");
       ByteArrayInputStream bis = new ByteArrayInputStream(
      	redStr.getBytes("ISO-8859-1"));
       ObjectInputStream ois = new ObjectInputStream(bis);
       ArrayList<List<List<String>>>  clientFriend = (ArrayList<List<List<String>>>) ois.readObject();
       ois.close();
       bis.close();

      return clientFriend;
      } 
      
}
