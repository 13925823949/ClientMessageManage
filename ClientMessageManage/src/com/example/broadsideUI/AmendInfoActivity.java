package com.example.broadsideUI;

import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.bean.UserInfo;
import com.example.clientmessagemanage.R;
import com.example.util.ConstantValue;
import com.example.util.DBAccess;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AmendInfoActivity extends Activity implements OnClickListener{
	
	
	private TextView amendInfoReturn;
	private TextView amendInfoOK;
	private LinearLayout mDayLayout;
	private LinearLayout mDayLayout2;
	private LinearLayout mDayLayout3;
	private TextView add_dateOfBirth;
	private TextView add_sex;
	private TextView add_memorial;
	private TextView add_day;
	private TextView add_memorial2;
	private TextView add_day2;
	private TextView add_memorial3;
	private TextView add_day3;
	private EditText add_name;
	private EditText add_age;
	private EditText add_QQ;
	private EditText add_phone;
	private EditText add_email;
	private EditText add_hobby;
	private EditText add_province;
	private EditText add_contactAddress;
	private EditText add_maritalStatus;
	private EditText add_diploma;
	private EditText add_major;
	private EditText add_userClass;
	private EditText add_school;
	private TextView add_graduationDate;
	private EditText add_profession;
	private EditText add_companyName;
	private EditText add_companyAddress;
	private EditText add_department;
	private EditText add_yearsOfWorking;
	
	private TextView deleteDate;
	//�洢��ǰ��¼�˺�
   private	String currentUser;
	
	//�洢objectId
	private String objId;
	//�����ӡ�����״̬
	private int mark;
	//������
	String[] memorial={"���׽�","ĸ�׽�","��������","����������","��������","ɾ����ǰ������"};
	
	private List<UserInfo> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.broadside_amend_info);

		//�ڶ�ȡSharedPreferences����ǰҪʵ������һ��SharedPreferences���� 
		SharedPreferences sharedPreferences= getSharedPreferences("test", 
		Activity.MODE_PRIVATE); 
		// ʹ��getString�������value��ע���2��������value��Ĭ��ֵ 
		currentUser =sharedPreferences.getString("currentUser", ""); 
		Log.i("currentUser", ""+currentUser);
		
		//objId= intent.getStringExtra("num");
	    objId=(String) getIntent().getExtras().get("sss");
		Log.i("bojId", ""+objId);
		init();
		onQuery();


	}


	private void init() {
		amendInfoReturn=(TextView)findViewById(R.id.amendInfoReturn);
		amendInfoOK=(TextView)findViewById(R.id.amendInfoOK);
		deleteDate=(TextView)findViewById(R.id.deleteDate);
		mDayLayout=(LinearLayout)findViewById(R.id.mDayLayout);
		mDayLayout2=(LinearLayout)findViewById(R.id.mDayLayout2);
		mDayLayout3=(LinearLayout)findViewById(R.id.mDayLayout3);
		add_sex=(TextView)findViewById(R.id.add_sex);
		add_dateOfBirth=(TextView)findViewById(R.id.add_dateOfBirth);
		add_graduationDate=(TextView)findViewById(R.id.add_graduationDate);
		add_memorial=(TextView)findViewById(R.id.add_memorial);
		add_day=(TextView)findViewById(R.id.add_day);
		add_memorial2=(TextView)findViewById(R.id.add_memorial2);
		add_day2=(TextView)findViewById(R.id.add_day2);
		add_memorial3=(TextView)findViewById(R.id.add_memorial3);
		add_day3=(TextView)findViewById(R.id.add_day3);
		add_name=(EditText)findViewById(R.id.add_name);
		add_age=(EditText)findViewById(R.id.add_age);
		add_QQ=(EditText)findViewById(R.id.add_qq);
		add_phone=(EditText)findViewById(R.id.add_phone);
		add_email=(EditText)findViewById(R.id.add_email);
		add_hobby=(EditText)findViewById(R.id.add_hobby);
		add_province=(EditText)findViewById(R.id.add_province);
		add_contactAddress=(EditText)findViewById(R.id.add_contactAddress);
		add_maritalStatus=(EditText)findViewById(R.id.add_maritalStatus);
		add_diploma=(EditText)findViewById(R.id.add_diploma);
		add_major=(EditText)findViewById(R.id.add_major);
		add_userClass=(EditText)findViewById(R.id.add_class);
		add_school=(EditText)findViewById(R.id.add_school);
		add_profession=(EditText)findViewById(R.id.add_profession);
		add_companyName=(EditText)findViewById(R.id.add_companyName);
		add_companyAddress=(EditText)findViewById(R.id.add_companyAddress);
		add_department=(EditText)findViewById(R.id.add_department);
		add_yearsOfWorking=(EditText)findViewById(R.id.add_yearsOfWorking);
		
		amendInfoReturn.setOnClickListener(this);
		amendInfoOK.setOnClickListener(this);
		add_sex.setOnClickListener(this);
		add_dateOfBirth.setOnClickListener(this);
		add_graduationDate.setOnClickListener(this);
		mDayLayout.setOnClickListener(this);
		mDayLayout2.setOnClickListener(this);
		mDayLayout3.setOnClickListener(this);
	    deleteDate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

    switch (v.getId()) {
	case R.id.amendInfoReturn:
		Intent intent=new Intent();
		intent.setClass(AmendInfoActivity.this,PersonInfoActivity.class);
		AmendInfoActivity.this.startActivity(intent);
		finish();
		break;
	case R.id.add_sex:
		onCreateDialog();
		break;		
	case R.id.add_dateOfBirth:
		onCreateTimeDialog(4);		
		break;
	case R.id.add_graduationDate:
		onCreateTimeDialog(5);
		break;
	case R.id.mDayLayout:
		onCreatMemorialDayDialog(1);
		break;	
	case R.id.mDayLayout2:
		onCreatMemorialDayDialog(2);
		break;
	case R.id.mDayLayout3:
		onCreatMemorialDayDialog(3);
		break;
	case R.id.amendInfoOK:
		onSubmitInfo();
		break;
	case R.id.deleteDate:
		add_graduationDate.setText(null);
		break;
	default:
		break;
	}
		
	}

    /*
     * ��ѯ�û���Ϣ����ӵ��ؼ���
     */
	private void onQuery() {
		final ProgressDialog progress = new ProgressDialog(AmendInfoActivity.this);
		progress.setMessage("�����û���Ϣ��...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
		query.getObject(this,objId, new GetListener<UserInfo>() {

			//���ҳɹ����������ؼ��������
		    @Override
		    public void onSuccess(UserInfo info) {
		    if(!info.getObjectId().equals(null)){
		    add_name.setText(info.getName());
		    add_sex.setText(info.getSex()); 
		    add_age.setText(info.getAge()); 
		    add_QQ.setText(info.getQQ());
		    add_phone.setText(info.getPhone());
		    add_email.setText(info.getEmail());
		    add_hobby.setText(info.getHobby());
		    add_province.setText(info.getProvince());
		    add_dateOfBirth.setText(info.getDateOfBirth());
		    add_contactAddress.setText(info.getContactAddress());
		    add_maritalStatus.setText(info.getMaritalStatus());
		    add_memorial.setText(info.getMemorial());
		    add_day.setText(info.getDay());
		    add_memorial2.setText(info.getMemorial2());
		    add_day2.setText(info.getDay2());
		    add_memorial3.setText(info.getMemorial3());
		    add_day3.setText(info.getDay3());
		    add_diploma.setText(info.getDiploma());
		    add_major.setText(info.getMajor());
		    add_userClass.setText(info.getUserClass());
		    add_school.setText(info.getSchool());
		    add_graduationDate.setText(info.getGraduationDate());
		    add_profession.setText(info.getProfession());
		    add_companyName.setText(info.getCompanyName());
		    add_companyAddress.setText(info.getCompanyAddress());
		    add_department.setText(info.getDepartment());
		    add_yearsOfWorking.setText(info.getYearsOfWorking());
		    progress.dismiss();
		 Toast.makeText(AmendInfoActivity.this, "ƥ���û���Ϣ�ɹ���", Toast.LENGTH_LONG).show();	
  	
		    }
 
		    }

		@Override
		public void onFailure(int code, String arg0) {
		progress.dismiss();  
	    Toast.makeText(AmendInfoActivity.this, "δ�ҵ��û���Ϣ���������������Ϣ�����ڣ�", Toast.LENGTH_LONG).show();	 
		    
		    }

		});
		
	}

	/**
	 * �ύ�û���Ϣ
	 */
	private void onSubmitInfo() {
//		if (TextUtils.isEmpty(add_name.getText().toString())) {
//			Toast.makeText(AmendInfoActivity.this, "������Ϣδ��д������", Toast.LENGTH_SHORT).show();	
//			return;
//		}
		
		if(add_name.getText().toString().trim().equals("")||
		   add_sex.getText().toString().equals("")||
		   add_age.getText().toString().equals("")||
		   add_QQ.getText().toString().equals("") ||
		   add_phone.getText().toString().equals("")||
		   add_email.getText().toString().equals("")||
		   add_hobby.getText().toString().equals("")||
		   add_province.getText().toString().equals("")||
		   add_dateOfBirth.getText().toString().equals("")||
		   add_contactAddress.getText().toString().equals("")||
		   add_maritalStatus.getText().toString().equals("")
				)
		{
		Toast.makeText(AmendInfoActivity.this, "������Ϣδ��д������", Toast.LENGTH_SHORT).show();				
		
		}
		else{
	 final ProgressDialog progress = new ProgressDialog(AmendInfoActivity.this);
	 progress.setMessage("������Ϣ��...");
	 progress.setCanceledOnTouchOutside(false);
	 progress.show();	

     UserInfo uInfo= new UserInfo();	
	 uInfo.setUserId(currentUser);
	 uInfo.setName(add_name.getText().toString());
	 uInfo.setSex(add_sex.getText().toString());
	 uInfo.setAge(add_age.getText().toString());
	 uInfo.setQQ(add_QQ.getText().toString());
	 uInfo.setPhone(add_phone.getText().toString());
	 uInfo.setEmail(add_email.getText().toString());
	 uInfo.setHobby(add_hobby.getText().toString());
	 uInfo.setProvince(add_province.getText().toString());
	 uInfo.setDateOfBirth(add_dateOfBirth.getText().toString());
	 uInfo.setContactAddress(add_contactAddress.getText().toString());
	 uInfo.setMaritalStatus(add_maritalStatus.getText().toString());
	 uInfo.setMemorial(add_memorial.getText().toString());
	 uInfo.setDay(add_day.getText().toString());
	 uInfo.setMemorial2(add_memorial2.getText().toString());
	 uInfo.setDay2(add_day2.getText().toString());
	 uInfo.setMemorial3(add_memorial3.getText().toString());
	 uInfo.setDay3(add_day3.getText().toString());
	 uInfo.setDiploma(add_diploma.getText().toString());
	 uInfo.setMajor(add_major.getText().toString());
	 uInfo.setUserClass(add_userClass.getText().toString());
	 uInfo.setSchool(add_school.getText().toString());
	 uInfo.setGraduationDate(add_graduationDate.getText().toString());
	 uInfo.setProfession(add_profession.getText().toString());
	 uInfo.setCompanyName(add_companyName.getText().toString());
	 uInfo.setCompanyAddress(add_companyAddress.getText().toString());
	 uInfo.setDepartment(add_department.getText().toString());
	 uInfo.setYearsOfWorking(add_yearsOfWorking.getText().toString());
				
	     if(objId.equals("success")){		 


		 uInfo.save(AmendInfoActivity.this, new SaveListener() {

			@Override
			public void onFailure(int arg0, String arg1) {
			progress.dismiss();	
			Toast.makeText(AmendInfoActivity.this, "�������ʧ�ܣ�"+arg1, Toast.LENGTH_SHORT).show();		
			
			}

			@Override
			public void onSuccess() {
	
			progress.dismiss();
			getobjectId();
			Toast.makeText(AmendInfoActivity.this, "������ӳɹ���"+currentUser, Toast.LENGTH_SHORT).show();	
			mark=1;
			addInfo();
			}
		   });
	      }else if(!objId.equals(null)&&!objId.equals("success")&&!objId.equals("error")){

	    	 uInfo.update(AmendInfoActivity.this,objId, new UpdateListener() {
	    	 @Override
	    	 public void onSuccess() {
	    	 progress.dismiss();
	    	 Toast.makeText(AmendInfoActivity.this, "���ݸ��³ɹ���", Toast.LENGTH_SHORT).show();         
	    	 mark=2;
	    	 addInfo();
	    	  }
	    	  @Override
	    	  public void onFailure(int code, String msg) {
	    	progress.dismiss();
	    	Toast.makeText(AmendInfoActivity.this, "���ݸ���ʧ�ܣ�"+msg, Toast.LENGTH_LONG).show();         
	    	      }
	    	  });

	    	 }else if (objId.equals("error")){
	    Toast.makeText(AmendInfoActivity.this, "��ѯ�û���Ϣʧ�ܣ��鿴�����Ƿ����ӣ�", Toast.LENGTH_LONG).show();	 
	    	 }
		}		
	}
	
	/**
	 * ������Ϣ�����浽�������ݿ⣨sqList��
	 */
	private void addInfo() {
		UserInfo uInfo= new UserInfo();	
		DBAccess access= new DBAccess(this);
		 uInfo.setUserId(currentUser);
		 uInfo.setName(add_name.getText().toString());
		 uInfo.setSex(add_sex.getText().toString());
		 uInfo.setAge(add_age.getText().toString());
		 uInfo.setQQ(add_QQ.getText().toString());
		 uInfo.setPhone(add_phone.getText().toString());
		 uInfo.setEmail(add_email.getText().toString());
		 uInfo.setHobby(add_hobby.getText().toString());
		 uInfo.setProvince(add_province.getText().toString());
		 uInfo.setDateOfBirth(add_dateOfBirth.getText().toString());
		 uInfo.setContactAddress(add_contactAddress.getText().toString());
		 uInfo.setMaritalStatus(add_maritalStatus.getText().toString());
		 uInfo.setMemorial(add_memorial.getText().toString());
		 uInfo.setDay(add_day.getText().toString());
		 uInfo.setMemorial2(add_memorial2.getText().toString());
		 uInfo.setDay2(add_day2.getText().toString());
		 uInfo.setMemorial3(add_memorial3.getText().toString());
		 uInfo.setDay3(add_day3.getText().toString());
		 uInfo.setDiploma(add_diploma.getText().toString());
		 uInfo.setMajor(add_major.getText().toString());
		 uInfo.setUserClass(add_userClass.getText().toString());
		 uInfo.setSchool(add_school.getText().toString());
		 uInfo.setGraduationDate(add_graduationDate.getText().toString());
		 uInfo.setProfession(add_profession.getText().toString());
		 uInfo.setCompanyName(add_companyName.getText().toString());
		 uInfo.setCompanyAddress(add_companyAddress.getText().toString());
		 uInfo.setDepartment(add_department.getText().toString());
		 uInfo.setYearsOfWorking(add_yearsOfWorking.getText().toString());
		 Log.i("mark", ""+mark);
		switch (mark) {
		case 1:		 
			access.insert(uInfo); 	
			break;
         case 2:
     		 list=access.queryInfo(ConstantValue.DB_MetaData.USER_ID,currentUser);
        	 if(list.size()==0){
             access.insert(uInfo); 		 
        	 }else{
        	 access.update(uInfo);
        	 }
			break;
		default:
			break;
		}		
		
	}
	
	/**
	 * ����������ѡ���
	 */
  private void onCreatMemorialDayDialog(final int mDay) {
	  AlertDialog.Builder builder = new Builder(AmendInfoActivity.this);
	  builder.setTitle("ѡ������գ�����Ĭ��Ϊ������");	
	  builder.setItems(memorial,new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			//which==memorial.length-1   ɾ����ǰ������
			 if(mDay==1 && which!=memorial.length-1){				
				onCreateTimeDialog(1);
				add_memorial.setText(memorial[which]);
				//add_day.setText(time);
				}else if(mDay==1 && which==memorial.length-1){
				add_memorial.setText("");
				add_day.setText("");	
				}else if(mDay==2 && which!=memorial.length-1){
				onCreateTimeDialog(2);	
				add_memorial2.setText(memorial[which]);
				//add_day2.setText(time);
				}else if(mDay==2 && which==memorial.length-1){
				add_memorial2.setText("");
				add_day2.setText("");	
				}else if(mDay==3 && which!=memorial.length-1){
				onCreateTimeDialog(3);
				add_memorial3.setText(memorial[which]);
				//add_day3.setText(time);
				}else if(mDay==3 && which==memorial.length-1){
				add_memorial3.setText("");
				add_day3.setText("");	
				}
			}	
			}).show();
	}

