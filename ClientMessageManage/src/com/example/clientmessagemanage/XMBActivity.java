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
 * ��Ŀ����Activity�����е�Fragment��Ƕ�������
 * 
 * @author guolin
 */
public class XMBActivity extends Activity implements OnClickListener,CallBackTips{


			/**
			 * ����չʾ������Fragment
			 */
			private ContactsFragment contactsFragment;

			/**
			 * ����չʾ���ѵ�Fragment
			 */
			private RemindFragment remindFragment;

			/**
			 * ����չʾ�Ƽ���Fragment
			 */
			private RecommendFragment recommendFragment;

			/**
			 * �������沼��
			 */
			private View contactsLayout;

			/**
			 * ���ѽ��沼��
			 */
			private View remindLayout;

			/**
			 * �Ƽ����沼��
			 */
			private View recommendLayout;

			/**
			 * ��Tab��������ʾ��������Ŀؼ�
			 */
			private TextView contactsText;

			/**
			 * ��Tab��������ʾ���ѱ���Ŀؼ�
			 */
			private TextView remindText;

			/**
			 * ��Tab��������ʾ�Ƽ�����Ŀؼ�
			 */
			private TextView recommendText;

			/**
			 * ���ڶ�Fragment���й���
			 */
			private FragmentManager fragmentManager;
			
			//�໬�˵�
			private ListView broadsideList;
			private String[] items={"��������","��Ӻ���","��ձ�������"};

			private String passId;
			private int xmbMsg;
			List<Map<String, Object>> list2=new ArrayList<Map<String,Object>>();
			private ImageView msg_tips_img;
			private myThread th; 
			@Override
			public void onCreate(Bundle savedInstanceState) {
				requestWindowFeature(Window.FEATURE_NO_TITLE); // �ޱ���
				super.onCreate(savedInstanceState);
				setContentView(R.layout.xmb);
				// ��ʼ������Ԫ��
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
				// ��һ������ʱѡ�е�0��tab
				setTabSelection(setTab);
			
			}


			/**
			 * �������ȡ��ÿ����Ҫ�õ��Ŀؼ���ʵ���������������úñ�Ҫ�ĵ���¼���
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
				
			    //ͨ��setAdapter����һ��ArrayAdapter��items��mListView"��" 
				broadsideList.setAdapter(new ArrayAdapter<String>(this,
						android.R.layout.simple_expandable_list_item_1,items));
				
				contactsLayout.setOnClickListener(this);
				remindLayout.setOnClickListener(this);
				recommendLayout.setOnClickListener(this);
				

				//����˵���
				broadsideList.setVisibility(View.INVISIBLE);
				broadsideList.setOnItemClickListener(new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						 TextView tv = (TextView) view;
				        if(tv.getText().toString().equals("��������"))
		                {
		                    Intent intent = new Intent();
		                    intent.setClass(XMBActivity.this, PersonInfoActivity.class);
		                    XMBActivity.this.startActivity(intent);
		                }
		                if(tv.getText().toString().equals("��Ӻ���"))
		                {
		                    Intent intent = new Intent();
		                    intent.setClass(XMBActivity.this, AddFriendActivity.class);
		                    XMBActivity.this.startActivity(intent);
		                    finish();
		                }
		                if(tv.getText().toString().equals("��ձ�������"))
		                {
                      DBAccess acs=new DBAccess(XMBActivity.this);
                      acs.deleteTableUserInfo();
		              Toast.makeText(XMBActivity.this, "����գ�", Toast.LENGTH_SHORT).show();	
		                }
					}
					
				});
				
				
			}

			//���tab
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.contacts_layout:
					// �����������tabʱ��ѡ�е�1��tab
					setTabSelection(0);
					break;
				case R.id.remind_layout:
					// �����������tabʱ��ѡ�е�2��tab
					setTabSelection(1);
					break;
				case R.id.recommend_layout:
					// ��������Ƽ�tabʱ��ѡ�е�3��tab
					setTabSelection(2);
					break;
				default:
					break;
				}
			}
			
			/**
			 * ���ݴ����index����������ѡ�е�tabҳ��
			 * 
			 * @param index
			 *            ÿ��tabҳ��Ӧ���±ꡣ0��ʾ������1��ʾ���ѣ�2��ʾ�Ƽ���
			 */
			private void setTabSelection(int index) {
				// ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬
				clearSelection();
				// ����һ��Fragment����
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
				hideFragments(transaction);
				switch (index) {
				case 0:
					// �����������tabʱ���ı�ؼ���������ɫ
					contactsText.setTextColor(Color.WHITE);
					if (contactsFragment == null) {
						// ���ContactsFragmentΪ�գ��򴴽�һ������ӵ�������
						contactsFragment = new ContactsFragment();
						transaction.add(R.id.content, contactsFragment);
					} else {
						// ���ContactsFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
						transaction.show(contactsFragment);
					}
					break;
				case 1:
					// �����������tabʱ���ı�ؼ���������ɫ
					remindText.setTextColor(Color.WHITE);
					if (remindFragment == null) {
						// ���RemindFragmentΪ�գ��򴴽�һ������ӵ�������
						remindFragment = new RemindFragment();
						transaction.add(R.id.content, remindFragment);
						
					} else {
						// ���RemindFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
						transaction.show(remindFragment);
					}
					break;
				case 2:
				default:
					Bundle bundle = new Bundle();
					bundle.putInt("msg", xmbMsg);		
					
					// ��������Ƽ�tabʱ���ı�ؼ���������ɫ
					recommendText.setTextColor(Color.WHITE);
					if (recommendFragment == null) {
						// ���RecommendFragmentΪ�գ��򴴽�һ������ӵ�������
						recommendFragment = new RecommendFragment();						
						recommendFragment.setArguments(bundle);
						transaction.add(R.id.content, recommendFragment,"myTag");
					} else {
						// ���RecommendFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
					//	recommendFragment.setArguments(bundle);
						transaction.show(recommendFragment);
					}
					break;
				}
		        
				transaction.commit();
			}

