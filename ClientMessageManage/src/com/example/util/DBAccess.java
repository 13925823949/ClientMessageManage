package com.example.util;

import java.util.ArrayList;
import java.util.List;

import z.which;

import com.example.bean.ContactPerson;
import com.example.bean.Contacts;
import com.example.bean.SendRequest;
import com.example.bean.UserInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAccess {

	private userInfoDBUtil uInfoDBUtil;
	private SQLiteDatabase db;
	
	//表中列名 (未使用)
	private static String[] colNames = new String[]{
		ConstantValue.DB_MetaData.USER_ID,
		ConstantValue.DB_MetaData.NAME,
		ConstantValue.DB_MetaData.SEX,
		ConstantValue.DB_MetaData.AGE,
		ConstantValue.DB_MetaData.QQ,
		ConstantValue.DB_MetaData.PHONE,
		ConstantValue.DB_MetaData.EMAIL,
		ConstantValue.DB_MetaData.HOBBY,
		ConstantValue.DB_MetaData.PROVINCE,
		ConstantValue.DB_MetaData.DATE_OF_BIRTH,
		ConstantValue.DB_MetaData.CONTACT_ADDRESS,
		ConstantValue.DB_MetaData.MARITAL_STATUS,
		ConstantValue.DB_MetaData.MEMORIAL,
		ConstantValue.DB_MetaData.DAY,
		ConstantValue.DB_MetaData.MEMORIAL2,
		ConstantValue.DB_MetaData.DAY2,
		ConstantValue.DB_MetaData.MEMORIAL3,
		ConstantValue.DB_MetaData.DAY3,
		ConstantValue.DB_MetaData.DIPLOMA,
		ConstantValue.DB_MetaData.MAJOR,
		ConstantValue.DB_MetaData.USER_CLASS,
		ConstantValue.DB_MetaData.SCHOOL,
		ConstantValue.DB_MetaData.GRADUATTONd_DATE,
		ConstantValue.DB_MetaData.PROFESSION,
		ConstantValue.DB_MetaData.COMPANY_NAME,
		ConstantValue.DB_MetaData.COMPANY_ADDRESS,
		ConstantValue.DB_MetaData.DEPARTMENT,
		ConstantValue.DB_MetaData.YEARS_OF_WORKING
	};
	
	public DBAccess(Context context) {
		uInfoDBUtil = new userInfoDBUtil(context);
	}
	
	//增加数据到数据库
	 public void insert(UserInfo info) {
	 db = uInfoDBUtil.getWritableDatabase();
			
	 ContentValues cv = new ContentValues();
	 
	 cv.put(ConstantValue.DB_MetaData.USER_ID, info.getUserId());
	 cv.put(ConstantValue.DB_MetaData.NAME, info.getName());
	 cv.put(ConstantValue.DB_MetaData.SEX, info.getSex()); 
	 cv.put(ConstantValue.DB_MetaData.AGE,info.getAge()); 
	 cv.put(ConstantValue.DB_MetaData.QQ,info.getQQ());
	 cv.put(ConstantValue.DB_MetaData.PHONE,info.getPhone());
	 cv.put(ConstantValue.DB_MetaData.EMAIL,info.getEmail());
	 cv.put(ConstantValue.DB_MetaData.HOBBY,info.getHobby());
	 cv.put(ConstantValue.DB_MetaData.PROVINCE,info.getProvince());
	 cv.put(ConstantValue.DB_MetaData.DATE_OF_BIRTH,info.getDateOfBirth());
	 cv.put(ConstantValue.DB_MetaData.CONTACT_ADDRESS,info.getContactAddress());
	 cv.put(ConstantValue.DB_MetaData.MARITAL_STATUS,info.getMaritalStatus());
	 cv.put(ConstantValue.DB_MetaData.MEMORIAL,info.getMemorial());
	 cv.put(ConstantValue.DB_MetaData.DAY,info.getDay());
	 cv.put(ConstantValue.DB_MetaData.MEMORIAL2,info.getMemorial2());
	 cv.put(ConstantValue.DB_MetaData.DAY2,info.getDay2());
	 cv.put(ConstantValue.DB_MetaData.MEMORIAL3,info.getMemorial3());
	 cv.put(ConstantValue.DB_MetaData.DAY3,info.getDay3());
	 cv.put(ConstantValue.DB_MetaData.DIPLOMA,info.getDiploma());
	 cv.put(ConstantValue.DB_MetaData.MAJOR,info.getMajor());
	 cv.put(ConstantValue.DB_MetaData.USER_CLASS,info.getUserClass());
	 cv.put(ConstantValue.DB_MetaData.SCHOOL,info.getSchool());
	 cv.put(ConstantValue.DB_MetaData.GRADUATTONd_DATE,info.getGraduationDate());
	 cv.put(ConstantValue.DB_MetaData.PROFESSION,info.getProfession());
	 cv.put(ConstantValue.DB_MetaData.COMPANY_NAME,info.getCompanyName());
	 cv.put(ConstantValue.DB_MetaData.COMPANY_ADDRESS,info.getCompanyAddress());
	 cv.put(ConstantValue.DB_MetaData.DEPARTMENT,info.getDepartment());
	 cv.put(ConstantValue.DB_MetaData.YEARS_OF_WORKING,info.getYearsOfWorking());
	
     // 第一个参数:表名称  
     // 第二个参数：SQl不允许一个空列，如果ContentValues是空的，那么这一列被明确的指明为NULL值  
     // 第三个参数：ContentValues对象 
	 db.insert(ConstantValue.TABLE_NAME, null, cv);
	 Log.i("DBuserId2", ""+info.getName());
	 db.close();
		}
	 
	//删除单行数据
    public void delete(UserInfo info) {
			db = uInfoDBUtil.getWritableDatabase();
            //第一个参数String：表名  
            //第二个参数String：条件语句  
            //第三个参数String[]：条件值 			
			db.delete(ConstantValue.TABLE_NAME, 
			   ConstantValue.DB_MetaData.USER_ID + "=" + info.getUserId(), null);
			db.close();	
    }
	 //更新
	 public void update(UserInfo info) {
	 db = uInfoDBUtil.getWritableDatabase();
			
	 ContentValues cv = new ContentValues();
	 cv.put(ConstantValue.DB_MetaData.USER_ID, info.getUserId());
	 cv.put(ConstantValue.DB_MetaData.NAME, info.getName());
	 cv.put(ConstantValue.DB_MetaData.SEX, info.getSex()); 
	 cv.put(ConstantValue.DB_MetaData.AGE,info.getAge()); 
	 cv.put(ConstantValue.DB_MetaData.QQ,info.getQQ());
	 cv.put(ConstantValue.DB_MetaData.PHONE,info.getPhone());
	 cv.put(ConstantValue.DB_MetaData.EMAIL,info.getEmail());
	 cv.put(ConstantValue.DB_MetaData.HOBBY,info.getHobby());
	 cv.put(ConstantValue.DB_MetaData.PROVINCE,info.getProvince());
	 cv.put(ConstantValue.DB_MetaData.DATE_OF_BIRTH,info.getDateOfBirth());
	 cv.put(ConstantValue.DB_MetaData.CONTACT_ADDRESS,info.getContactAddress());
	 cv.put(ConstantValue.DB_MetaData.MARITAL_STATUS,info.getMaritalStatus());
	 cv.put(ConstantValue.DB_MetaData.MEMORIAL,info.getMemorial());
	 cv.put(ConstantValue.DB_MetaData.DAY,info.getDay());
	 cv.put(ConstantValue.DB_MetaData.MEMORIAL2,info.getMemorial2());
	 cv.put(ConstantValue.DB_MetaData.DAY2,info.getDay2());
	 cv.put(ConstantValue.DB_MetaData.MEMORIAL3,info.getMemorial3());
	 cv.put(ConstantValue.DB_MetaData.DAY3,info.getDay3());
	 cv.put(ConstantValue.DB_MetaData.DIPLOMA,info.getDiploma());
	 cv.put(ConstantValue.DB_MetaData.MAJOR,info.getMajor());
	 cv.put(ConstantValue.DB_MetaData.USER_CLASS,info.getUserClass());
	 cv.put(ConstantValue.DB_MetaData.SCHOOL,info.getSchool());
	 cv.put(ConstantValue.DB_MetaData.GRADUATTONd_DATE,info.getGraduationDate());
	 cv.put(ConstantValue.DB_MetaData.PROFESSION,info.getProfession());
	 cv.put(ConstantValue.DB_MetaData.COMPANY_NAME,info.getCompanyName());
	 cv.put(ConstantValue.DB_MetaData.COMPANY_ADDRESS,info.getCompanyAddress());
	 cv.put(ConstantValue.DB_MetaData.DEPARTMENT,info.getDepartment());
	 cv.put(ConstantValue.DB_MetaData.YEARS_OF_WORKING,info.getYearsOfWorking());
	 
	// 第一个参数String：表名  
    // 第二个参数ContentValues：ContentValues对象  
	// 第三个参数String：where字句，相当于sql语句where后面的语句，？号是占位符  
	// 第四个参数String[]：占位符的值  
	 db.update(ConstantValue.TABLE_NAME, cv, 
	 ConstantValue.DB_MetaData.USER_ID + "=" + info.getUserId(), null);
	// Log.i("DBuserId", ""+info.getName());
	 db.close();
		}
	 
   //查找
   public List<UserInfo> queryInfo(String Column,String value) {
	   db = uInfoDBUtil.getReadableDatabase();
	  List<UserInfo> uInfo=new ArrayList<UserInfo>();
	  
	   // 第一个参数String：表名  
	   // 第二个参数String[]:要查询的列名  
	   // 第三个参数String：查询条件  
	   // 第四个参数String[]：查询条件的参数  
	   // 第五个参数String:对查询的结果进行分组  
	   // 第六个参数String：对分组的结果进行限制  
	   // 第七个参数String：对查询的结果进行排序  
	   
	Cursor c =db.rawQuery("SELECT * FROM "+ConstantValue.TABLE_NAME+
			" WHERE "+Column+"=?",new String[]{value});  
	while(c.moveToNext()){
		   
		String userId=(c.getString(0));
		String name=(c.getString(1));
		String sex=(c.getString(c.getColumnIndex("sex")));
		String age=(c.getString(3));
		String QQ=(c.getString(4));
		String phone=(c.getString(5));
		String email=(c.getString(6));
		String hobby=(c.getString(7));
		String province=(c.getString(8));
		String dateOfBirth=(c.getString(9));
		String contactAddress=(c.getString(10));
		String maritalStatus=(c.getString(11));
		String memorial=(c.getString(12));
		String day=(c.getString(13));
		String memorial2=(c.getString(14));
		String day2=(c.getString(15));
		String memorial3=(c.getString(16));
		String day3=(c.getString(17));
		String diploma=(c.getString(18));
		String major=(c.getString(19));
		String userClass=(c.getString(20));
		String school=(c.getString(21));
		String graduationDate=(c.getString(22));
		String profession=(c.getString(23));
		String companyName=(c.getString(24));
		String companyAddress=(c.getString(25));
		String department=(c.getString(26));
		String yearsOfWorking=(c.getString(27));
		
		uInfo.add(new UserInfo(userId,name,sex,age,
				QQ,phone,email,hobby,
				province,dateOfBirth,contactAddress,
				maritalStatus,memorial,day,
				memorial2,day2,memorial3,day3,
				diploma,major,userClass,school,
				graduationDate,profession,companyName,
				companyAddress,department,yearsOfWorking));
		Log.i("查询表一", ""+userId+name+"/"+age+"/"+department);
	}
	
	 userInfoDBUtil.closeCursor(c);
	 userInfoDBUtil.close(db);
	return uInfo;
	 }
   
   //清空表1、2,3
   public void deleteTableUserInfo() {
		db = uInfoDBUtil.getWritableDatabase();
       //第一个参数String：表名  
       //第二个参数String：条件语句  
       //第三个参数String[]：条件值 
		db.execSQL("delete from "+ConstantValue.TABLE_NAME);
		db.execSQL("delete from "+ConstantValue.TABLE_NAME2);
		db.execSQL("delete from "+ConstantValue.TABLE_NAME3);
		db.close();	
}	
   //清空表2
   public void deleteTableSendRequest() {
		db = uInfoDBUtil.getWritableDatabase();
		db.execSQL("delete from "+ConstantValue.TABLE_NAME2);
		db.close();	
}
   
   //表二操作
   //添加数据  
   public void insert2(SendRequest sr) {
		 db = uInfoDBUtil.getWritableDatabase();				
		 ContentValues cv = new ContentValues();
		 
		 cv.put(ConstantValue.DB_MetaData2.REQUEST_OBJECT, sr.getRequestObject());	 
		 cv.put(ConstantValue.DB_MetaData2.REQUEST_PERSON, sr.getRequestPerson());	 
		 cv.put(ConstantValue.DB_MetaData2.ADD_FLAG, sr.getAddFlag());
		 cv.put(ConstantValue.DB_MetaData2.OBJECT_ID, sr.getObjectId());
		 cv.put(ConstantValue.DB_MetaData2.REQUEST_OBJECT_NAME, sr.getRequestObjectName());
		 cv.put(ConstantValue.DB_MetaData2.REQUEST_PERSON_NAME, sr.getRequestPersonName());
		 db.insert(ConstantValue.TABLE_NAME2, null, cv);
//		 db.execSQL("INSERT OR IGNORE INTO "+ConstantValue.TABLE_NAME2+
//				" ("+ConstantValue.DB_MetaData2.OBJECT_ID+")values("+cv+")");
		 
		 db.close();
   }
   
   
   //去重复值
   public void offRepetition(){
	   db = uInfoDBUtil.getWritableDatabase();
	   db.execSQL("delete from "+ConstantValue.TABLE_NAME2+" where rowid not in(select " +
	   		"max(rowid) from "+ConstantValue.TABLE_NAME2+" group by "+ 
			ConstantValue.DB_MetaData2.OBJECT_ID+")");
	   db.close();
   }
	//删除单行数据
   public void delete2(SendRequest sr) {
			db = uInfoDBUtil.getWritableDatabase();
           //第一个参数String：表名  
           //第二个参数String：条件语句  
           //第三个参数String[]：条件值 			
			db.delete(ConstantValue.TABLE_NAME, 
			   ConstantValue.DB_MetaData2.ADD_FLAG + "=" + sr.getAddFlag(), null);
			db.close();	
   }
 //更新
 	 public void update2(SendRequest sr) {
 	 db = uInfoDBUtil.getWritableDatabase();			
 	 ContentValues cv = new ContentValues();
 	 cv.put(ConstantValue.DB_MetaData2.REQUEST_OBJECT, sr.getRequestObject());	 
	 cv.put(ConstantValue.DB_MetaData2.REQUEST_PERSON, sr.getRequestPerson());	 
	 cv.put(ConstantValue.DB_MetaData2.ADD_FLAG, sr.getAddFlag());	
	 cv.put(ConstantValue.DB_MetaData2.REQUEST_OBJECT_NAME, sr.getRequestObjectName());
	 cv.put(ConstantValue.DB_MetaData2.REQUEST_PERSON_NAME, sr.getRequestPersonName());
	 db.update(ConstantValue.TABLE_NAME2, cv,null, null);			
	 Log.i("ssssssss", ""+sr.getRequestObject()+"/"+sr.getRequestPerson()+"/"+sr.getAddFlag());		
	 db.close();
 	 }
 
 	 //查找表中是否有内容
 	   public boolean queryTable2() {
 		 
 		 db = uInfoDBUtil.getReadableDatabase();
		 Cursor c =db.query(ConstantValue.TABLE_NAME2, null,null,  
         null, null, null, null);  
        if(c.moveToNext()==false){
        	db.close();
        	return false;	
        }else{
        	db.close();
            return true;
        }
        
 	   }
 	   
   //查找申请信息
 	public List<SendRequest> queryTablePending(String rot) {
 		db = uInfoDBUtil.getReadableDatabase();
 		List<SendRequest> sr=new ArrayList<SendRequest>();
 		Cursor c =db.rawQuery("SELECT * FROM "+ConstantValue.TABLE_NAME2+
 				" WHERE requestPerson=?",new String[]{rot});  
 		while(c.moveToNext()){
 		String requestObject=c.getString(c.getColumnIndex("requestObject"));
 		String requestPerson=c.getString(c.getColumnIndex("requestPerson"));
 		String addFlag=c.getString(c.getColumnIndex("addFlag"));
 		String objectId=c.getString(c.getColumnIndex("objectId"));
 		String requestObjectName=c.getString(c.getColumnIndex("requestObjectName"));
 		String requestPersonName=c.getString(c.getColumnIndex("requestPersonName"));
 		sr.add(new SendRequest(requestObject,requestPerson,addFlag,
 		  objectId,requestObjectName,requestPersonName));
 		}
 		db.close();
		return sr;
 		
 	}
   //查询请求消息
 	public List<SendRequest> queryTableRequest(String rot) {
 		db = uInfoDBUtil.getReadableDatabase();
 		List<SendRequest> sr=new ArrayList<SendRequest>();
 		Cursor c =db.rawQuery("SELECT * FROM "+ConstantValue.TABLE_NAME2+
 				" WHERE requestObject=?",new String[]{rot});  
 		while(c.moveToNext()){
 		String requestObject=c.getString(c.getColumnIndex("requestObject"));
 		String requestPerson=c.getString(c.getColumnIndex("requestPerson"));
 		String addFlag=c.getString(c.getColumnIndex("addFlag"));
 		String objectId=c.getString(c.getColumnIndex("objectId"));
 		String requestObjectName=c.getString(c.getColumnIndex("requestObjectName"));
 		String requestPersonName=c.getString(c.getColumnIndex("requestPersonName"));
 		sr.add(new SendRequest(requestObject,requestPerson,addFlag,
 		  objectId,requestObjectName,requestPersonName));
 		}
 		db.close();
		return sr;
 		
 	}
    
    
	 //查找表中是否有内容
	   public boolean queryTable3() {
		 
		 db = uInfoDBUtil.getReadableDatabase();
		 Cursor c =db.query(ConstantValue.TABLE_NAME3, null,null,  
      null, null, null, null);  
     if(c.moveToNext()==false){
     	db.close();
     	return false;	
     }else{
     	db.close();
         return true;
     }
	   }
   
	 /**
	  * 查询表3中信息
	  * @param rot 查询的Id号
	  * @return 返回全部信息
	  */
    public List<ContactPerson> queryTableInfo3(String rot) {
 		db = uInfoDBUtil.getReadableDatabase();
 		List<ContactPerson> cp=new ArrayList<ContactPerson>();
 		
 		Cursor c =db.rawQuery("SELECT * FROM "+ConstantValue.TABLE_NAME3+
 				" WHERE userId=?",new String[]{rot});  
 		while(c.moveToNext()){
 		String objectIds=c.getString(c.getColumnIndex("objectIds"));
 		String userId=c.getString(c.getColumnIndex("userId")); 		
 		String privateGroup=c.getString(c.getColumnIndex("privateGroup"));
 		String privateFriend=c.getString(c.getColumnIndex("privateFriend"));
 		String clientGroup=c.getString(c.getColumnIndex("clientGroup"));
 		String clientFriend=c.getString(c.getColumnIndex("clientFriend"));
 		Log.i("查询表三结果", ""+objectIds+userId+privateGroup);
 		
 		cp.add(new ContactPerson(privateGroup,privateFriend,
 				clientGroup,clientFriend,objectIds,userId));		
 		}
 		db.close();
		return cp; 		
    } 
    
    /**
     * 单个更新
     * @param key 更新的Id
     * @param column 需要更新的列名
     * @param values 更新的值
     */
    public void updateTable3Single(String key,String column,String values){
     db=uInfoDBUtil.getWritableDatabase();
     ContentValues cv=new ContentValues();
     cv.put(column,values);
     db.update(ConstantValue.TABLE_NAME3, cv,
      ConstantValue.DB_MetaData3.USER_ID+ "=" + key, null);
     db.close();     
    }
    
    /**
     * 更新整个表3
     * @param cp
     */
    public void insertTable3(ContactPerson cp){
        db=uInfoDBUtil.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(ConstantValue.DB_MetaData3.USER_ID,cp.getUserId());
        cv.put(ConstantValue.DB_MetaData3.OBJECT_IDS,cp.getObjectIds());
        cv.put(ConstantValue.DB_MetaData3.PRIVATE_GROUP,cp.getPrivateGroup());
        cv.put(ConstantValue.DB_MetaData3.PRIVATE_FRIEND,cp.getPrivateFriend());
        cv.put(ConstantValue.DB_MetaData3.CLIENT_GROUP,cp.getClientGroup());
        cv.put(ConstantValue.DB_MetaData3.CLIENT_FRIEND,cp.getClientFriend());
        
        db.insert(ConstantValue.TABLE_NAME3, null, cv);
        Log.i("表三数据更新完毕", ""+cp.getUserId());
        db.close();     
       }
    
    /**
     * 查询用户的ObjectId
     * @param rot 用户ID
     * @return ObjectId
     */
    public String queryTable3ObjectId(String rot) {
    	db=uInfoDBUtil.getReadableDatabase();
 		Cursor c =db.rawQuery("SELECT * FROM "+ConstantValue.TABLE_NAME3+
 				" WHERE userId=?",new String[]{rot}); 
 		while (c.moveToNext()) {
		String objectId=c.getString(c.getColumnIndex("objectIds"));	
		
		return objectId;
 		};		
		return null;
    	
    }
    
}
