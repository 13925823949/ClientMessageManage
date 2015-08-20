package com.example.clientmessagemanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.FindListener;

import com.example.bean.ContactPerson;
import com.example.bean.Contacts;
import com.example.bean.UserID;
import com.example.bean.UserInfo;
import com.example.util.CommonUtils;
import com.example.util.DBAccess;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
    
	private TextView registerReturn;
	private EditText rsrUserId;
	private EditText rsrPassword;
	private EditText rsrPassword2;
	private Button register;
	private EditText rsrName;
	private UserID user=new UserID();
	
	private ContactPerson cp =new ContactPerson();
	private boolean queryId=false;//判定Id是否被注册，false不可注册，true为可注册

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
	
				
		init();
	}

	private void init() {
		registerReturn=(TextView)findViewById(R.id.registerReturn);
		rsrUserId=(EditText)findViewById(R.id.rsrUserId);
		rsrPassword=(EditText)findViewById(R.id.rsrPassword);
		rsrPassword2=(EditText)findViewById(R.id.rsrPassword2);
		register=(Button)findViewById(R.id.register);
		rsrName=(EditText)findViewById(R.id.rsrName);
		
		rsrUserId.setOnFocusChangeListener(new OnFocusChangeListener(){
				@Override
				public void onFocusChange(View v, boolean hasFocus) {				
				if(!hasFocus){					
					queryUserId();
				}
				}
		});
		
		
		register.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				registerResult();
			}			
		});
		
		registerReturn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent =new Intent();
				intent.setClass(RegisterActivity.this,LogInActivity.class);
				RegisterActivity.this.startActivity(intent);	
				finish();
			}	
		});	
		
		
	}

	private void registerResult() {	
		if (TextUtils.isEmpty(rsrUserId.getText().toString())) {
			Toast.makeText(RegisterActivity.this, "账号不能为空！", Toast.LENGTH_SHORT).show();	
			return;
		}
		if (TextUtils.isEmpty( rsrPassword.getText().toString())) {
			Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();	
			return;
		}
		if (!rsrPassword.getText().toString().equals(rsrPassword2.getText().toString())) {
			Toast.makeText(RegisterActivity.this, "两次输入密码不匹配！", Toast.LENGTH_SHORT).show();	
			return;
		}
		if (TextUtils.isEmpty( rsrName.getText().toString())) {
			Toast.makeText(RegisterActivity.this, "名字不能为空！", Toast.LENGTH_SHORT).show();	
			return;
		}
		boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
		if(!isNetConnected){
			Toast.makeText(RegisterActivity.this, "当前网络不可用，请检查你当前网络！", Toast.LENGTH_SHORT).show();	
			return;
		}
		
		queryUserId();
		if(queryId==true){
		final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
		progress.setMessage("正在注册...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		
        user.setUserId(rsrUserId.getText().toString());
		user.setUserpwd(rsrPassword.getText().toString());
		
		user.save(RegisterActivity.this, new SaveListener() {
			
		    @Override
		    public void onSuccess() {
		    progress.dismiss();
		    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
		    UserInfo info=new UserInfo();
		    info.setName(rsrName.getText().toString());
		    info.setUserId(rsrUserId.getText().toString());
		    info.save(RegisterActivity.this, new SaveListener() {
				
				@Override
				public void onSuccess() {
				Log.i("onSuccess", "添加名字成功！");
				}				
				@Override
				public void onFailure(int arg0, String arg1) {
                Log.i("onFailure", "添加名字失败！");
				}
			});
		    
		    addGroupThread agt= new addGroupThread();
		    agt.start();

			final Intent intent =new Intent();
			Timer timer= new Timer();
			TimerTask task=new TimerTask()
			{
				@Override
				public void run() {
				String registerId = rsrUserId.getText().toString();
				intent.putExtra("num1", registerId);  
				intent.setClass(RegisterActivity.this,LogInActivity.class);
				RegisterActivity.this.startActivity(intent);
		        finish();
				}	
			};
			timer.schedule(task, 2000);	
		
		    }

		    @Override
		    public void onFailure(int code, String msg) {
		    Toast.makeText(RegisterActivity.this, "注册失败！"+msg, Toast.LENGTH_LONG).show();
		    }
		});
		}
	}
	
	private void queryUserId() {
		String uId=rsrUserId.getText().toString();
		String rpwd=rsrPassword.getText().toString();
        user.setUserId(uId);
	    user.setUserpwd(rpwd);
		final BmobQuery<UserID> query=new BmobQuery<UserID>();		
		query.addWhereEqualTo("userId", rsrUserId.getText().toString());
		query.findObjects(RegisterActivity.this, new FindListener<UserID>() {

		@Override
		public void onError(int arg0, String arg1) {
			queryId=false;
			}
        
		//value.size()查询结果的个数
		@Override
		public void onSuccess(List<UserID> value) {
						
		if(value == null || value.size() == 0){
		Toast.makeText(RegisterActivity.this, "此ID可以注册！", Toast.LENGTH_SHORT).show();	
		queryId=true;
		}else{
		queryId=false;
			Toast.makeText(RegisterActivity.this, "此ID已存在！并存在"+value.size()+"个。", Toast.LENGTH_SHORT).show();
		}	
		}
		});
		
	}

	
	class addGroupThread extends Thread{
		
		@Override
		public void run() {
			final Contacts c=new Contacts();	
			c.setUserId(rsrUserId.getText().toString());
			//创建默认分组
			ArrayList<String> list=new ArrayList<String>();
			list.add("未分组");
			list.add("特别关注");
			list.add("家人");
			list.add("亲戚");
			list.add("朋友");
			list.add("同学");
			list.add("其他");
			ArrayList<String> list2=new ArrayList<String>();
			list2.add("未分组");
			list2.add("特别关注");
			list2.add("朋友");
			list2.add("同学");
			list2.add("同事");
			list2.add("公司老板");
			list2.add("其他");
			
			try {
				cp.setPrivateGroups(list);
				cp.setClientGroups(list2);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			c.setPrivateGroup(list);
			c.setClientGroup(list2);
			c.save(RegisterActivity.this,new SaveListener() {

				@Override
				public void onSuccess() {
				Log.i("onSuccess", "添加组名成功！"+rsrUserId.getText().toString()+c.getObjectId());						
				
				 //云服务保存成功后再同步保存到本地
				 cp.setObjectIds(c.getObjectId());
				 cp.setUserId(rsrUserId.getText().toString());
				 DBAccess ac=new DBAccess(RegisterActivity.this);
				 ac.insertTable3(cp);
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
				Log.i("onFailure", "添加组名失败！"+arg0+arg1);		
					
				}
			});			
		}
	}
	
}
