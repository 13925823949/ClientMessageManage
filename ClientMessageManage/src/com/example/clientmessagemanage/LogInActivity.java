package com.example.clientmessagemanage;

import java.util.List;

import com.example.bean.UserID;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends Activity{
	
	private TextView register;
	private Button logInButton;
	private EditText userId;
	private EditText userPwd;

	public static String APPID = "8db0cbd16d6122dca1ac6f9e0aa7567d"; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_log_in);
		
       // ��ʼ�� Bmob SDK
       // ʹ��ʱ�뽫�ڶ�������Application ID�滻������Bmob�������˴�����Application ID
       Bmob.initialize(this, APPID);       

		init();
	    
	}


	private void init() {
		register=(TextView)findViewById(R.id.register);
		logInButton=(Button)findViewById(R.id.logInButton);
		userId=(EditText)findViewById(R.id.userId);
		userPwd=(EditText)findViewById(R.id.password);
		
		Intent intent = getIntent();  
	    String Id = intent.getStringExtra("num1");
	    userId.setText(Id);
		
		register.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent =new Intent();
			intent.setClass(LogInActivity.this, RegisterActivity.class);
			LogInActivity.this.startActivity(intent);
			finish();
			}	
		});
		
		logInButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (TextUtils.isEmpty(userId.getText().toString())) {
					Toast.makeText(LogInActivity.this, "�˺Ų���Ϊ�գ�", Toast.LENGTH_SHORT).show();	
					return;
				}
				if (TextUtils.isEmpty( userPwd.getText().toString())) {
					Toast.makeText(LogInActivity.this, "���벻��Ϊ�գ�", Toast.LENGTH_SHORT).show();	
					return;
				}
				


				
				final ProgressDialog progress = new ProgressDialog(LogInActivity.this);
				progress.setMessage("���ڵ�½...");
				progress.setCanceledOnTouchOutside(false);
				progress.show();
				UserID user=new UserID();
				user.setUserId(userId.getText().toString());
				user.setUserpwd(userPwd.getText().toString());
								
				BmobQuery<UserID> query=new BmobQuery<UserID>();		
				query.addWhereEqualTo("userId", userId.getText().toString());
				query.addWhereEqualTo("userpwd", userPwd.getText().toString());
				query.findObjects(LogInActivity.this, new FindListener<UserID>() {

				@Override
				public void onError(int arg0, String arg1) {
					
					}
		        
				//value.size()��ѯ����ĸ���
				@Override
				public void onSuccess(List<UserID> value) {				
				if(value == null || value.size() == 0){
				progress.dismiss();
				Toast.makeText(LogInActivity.this, "�˺Ż��������"+value.size(), Toast.LENGTH_SHORT).show();
				}else{
					//ʵ����SharedPreferences���󣨵�һ���� 
					SharedPreferences mySharedPreferences= getSharedPreferences("test", 
							Activity.MODE_PRIVATE); 
					//ʵ����SharedPreferences.Editor���󣨵ڶ����� 
					SharedPreferences.Editor editor = mySharedPreferences.edit(); 
					//��putString�ķ����������� 
					editor.putString("currentUser", userId.getText().toString()); 
					//�ύ��ǰ���� 
					editor.commit();					
				progress.dismiss();
				Intent intent =new Intent();
				intent.setClass(LogInActivity.this, XMBActivity.class);
				LogInActivity.this.startActivity(intent);
				
				}	
				}
				});				
			}
		
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}



}
