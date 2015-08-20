package com.example.broadsideUI;

import com.example.clientmessagemanage.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class SeeInfoActivity extends Activity implements OnClickListener{

	
	private PersonalInfoFragment personalInfoFragment;
	private EducationInfoFragment educationInfoFragment;
	private ProfessionInfoFragment professionInfoFragment;
	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;
	private TextView see_personal_info;
	private TextView see_education_info;
	private TextView see_profession_info;
	private TextView see_name_text;
	private String see_userId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.broadside_see_info);
		
		
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		setSee_userId(bundle.getString("id"));
		Log.i("setSee_userId1", ""+getSee_userId());
		init();	
		if(bundle.getString("name")==null){
		see_name_text.setText("未填写姓名");	
		}else{
		see_name_text.setText(bundle.getString("name"));
		}
		

		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection4(0);
	}


//	/**
//	 * 实现接口获取PrivateFragment传递的值
//	 */
//	@Override
//	public void myOnListClick(String id) {
//		see_userId=id;		
//	}
	

	public String getSee_userId() {
		return see_userId;
	}


	public void setSee_userId(String see_userId) {
		this.see_userId = see_userId;
	}


	private void init() {

		see_personal_info=(TextView)findViewById(R.id.see_personal_info);
		see_education_info=(TextView)findViewById(R.id.see_education_info);
		see_profession_info=(TextView)findViewById(R.id.see_profession_info);
		see_name_text=(TextView)findViewById(R.id.see_name_text);
		
		see_personal_info.setOnClickListener(this);
		see_education_info.setOnClickListener(this);
		see_profession_info.setOnClickListener(this);
		
	}
	
	
	@Override
	public void onClick(View v) {
	switch(v.getId()){
	case R.id.see_personal_info:
		setTabSelection4(0);
		break;
	case R.id.see_education_info:
		setTabSelection4(1);
		break;
	case R.id.see_profession_info:
		setTabSelection4(2);
		break;
	default:
		break;
	}
		
	}
	private void setTabSelection4(int index4) {
		//清除之前选定的状态
				clearSelection();
			   //开启一个fragment
			   FragmentTransaction transaction4 = fragmentManager.beginTransaction();
				// 先隐藏掉所有的Fragment,以防止有多个Fragment显示在界面上的情况
				hideFragments(transaction4);
				switch (index4) {
				case 0:
					// 当点击了人脉tab时，改变控件的文字颜色
					see_personal_info.setTextColor(Color.WHITE);
					if (personalInfoFragment == null) {
						// 如果PersonalInfoFragment为空，则创建一个并添加到界面上
						personalInfoFragment = new PersonalInfoFragment();
						transaction4.add(R.id.content4, personalInfoFragment);
					} else {
						// 如果PersonalInfoFragment不为空，则直接将它显示出来
						transaction4.show(personalInfoFragment);
					}	
					break;
				case 1:
					// 当点击了人脉tab时，改变控件的文字颜色
					see_education_info.setTextColor(Color.WHITE);
					if (educationInfoFragment == null) {
						educationInfoFragment = new EducationInfoFragment();
						transaction4.add(R.id.content4, educationInfoFragment);
					} else {
						transaction4.show(educationInfoFragment);
					}	
					break;
				case 2:
					// 当点击了人脉tab时，改变控件的文字颜色
					see_profession_info.setTextColor(Color.WHITE);
					if (professionInfoFragment == null) {
						professionInfoFragment = new ProfessionInfoFragment();
						transaction4.add(R.id.content4, professionInfoFragment);
					} else {
						transaction4.show(professionInfoFragment);
					}	
					break;
				default:
					break;
				}
				transaction4.commit();	
	}
	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {

		see_personal_info.setTextColor(Color.parseColor("#2B2B2B"));
		see_education_info.setTextColor(Color.parseColor("#2B2B2B"));
		see_profession_info.setTextColor(Color.parseColor("#2B2B2B"));
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.see_info, menu);
		return true;
	}



}
