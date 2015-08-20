package com.example.util;

public class ConstantValue {

	//库名
	public static final String DB_NAME = "messageManage.db";
	//表名
	public static final String TABLE_NAME ="userInfos";
	/**
	 * 临时保存请求信息
	 */
	public static final String TABLE_NAME2 ="friendRequest";
	/**
	 * 保存联系人
	 */
	public static final String TABLE_NAME3 ="Contacts";
	//版本号
	public static final int VERSION =1;
	
	public static class DB_MetaData{
		public static final String USER_ID = "userId";
		public static final String NAME = "name";
		public static final String SEX = "sex";
		public static final String AGE = "age";
		public static final String QQ = "QQ";
		public static final String PHONE = "phone";
		public static final String EMAIL = "email";
		public static final String HOBBY = "hobby";
		public static final String PROVINCE = "province";
		public static final String DATE_OF_BIRTH = "dateOfBirth";
		public static final String CONTACT_ADDRESS = "contactAddress";
		public static final String MARITAL_STATUS = "maritalStatus";
		public static final String MEMORIAL = "memorial";
		public static final String DAY = "day";
		public static final String MEMORIAL2 = "memorial2";
		public static final String DAY2 = "day2";
		public static final String MEMORIAL3 = "memorial3";
		public static final String DAY3 = "day3";
		public static final String DIPLOMA = "diploma";
		public static final String MAJOR = "major";
		public static final String USER_CLASS = "userClass";
		public static final String SCHOOL = "school";
		public static final String GRADUATTONd_DATE = "graduationDate";
		public static final String PROFESSION = "profession";
		public static final String COMPANY_NAME = "companyName";
		public static final String COMPANY_ADDRESS = "companyAddress";
		public static final String DEPARTMENT = "department";
		public static final String YEARS_OF_WORKING = "yearsOfWorking";
	}
	
	public static class DB_MetaData2{
		public static final String OBJECT_ID ="objectId";
		public static final String REQUEST_OBJECT_NAME ="requestObjectName";
		public static final String REQUEST_OBJECT = "requestObject";
		public static final String REQUEST_PERSON_NAME = "requestPersonName";
		public static final String REQUEST_PERSON = "requestPerson";
		public static final String ADD_FLAG = "addFlag";
		public static final String OBJECT_SEX = "objectSex";
		public static final String PERSON_SEX = "personSex";
	}
	public static class DB_MetaData3{
		public static final String OBJECT_IDS ="objectIds";	
		public static final String USER_ID ="userId";		
		public static final String PRIVATE_GROUP ="privateGroup";
		public static final String PRIVATE_FRIEND ="privateFriend";
		public static final String CLIENT_GROUP ="clientGroup";
		public static final String CLIENT_FRIEND ="clientFriend";
	}

}
