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
	 * 用于对Fragment进行管理
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
		// 第一次启动时选中第0个tab
		setTabSelection3(0);
	}
    //向fragment传值userId


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
		
		//欢迎标签
	    File file = getExternalCacheDir(); 
	    if(file.equals(null)){
	    welcomes_text.setText("亲爱的"+currentUser+",您好！");	
	    }else{
		DBAccess access=new DBAccess(this);
		list=access.queryInfo(ConstantValue.DB_MetaData.USER_ID,currentUser);		
	    if(list.size()==0){
	    welcomes_text.setText("亲爱的"+currentUser+",您好！");	
	    }else{
	    String name=(String)list.get(0).getName();
	    welcomes_text.setText("亲爱的"+name+",您好！");	
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
		//清除之前选定的状态
		clearSelection();
	   //开启一个fragment
	   FragmentTransaction transaction3 = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment,以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction3);
		
		switch (index3) {
		case 0:
			
			// 当点击了人脉tab时，改变控件的文字颜色
			personal_info.setTextColor(Color.WHITE);
			if (personalInfoFragment == null) {
				// 如果PersonalInfoFragment为空，则创建一个并添加到界面上
				personalInfoFragment = new PersonalInfoFragment();
				transaction3.add(R.id.content3, personalInfoFragment);
			} else {
				// 如果PersonalInfoFragment不为空，则直接将它显示出来
				transaction3.show(personalInfoFragment);
			}	
			break;
		case 1:
			
			// 当点击了人脉tab时，改变控件的文字颜色
			education_info.setTextColor(Color.WHITE);
			if (educationInfoFragment == null) {
				educationInfoFragment = new EducationInfoFragment();
				transaction3.add(R.id.content3, educationInfoFragment);
			} else {
				transaction3.show(educationInfoFragment);
			}	
			break;
		case 2:
				
			// 当点击了人脉tab时，改变控件的文字颜色
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
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {

		personal_info.setTextColor(Color.parseColor("#2B2B2B"));
		education_info.setTextColor(Color.parseColor("#2B2B2B"));
		profession_info.setTextColor(Color.parseColor("#2B2B2B"));
	}
	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *  用于对Fragment执行操作的事务
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
	 * 获取objectId,判断用户是否保存过信息
	 */
	private void getobjectId() {
		//在读取SharedPreferences数据前要实例化出一个SharedPreferences对象 
		SharedPreferences sharedPreferences= getSharedPreferences("test", 
		Activity.MODE_PRIVATE); 
		// 使用getString方法获得value，注意第2个参数是value的默认值 
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
				Toast.makeText(PersonInfoActivity.this, "查询成功！"+objId+currentUser, Toast.LENGTH_LONG).show();			 
				}else{
				objId="success"; 
				Toast.makeText(PersonInfoActivity.this, "查询成功！"+objId+currentUser, Toast.LENGTH_LONG).show();
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