			/**
			 * ��������е�ѡ��״̬��
			 */
			private void clearSelection() {

				contactsText.setTextColor(Color.parseColor("#82858b"));
				remindText.setTextColor(Color.parseColor("#82858b"));
				recommendText.setTextColor(Color.parseColor("#82858b"));
			}
			/**
			 * �����е�Fragment����Ϊ����״̬��
			 * 
			 * @param transaction
			 *            ���ڶ�Fragmentִ�в���������
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
		     * ÿ�ε��������涼���ѯһ���Ƿ�����Ӻ��ѵ�������Ϣ
		     */
					//�ڶ�ȡSharedPreferences����ǰҪʵ������һ��SharedPreferences���� 
					SharedPreferences sharedPreferences= getSharedPreferences("test", 
					Activity.MODE_PRIVATE); 
					// ʹ��getString�������value��ע���2��������value��Ĭ��ֵ 
					final String currentUser =sharedPreferences.getString("currentUser", ""); 
					
					//�ȷֿ���ѯ
					BmobQuery<SendRequest> query1=new BmobQuery<SendRequest>();
					query1.addWhereEqualTo("requestObject", currentUser);
					BmobQuery<SendRequest> query2 = new BmobQuery<SendRequest>();
					query2.addWhereEqualTo("requestPerson", currentUser);
					//��ѯ������������ٴ�or���򣩲�ѯ
					List<BmobQuery<SendRequest>> queries = new ArrayList<BmobQuery<SendRequest>>();
					queries.add(query1);
					queries.add(query2);
					BmobQuery<SendRequest> mainQuery = new BmobQuery<SendRequest>();
					mainQuery.or(queries);
					mainQuery.findObjects(XMBActivity.this, new FindListener<SendRequest>() {
					    @Override
					    public void onSuccess(List<SendRequest> object) {
					    if(object!=null && object.size()>0){
					    	//������Ϣ��ʾС�����ʾ 
					    	Message msg = new Message();               
							msg.what = 2;                            
							XMBActivity.this.myHandler.sendMessage(msg); 
							DBAccess access= new DBAccess(XMBActivity.this);
					    	for(SendRequest sr : object){
					    	if(sr.getRequestPerson().equals(currentUser)
					    	  && sr.getAddFlag().equals(2)){					    	  
					    	passId=sr.getRequestPerson();	  
					    	  
					    	}
					        //�Ȳ���   		
						    access.insert2(sr);
			 Log.i("ssssss1111", ""+sr.getRequestObject()+"/"+sr.getRequestPerson()+"/"+sr.getAddFlag()+"/"+sr.getObjectId());		
					    	}
					    	//��ȥ���ظ�ֵ
					    	access.offRepetition();
					    	}					    
					    }
					    @Override
					    public void onError(int code, String msg) {
					    Log.i("addFriendInfo", "ƥ����Ӻ�����Ϣʧ�ܣ�"+code+msg);    
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
