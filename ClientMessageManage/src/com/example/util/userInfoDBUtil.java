package com.example.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


/** 
 * SQLiteOpenHelper是一个辅助类，用来管理数据库的创建和版本他，它提供两个方面的功能 
 * 第一，getReadableDatabase()、getWritableDatabase()可以获得SQLiteDatabase对象，通过该对象可以对数据库进行操作 
 * 第二，提供了onCreate()、onUpgrade()两个回调函数，允许我们再创建和升级数据库时，进行自己的操作 
 */  

public class userInfoDBUtil extends SQLiteOpenHelper{
	

	 /** 
	  * 在SQLiteOpenHelper的子类当中，必须有该构造函数 
	  * @param context   上下文对象 
	  * @param name      数据库名称 
	  * @param factory 
	  * @param version   当前数据库的版本，值必须是整数并且是递增的状态 
	  */  
	public userInfoDBUtil(Context context, String name, CursorFactory factory,
			int version) {
		//必须通过super调用父类当中的构造函数 
		super(context, name, factory, version);
	}

	 public userInfoDBUtil(Context context, String name, int version){  
		         this(context,name,null,version);  
		    }  
	 public userInfoDBUtil(Context context,int version) {
			this(context, ConstantValue.DB_NAME, null, version);
		} 
	 public userInfoDBUtil(Context context) {
			this(context,ConstantValue.DB_NAME, null, ConstantValue.VERSION);
		}
	//该函数是在第一次创建的时候执行，实际上是第一次得到SQLiteDatabase对象的时候才会调用这个方法
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		 //execSQL用于执行SQL语句  
		 db.execSQL("create table "+ConstantValue.TABLE_NAME+"("
		 +ConstantValue.DB_MetaData.USER_ID+" varchar(20),"
		 +ConstantValue.DB_MetaData.NAME+" varchar(20)," 
		 +ConstantValue.DB_MetaData.SEX+" varchar(3),"
		 +ConstantValue.DB_MetaData.AGE+" varchar(5)," 
		 +ConstantValue.DB_MetaData.QQ+" varchar(15),"
		 +ConstantValue.DB_MetaData.PHONE+" varchar(11)," 
		 +ConstantValue.DB_MetaData.EMAIL+" varchar(30),"
		 +ConstantValue.DB_MetaData.HOBBY+" varchar," 
		 +ConstantValue.DB_MetaData.PROVINCE+" varchar(10),"
		 +ConstantValue.DB_MetaData.DATE_OF_BIRTH+" varchar(15),"
		 +ConstantValue.DB_MetaData.CONTACT_ADDRESS+" varchar,"
		 +ConstantValue.DB_MetaData.MARITAL_STATUS+" varchar(6),"
		 +ConstantValue.DB_MetaData.MEMORIAL+" varchar(15),"
		 +ConstantValue.DB_MetaData.DAY+" varchar(10),"
		 +ConstantValue.DB_MetaData.MEMORIAL2+" varchar(15),"
		 +ConstantValue.DB_MetaData.DAY2+" varchar(10),"
		 +ConstantValue.DB_MetaData.MEMORIAL3+" varchar(15),"
		 +ConstantValue.DB_MetaData.DAY3+" varchar(10),"
		 +ConstantValue.DB_MetaData.DIPLOMA+" varchar(15),"
		 +ConstantValue.DB_MetaData.MAJOR+" varchar(15),"
		 +ConstantValue.DB_MetaData.USER_CLASS+" varchar(15),"
		 +ConstantValue.DB_MetaData.SCHOOL+" varchar(20),"
		 +ConstantValue.DB_MetaData.GRADUATTONd_DATE+" varchar(15),"
		 +ConstantValue.DB_MetaData.PROFESSION+" varchar(20),"
		 +ConstantValue.DB_MetaData.COMPANY_NAME+" varchar(20),"
		 +ConstantValue.DB_MetaData.COMPANY_ADDRESS+" varchar,"
		 +ConstantValue.DB_MetaData.DEPARTMENT+" varchar(15),"
		 +ConstantValue.DB_MetaData.YEARS_OF_WORKING+" varchar(15)"
		 +")");  
		 db.execSQL("create table "+ConstantValue.TABLE_NAME2+"("
		 +ConstantValue.DB_MetaData2.REQUEST_PERSON+" varchar(20),"
		 +ConstantValue.DB_MetaData2.REQUEST_PERSON_NAME+" varchar(20),"
		 +ConstantValue.DB_MetaData2.REQUEST_OBJECT+" varchar(20),"
		 +ConstantValue.DB_MetaData2.REQUEST_OBJECT_NAME+" varchar(20),"
		 +ConstantValue.DB_MetaData2.ADD_FLAG+" varchar(20),"
		 +ConstantValue.DB_MetaData2.OBJECT_ID+" varchar(20),"
		 +ConstantValue.DB_MetaData2.PERSON_SEX+" varchar(6),"
		 +ConstantValue.DB_MetaData2.OBJECT_SEX+" varchar(6)"
		 +")"); 
		 db.execSQL("create table "+ConstantValue.TABLE_NAME3+"("
		+ConstantValue.DB_MetaData3.OBJECT_IDS+" varchar(20),"
		+ConstantValue.DB_MetaData3.USER_ID+" varchar(20),"
        +ConstantValue.DB_MetaData3.PRIVATE_GROUP+" varchar,"
        +ConstantValue.DB_MetaData3.PRIVATE_FRIEND+" varchar,"
        +ConstantValue.DB_MetaData3.CLIENT_GROUP+" varchar,"
        +ConstantValue.DB_MetaData3.CLIENT_FRIEND+" varchar"
		+")");

	}
	//用于升级软件时更新数据库表结构，如增加表、列字段等操作。
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + ConstantValue.TABLE_NAME);
		onCreate(db);		
	}
	//关闭数据库
	public static void close(SQLiteDatabase db) {
		if(db != null) {
			db.close();
		}
}
	//释放游标 
	public static void closeCursor(Cursor c) {
		if (c != null) {
			c.close();
		}
	}
}