/**
  * ������Ůѡ��ѡ���
  * @return
  */
	private Dialog onCreateDialog() {
		AlertDialog.Builder builder = new Builder(AmendInfoActivity.this);
		String[] sex={"��","Ů"};
		builder.setTitle("sex");
		builder.setItems(sex,new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
		if(which==0){
			add_sex.setText("��");
			
		}else if(which==1){
			add_sex.setText("Ů");
		}
		}	
		}).show();
		return null;
	}
	
	/**
	 * ������������ѡ���
	 * which����¼�ĸ��ؼ������
	 */
	private void onCreateTimeDialog(final int which) {
		Calendar c = Calendar.getInstance();	 
		// ֱ�Ӵ���һ��DatePickerDialog�Ի���ʵ������������ʾ����
		Dialog dateDialog = new DatePickerDialog(AmendInfoActivity.this,
			//�󶨼����¼�	
			new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker dp, int year, int month,
			int dayOfMonth) {		
			if(which==4||which==5){
			//�����洢����
			String time=year + "-" + (month + 1) + "-"+ dayOfMonth;
			//�������õ�����	
			if(which==4){
			add_dateOfBirth.setText(time);	
			}else if(which==5){
			add_graduationDate.setText(time);	
			}
			}else if(which==1||which==2||which==3){
			String time=(month + 1) + "-"+ dayOfMonth;	
			//�������õļ�����ʱ��
			 if(which==1){
				 add_day.setText(time);
			 }else if(which==2){
				 add_day2.setText(time);
			 }else if(which==3){
				 add_day3.setText(time);
			}
			}
            }
			
		}// ���ó�ʼ����
		, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
		c.get(Calendar.DAY_OF_MONTH));
		dateDialog.setTitle("��ѡ������");
		dateDialog.show();
		
	}
 
	/*
	 * ��ȡobjectId,�ж��û��Ƿ񱣴����Ϣ
	 */
	private void getobjectId() {
		
				BmobQuery<UserInfo> query=new BmobQuery<UserInfo>();
				query.addWhereEqualTo("userId",currentUser);
				query.findObjects(AmendInfoActivity.this, new FindListener<UserInfo>() {

				@Override
				public void onError(int arg0, String arg1) {
				objId="error";
				}
				
				@Override
				public void onSuccess(List<UserInfo> value) {
				if(value.size()==1){
				for (UserInfo uinfo : value) {						
				objId=uinfo.getObjectId();
				} 
			//	Toast.makeText(AmendInfoActivity.this, "��ѯ�ɹ���"+objId+currentUser, Toast.LENGTH_LONG).show();			 
				}else{
				objId="success"; 
			//	Toast.makeText(AmendInfoActivity.this, "��ѯ�ɹ���"+objId+currentUser, Toast.LENGTH_LONG).show();
				}
				}
				});	

	}



	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.amend_info, menu);
		return true;
	}
}
