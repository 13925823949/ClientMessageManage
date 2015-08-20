package com.exaple.receiver;

import java.io.Serializable;

import com.example.broadsideUI.PersonInfoActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class MyMessageReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("bmob", "收到反馈消息 = "+intent);	
		
		// 获取推送消息
		int k=0;
		k+=1;
		//String message = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
		NotificationManager nm = (NotificationManager) 
				context.getSystemService(Context.NOTIFICATION_SERVICE);	
		Notification n = new Notification();  
	     n.tickerText = "收到反馈消息";  

	   // Intent intent = new Intent(context, PersonInfoActivity.class); 
	     Bundle bd = new Bundle();
	    // bd.putSerializable("key",(Serializable) tent);
	    // intent.putExtra("int", tent);
	      
	}

}
