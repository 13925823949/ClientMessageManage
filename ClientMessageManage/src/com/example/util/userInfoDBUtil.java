package com.example.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


/** 
 * SQLiteOpenHelper��һ�������࣬�����������ݿ�Ĵ����Ͱ汾�������ṩ��������Ĺ��� 
 * ��һ��getReadableDatabase()��getWritableDatabase()���Ի��SQLiteDatabase����ͨ���ö�����Զ����ݿ���в��� 
 * �ڶ����ṩ��onCreate()��onUpgrade()�����ص����������������ٴ������������ݿ�ʱ�������Լ��Ĳ��� 
 */  

public class userInfoDBUtil extends SQLiteOpenHelper{
	

	 /** 
	  * ��SQLiteOpenHelper�����൱�У������иù��캯�� 
	  * @param context   �����Ķ��� 
	  * @param name      ���ݿ����� 
	  * @param factory 
	  * @param version   ��ǰ���ݿ�İ汾��ֵ���������������ǵ�����״̬ 
	  */  
	public userInfoDBUtil(Context context, String name, CursorFactory factory,
			int version) {
		//����ͨ��super���ø��൱�еĹ��캯�� 
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
	//�ú������ڵ�һ�δ�����ʱ��ִ�У�ʵ�����ǵ�һ�εõ�SQLiteDatabase�����ʱ��Ż�����������
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		 //execSQL����ִ��SQL���  
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
	//�����������ʱ�������ݿ��ṹ�������ӱ����ֶεȲ�����
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + ConstantValue.TABLE_NAME);
		onCreate(db);		
	}
	//�ر����ݿ�
	public static void close(SQLiteDatabase db) {
		if(db != null) {
			db.close();
		}
}
	//�ͷ��α� 
	public static void closeCursor(Cursor c) {
		if (c != null) {
			c.close();
		}
	}
}
