package com.example.bean;

import cn.bmob.v3.BmobObject;

public class UserInfo extends BmobObject {

	private String userId;//用户ID
	private String name;//名字
	private String sex;//性别
	private String age;//年龄
	private String QQ;//qq
	private String phone;//电话号码
	private String email;//电子邮箱
	private String hobby;//爱好
	private String province;//籍贯
	private String dateOfBirth;//出生日期
	private String contactAddress;//联系地址
	private String maritalStatus;//婚姻状况
	private String memorial;//纪念
	private String day;//纪念日期
	private String memorial2;//纪念2
	private String day2;//纪念日期2
	private String memorial3;//纪念3
	private String day3;//纪念日期3
	private String diploma;//学历
	private String major;//专业
	private String userClass;//班级
	private String school;//毕业院校
	private String graduationDate;//毕业时间
	private String profession;//职业
	private String companyName;//公司名称
	private String companyAddress;//公司地址
	private String department;//部门
	private String yearsOfWorking;//工作年限
		

	public UserInfo(String userId, String name, String sex, String age,
			String qQ, String phone, String email, String hobby,
			String province, String dateOfBirth, String contactAddress,
			String maritalStatus, String memorial, String day,
			String memorial2, String day2, String memorial3, String day3,
			String diploma, String major, String userClass, String school,
			String graduationDate, String profession, String companyName,
			String companyAddress, String department, String yearsOfWorking) {
		super();
		this.userId = userId;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.QQ = qQ;
		this.phone = phone;
		this.email = email;
		this.hobby = hobby;
		this.province = province;
		this.dateOfBirth = dateOfBirth;
		this.contactAddress = contactAddress;
		this.maritalStatus = maritalStatus;
		this.memorial = memorial;
		this.day = day;
		this.memorial2 = memorial2;
		this.day2 = day2;
		this.memorial3 = memorial3;
		this.day3 = day3;
		this.diploma = diploma;
		this.major = major;
		this.userClass = userClass;
		this.school = school;
		this.graduationDate = graduationDate;
		this.profession = profession;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.department = department;
		this.yearsOfWorking = yearsOfWorking;
	}

	public UserInfo (){
		
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getMemorial() {
		return memorial;
	}
	public void setMemorial(String memorial) {
		this.memorial = memorial;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMemorial2() {
		return memorial2;
	}
	public void setMemorial2(String memorial2) {
		this.memorial2 = memorial2;
	}
	public String getDay2() {
		return day2;
	}
	public void setDay2(String day2) {
		this.day2 = day2;
	}
	public String getMemorial3() {
		return memorial3;
	}
	public void setMemorial3(String memorial3) {
		this.memorial3 = memorial3;
	}
	public String getDay3() {
		return day3;
	}
	public void setDay3(String day3) {
		this.day3 = day3;
	}
	public String getDiploma() {
		return diploma;
	}
	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getUserClass() {
		return userClass;
	}
	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getGraduationDate() {
		return graduationDate;
	}
	public void setGraduationDate(String graduationDate) {
		this.graduationDate = graduationDate;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getYearsOfWorking() {
		return yearsOfWorking;
	}
	public void setYearsOfWorking(String yearsOfWorking) {
		this.yearsOfWorking = yearsOfWorking;
	}
	
	
}
