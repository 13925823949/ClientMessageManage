package com.example.broadsideUI;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.example.bean.UserInfo;
import com.example.clientmessagemanage.R;
import com.example.clientmessagemanage.XMBActivity;
import com.example.util.ConstantValue;
import com.example.util.DBAccess;


import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PersonInfoActivity extends Activity implements OnClickListener{

	private Button amendInfo_button;
	private TextView personInfoReturn;
	private String objId;
	private String currentUser;
	private TextView welcomes_text;
	private List<UserInfo> list;
	private TextView personal_info;
	private TextView education_info;
	private TextView profession_info;
	
	private TextView show_add_friend_info;
	private PersonalInfoFragment personalInfoFragment;
	private EducationInfoFragment educationInfoFragment;
	private ProfessionInfoFragment professionInfoFragment;
	/**
	 * ���ڶ�Fragment���й���
	 */
	private FragmentManager fragmentManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.broadside_person_info);
		
		getobjectId();		
		init();
//		show_add_friend_info.setText((String)
//				getIntent().getExtras().getString("int"));
	
		fragmentManager = getFragmentManager();
		// ��һ������ʱѡ�е�0��tab
		setTabSelection3(0);
	}
    //��fragment��ֵuserId


	private void init() {
		personInfoReturn=(TextView)findViewById(R.id.personInfoReturn);
		amendInfo_button=(Button)findViewById(R.id.amendInfo_button);
		welcomes_text=(TextView)findViewById(R.id.welcomes_text);
		personal_info=(TextView)findViewById(R.id.personal_info);
		education_info=(TextView)findViewById(R.id.education_info);
		profession_info=(TextView)findViewById(R.id.profession_info);
		
		show_add_friend_info=(TextView)findViewById(R.id.show_add_friend_info);
		//DBAccess access=new DBAccess(this);
		//access.equals(userId);
		//List<UserInfo> infoList=new ArrayList<UserInfo>(); 
		//UserInfo name=infoList.get(2);
		
		//��ӭ��ǩ
	    File file = getExternalCacheDir(); 
	    if(file.equals(null)){
	    welcomes_text.setText("�װ���"+currentUser+",���ã�");	
	    }else{
		DBAccess access=new DBAccess(this);
		list=access.queryInfo(ConstantValue.DB_MetaData.USER_ID,currentUser);		
	    if(list.size()==0){
	    welcomes_text.setText("�װ���"+currentUser+",���ã�");	
	    }else{
	    String name=(String)list.get(0).getName();
	    welcomes_text.setText("�װ���"+name+",���ã�");	
	    }
	    }
		
		personInfoReturn.setOnClickListener(this);
		amendInfo_button.setOnClickListener(this);
		personal_info.setOnClickListener(this);
		education_info.setOnClickListener(this);
		profession_info.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.personInfoReturn:
			Intent intent=new Intent();
			intent.setClass(PersonInfoActivity.this, XMBActivity.class);
			PersonInfoActivity.this.startActivity(intent);
			finish();
			break;
		case R.id.amendInfo_button:
			Bundle bundle = new Bundle();             
			bundle.putString("sss", objId);                
			Intent intent2=new Intent();
			intent2.putExtras(bundle);
			intent2.setClass(PersonInfoActivity.this, AmendInfoActivity.class);
			PersonInfoActivity.this.startActivity(intent2);
			break;
		case R.id.personal_info:
			setTabSelection3(0);
			break;
		case R.id.education_info:
			setTabSelection3(1);
			break;
		case R.id.profession_info:
			setTabSelection3(2);
			break;
		default:
			break;
		}
		
	}
	private void setTabSelection3(int index3) {
		//���֮ǰѡ����״̬
		clearSelection();
	   //����һ��fragment
	   FragmentTransaction transaction3 = fragmentManager.beginTransaction();
		// �����ص����е�Fragment,�Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
		hideFragments(transaction3);
		
		switch (index3) {
		case 0:
			
			// �����������tabʱ���ı�ؼ���������ɫ
			personal_info.setTextColor(Color.WHITE);
			if (personalInfoFragment == null) {
				// ���PersonalInfoFragmentΪ�գ��򴴽�һ������ӵ�������
				personalInfoFragment = new PersonalInfoFragment();
				transaction3.add(R.id.content3, personalInfoFragment);
			} else {
				// ���PersonalInfoFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction3.show(personalInfoFragment);
			}	
			break;
		case 1:
			
			// �����������tabʱ���ı�ؼ���������ɫ
			education_info.setTextColor(Color.WHITE);
			if (educationInfoFragment == null) {
				educationInfoFragment = new EducationInfoFragment();
				transaction3.add(R.id.content3, educationInfoFragment);
			} else {
				transaction3.show(educationInfoFragment);
			}	
			break;
		case 2:
				
			// �����������tabʱ���ı�ؼ���������ɫ
			profession_info.setTextColor(Color.WHITE);
			if (professionInfoFragment == null) {
				professionInfoFragment = new ProfessionInfoFragment();
				transaction3.add(R.id.content3, professionInfoFragment);
			} else {
				transaction3.show(professionInfoFragment);
			}	
			break;
		default:
			break;
		}
		transaction3.commit();
	}

	/**
	 * ��������е�ѡ��״̬��
	 */
	private void clearSelection() {

		personal_info.setTextColor(Color.parseColor("#2B2B2B"));
		education_info.setTextColor(Color.parseColor("#2B2B2B"));
		profession_info.setTextColor(Color.parseColor("#2B2B2B"));
	}
	/**
	 * �����е�Fragment����Ϊ����״̬��
	 * 
	 * @param transaction
	 *  ���ڶ�Fragmentִ�в���������
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (personalInfoFragment != null) {
			transaction.hide(personalInfoFragment);
		}
		if (educationInfoFragment != null) {
			transaction.hide(educationInfoFragment);
		}
		if (professionInfoFragment != null) {
			transaction.hide(professionInfoFragment);
		}

	}
	
	/*
	 * ��ȡobjectId,�ж��û��Ƿ񱣴����Ϣ
	 */
	private void getobjectId() {
		//�ڶ�ȡSharedPreferences����ǰҪʵ������һ��SharedPreferences���� 
		SharedPreferences sharedPreferences= getSharedPreferences("test", 
		Activity.MODE_PRIVATE); 
		// ʹ��getString�������value��ע���2��������value��Ĭ��ֵ 
		currentUser =sharedPreferences.getString("currentUser", ""); 
		Log.i("userId", ""+currentUser);
		
				BmobQuery<UserInfo> query=new BmobQuery<UserInfo>();
				query.addWhereEqualTo("userId",currentUser);
				query.findObjects(PersonInfoActivity.this, new FindListener<UserInfo>() {

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
				Toast.makeText(PersonInfoActivity.this, "��ѯ�ɹ���"+objId+currentUser, Toast.LENGTH_LONG).show();			 
				}else{
				objId="success"; 
				Toast.makeText(PersonInfoActivity.this, "��ѯ�ɹ���"+objId+currentUser, Toast.LENGTH_LONG).show();
				}
				}
				});	

	}
				
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.person_info, menu);
		return true;
	}

}
