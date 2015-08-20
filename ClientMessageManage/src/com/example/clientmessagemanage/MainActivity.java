package com.example.clientmessagemanage;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
			       WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		final Intent intent =new Intent();
		Timer timer= new Timer();
		TimerTask task=new TimerTask()
		{
			@Override
			public void run() {
		    // TODO Auto-generated method stub
			intent.setClass(MainActivity.this,LogInActivity.class);
	        MainActivity.this.startActivity(intent);
	        finish();
			}	
		};
		timer.schedule(task, 0000);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
