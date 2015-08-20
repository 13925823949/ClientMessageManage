package com.example.clientmessagemanage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.example.ContactsUI.ContactsFragment;
import com.example.bean.SendRequest;
import com.example.broadsideUI.AddFriendActivity;
import com.example.broadsideUI.PersonInfoActivity;
import com.example.recommendUI.RecommendFragment;
import com.example.recommendUI.RecommendFragment.CallBackTips;
import com.example.util.DBAccess;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 * 
 * @author guolin
 */
public class XMBActivity extends Activity implements OnClickListener,CallBackTips{


			/**
			 * 用于展示人脉的Fragment
			 */
			private ContactsFragment contactsFragment;

			/**
			 * 用于展示提醒的Fragment
			 */
			private RemindFragment remindFragment;

			/**
			 * 用于展示推荐的Fragment
			 */
			private RecommendFragment recommendFragment;

			/**
			 * 人脉界面布局
			 */
			private View contactsLayout;

			/**
			 * 提醒界面布局
			 */
			private View remindLayout;

			/**
			 * 推荐界面布局
			 */
			private View recommendLayout;

			/**
			 * 在Tab布局上显示人脉标题的控件
			 */
			private TextView contactsText;

			/**
			 * 在Tab布局上显示提醒标题的控件
			 */
			private TextView remindText;

			/**
			 * 在Tab布局上显示推荐标题的控件
			 */
			private TextView recommendText;

			/**
			 * 用于对Fragment进行管理
			 */
			private FragmentManager fragmentManager;
			
			//侧滑菜单
			private ListView broadsideList;
			private String[] items={"个人资料","添加好友","清空表中数据"};

			private String passId;
			private int xmbMsg;
			List<Map<String, Object>> list2=new ArrayList<Map<String,Object>>();
			private ImageView msg_tips_img;
			private myThread th; 
			@Override
			public void onCreate(Bundle savedInstanceState) {
				requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题
				super.onCreate(savedInstanceState);
				setContentView(R.layout.xmb);
				// 初始化布局元素
				initViews();
				 th = new myThread();                
                 th.start(); 
                    
                 int setTab=0;
                 
                 try {
                	 Intent intent = getIntent(); 
                     Bundle data = intent.getExtras(); 
                     if(data.getInt("setTab")>0){
                     setTab = data.getInt("setTab");	 
                     } 	
				 } catch (Exception e) {
					e.printStackTrace();
				 }
                               
				fragmentManager = getFragmentManager();
				// 第一次启动时选中第0个tab
				setTabSelection(setTab);
			
			}


			/**
			 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
			 */
			private void initViews() {
				contactsLayout = findViewById(R.id.contacts_layout);
				remindLayout = findViewById(R.id.remind_layout);
				recommendLayout = findViewById(R.id.recommend_layout);
				contactsText = (TextView) findViewById(R.id.contacts_text);
				remindText = (TextView) findViewById(R.id.remind_text);
				recommendText = (TextView) findViewById(R.id.recommend_text);
				msg_tips_img=(ImageView)findViewById(R.id.msg_tips_img);
				
				RelativeLayout lin = (RelativeLayout)findViewById
						(R.id.menu_frame);
				broadsideList=(ListView)lin.findViewById(R.id.broadsideList);
				
			    //通过setAdapter构建一个ArrayAdapter将items与mListView"绑定" 
				broadsideList.setAdapter(new ArrayAdapter<String>(this,
						android.R.layout.simple_expandable_list_item_1,items));
				
				contactsLayout.setOnClickListener(this);
				remindLayout.setOnClickListener(this);
				recommendLayout.setOnClickListener(this);
				

				//点击菜单栏
				broadsideList.setVisibility(View.INVISIBLE);
				broadsideList.setOnItemClickListener(new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						 TextView tv = (TextView) view;
				        if(tv.getText().toString().equals("个人资料"))
		                {
		                    Intent intent = new Intent();
		                    intent.setClass(XMBActivity.this, PersonInfoActivity.class);
		                    XMBActivity.this.startActivity(intent);
		                }
		                if(tv.getText().toString().equals("添加好友"))
		                {
		                    Intent intent = new Intent();
		                    intent.setClass(XMBActivity.this, AddFriendActivity.class);
		                    XMBActivity.this.startActivity(intent);
		                    finish();
		                }
		                if(tv.getText().toString().equals("清空表中数据"))
		                {
                      DBAccess acs=new DBAccess(XMBActivity.this);
                      acs.deleteTableUserInfo();
		              Toast.makeText(XMBActivity.this, "已清空！", Toast.LENGTH_SHORT).show();	
		                }
					}
					
				});
				
				
			}

			//点击tab
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.contacts_layout:
					// 当点击了人脉tab时，选中第1个tab
					setTabSelection(0);
					break;
				case R.id.remind_layout:
					// 当点击了提醒tab时，选中第2个tab
					setTabSelection(1);
					break;
				case R.id.recommend_layout:
					// 当点击了推荐tab时，选中第3个tab
					setTabSelection(2);
					break;
				default:
					break;
				}
			}
			
			/**
			 * 根据传入的index参数来设置选中的tab页。
			 * 
			 * @param index
			 *            每个tab页对应的下标。0表示人脉，1表示提醒，2表示推荐。
			 */
			private void setTabSelection(int index) {
				// 每次选中之前先清楚掉上次的选中状态
				clearSelection();
				// 开启一个Fragment事务
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
				hideFragments(transaction);
				switch (index) {
				case 0:
					// 当点击了人脉tab时，改变控件的文字颜色
					contactsText.setTextColor(Color.WHITE);
					if (contactsFragment == null) {
						// 如果ContactsFragment为空，则创建一个并添加到界面上
						contactsFragment = new ContactsFragment();
						transaction.add(R.id.content, contactsFragment);
					} else {
						// 如果ContactsFragment不为空，则直接将它显示出来
						transaction.show(contactsFragment);
					}
					break;
				case 1:
					// 当点击了提醒tab时，改变控件的文字颜色
					remindText.setTextColor(Color.WHITE);
					if (remindFragment == null) {
						// 如果RemindFragment为空，则创建一个并添加到界面上
						remindFragment = new RemindFragment();
						transaction.add(R.id.content, remindFragment);
						
					} else {
						// 如果RemindFragment不为空，则直接将它显示出来
						transaction.show(remindFragment);
					}
					break;
				case 2:
				default:
					Bundle bundle = new Bundle();
					bundle.putInt("msg", xmbMsg);		
					
					// 当点击了推荐tab时，改变控件的文字颜色
					recommendText.setTextColor(Color.WHITE);
					if (recommendFragment == null) {
						// 如果RecommendFragment为空，则创建一个并添加到界面上
						recommendFragment = new RecommendFragment();						
						recommendFragment.setArguments(bundle);
						transaction.add(R.id.content, recommendFragment,"myTag");
					} else {
						// 如果RecommendFragment不为空，则直接将它显示出来
					//	recommendFragment.setArguments(bundle);
						transaction.show(recommendFragment);
					}
					break;
				}
		        
				transaction.commit();
			}

			/**
			 * 清除掉所有的选中状态。
			 */
			private void clearSelection() {

				contactsText.setTextColor(Color.parseColor("#82858b"));
				remindText.setTextColor(Color.parseColor("#82858b"));
				recommendText.setTextColor(Color.parseColor("#82858b"));
			}
			/**
			 * 将所有的Fragment都置为隐藏状态。
			 * 
			 * @param transaction
			 *            用于对Fragment执行操作的事务
			 */
			private void hideFragments(FragmentTransaction transaction) {
				if (contactsFragment != null) {
					transaction.hide(contactsFragment);
				}
				if (remindFragment != null) {
					transaction.hide(remindFragment);
				}
				if (recommendFragment != null) {
					transaction.hide(recommendFragment);
				}
			}
			
           

			
			
			
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xmb, menu);
		return true;
	}

	 Handler myHandler = new Handler(){
		 @Override        
		 public void handleMessage(Message msg) {
			 switch (msg.what) {             
			 case 2: 
				 try{                  
					msg_tips_img.setVisibility(View.VISIBLE);
					xmbMsg=msg.what;
	
					 }catch(Exception e){                    
						 e.printStackTrace();            
						 }               
				 break;        
			default:                
				break;          
					 }           
			 super.handleMessage(msg); 
			 }
		 
	 };
	

	
	class myThread extends Thread{ 
		@Override
		public void run() {             
			/**
		     * 每次到达主界面都会查询一次是否有添加好友的请求信息
		     */
					//在读取SharedPreferences数据前要实例化出一个SharedPreferences对象 
					SharedPreferences sharedPreferences= getSharedPreferences("test", 
					Activity.MODE_PRIVATE); 
					// 使用getString方法获得value，注意第2个参数是value的默认值 
					final String currentUser =sharedPreferences.getString("currentUser", ""); 
					
					//先分开查询
					BmobQuery<SendRequest> query1=new BmobQuery<SendRequest>();
					query1.addWhereEqualTo("requestObject", currentUser);
					BmobQuery<SendRequest> query2 = new BmobQuery<SendRequest>();
					query2.addWhereEqualTo("requestPerson", currentUser);
					//查询结果放入数组再次or（或）查询
					List<BmobQuery<SendRequest>> queries = new ArrayList<BmobQuery<SendRequest>>();
					queries.add(query1);
					queries.add(query2);
					BmobQuery<SendRequest> mainQuery = new BmobQuery<SendRequest>();
					mainQuery.or(queries);
					mainQuery.findObjects(XMBActivity.this, new FindListener<SendRequest>() {
					    @Override
					    public void onSuccess(List<SendRequest> object) {
					    if(object!=null && object.size()>0){
					    	//发送消息显示小红点提示 
					    	Message msg = new Message();               
							msg.what = 2;                            
							XMBActivity.this.myHandler.sendMessage(msg); 
							DBAccess access= new DBAccess(XMBActivity.this);
					    	for(SendRequest sr : object){
					    	if(sr.getRequestPerson().equals(currentUser)
					    	  && sr.getAddFlag().equals(2)){					    	  
					    	passId=sr.getRequestPerson();	  
					    	  
					    	}
					        //先插入   		
						    access.insert2(sr);
			 Log.i("ssssss1111", ""+sr.getRequestObject()+"/"+sr.getRequestPerson()+"/"+sr.getAddFlag()+"/"+sr.getObjectId());		
					    	}
					    	//再去掉重复值
					    	access.offRepetition();
					    	}					    
					    }
					    @Override
					    public void onError(int code, String msg) {
					    Log.i("addFriendInfo", "匹配添加好友信息失败："+code+msg);    
					    }
					});   			    
		}	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ((keyCode == KeyEvent.KEYCODE_BACK)&&(fragmentManager.getBackStackEntryCount()>0)) {
			fragmentManager.popBackStack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	@Override
	public void CallBackTips(int message) {
		if (message==2)		
	    msg_tips_img.setVisibility(View.GONE);
		Log.i("callBackTips", "call back succeed");
	 }
	
	
}
