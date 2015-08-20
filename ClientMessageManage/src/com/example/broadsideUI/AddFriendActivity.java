package com.example.broadsideUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.Adapter.AddFriendAdapter;
import com.example.Adapter.AddFriendAdapter.ScrollToLastCallBack;
import com.example.bean.SendRequest;
import com.example.bean.UserInfo;
import com.example.clientmessagemanage.R;
import com.example.clientmessagemanage.XMBActivity;
import com.example.util.ConstantValue;
import com.example.util.DBAccess;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddFriendActivity extends Activity implements OnClickListener{

	private Button btn_find_search_id;
	private TextView et_find_user;
	private ListView add_friend_list;
	private Button btn_find_search_user;
	private ProgressDialog progress ;
	private TextView add_friend_return;
	private Button btn_find_search_refresh;
	private String currentUser;
	//ȷ�ϵ�ǰ�������ֲ�ѯ��ʽ
    private int mark=0;
    //��ʱ�����ѯ���
    private ArrayList<String> temp=new ArrayList<String>();  
    private AddFriendAdapter adapter;
	private String requestPersonName;
    
    private List<Map<String, Object>> list=new ArrayList<Map<String,Object>>(); 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.broadside_add_friend);

		init();
		//�ڶ�ȡSharedPreferences����ǰҪʵ������һ��SharedPreferences���� 
		SharedPreferences sharedPreferences= getSharedPreferences("test", 
		Activity.MODE_PRIVATE); 
		// ʹ��getString�������value��ע���2��������value��Ĭ��ֵ 
		currentUser =sharedPreferences.getString("currentUser", ""); 
		
		
	}

	private void init() {
		et_find_user=(TextView)findViewById(R.id.et_find_user);
		btn_find_search_id=(Button)findViewById(R.id.btn_find_search_id);
		btn_find_search_user=(Button)findViewById(R.id.btn_find_search_user);
		add_friend_list=(ListView)findViewById(R.id.add_friend_list);  
		add_friend_return=(TextView)findViewById(R.id.add_friend_return);
		btn_find_search_refresh=(Button)findViewById(R.id.btn_find_search_refresh);
		
		btn_find_search_id.setOnClickListener(this);
		btn_find_search_user.setOnClickListener(this);
		add_friend_return.setOnClickListener(this);
		btn_find_search_refresh.setOnClickListener(this);
		add_friend_list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent();
				
				//�鿴id�Ƿ������ڵ�¼��
				if(list.get(arg2).get("objectId").equals(currentUser)){
				intent.setClass(AddFriendActivity.this,PersonInfoActivity.class);	
			    startActivity(intent);	
				}else{
			  Bundle bundle=new Bundle();
			  bundle.putString("id",(String) list.get(arg2).get("objectId"));
			  bundle.putString("name",(String) list.get(arg2).get("title"));
			 
			  
			  intent.putExtras(bundle);
			  intent.setClass(AddFriendActivity.this, SeeInfoActivity.class);	
			  startActivity(intent);
			}
			}
		});
	}

	@Override
	public void onClick(View v) {
	
		switch (v.getId()) {
		case R.id.btn_find_search_id:
			if(et_find_user.getText().toString().equals("") ){
			Toast.makeText(AddFriendActivity.this, "�������û�����", Toast.LENGTH_SHORT).show();					
			}else{
			queryFriend(1,1);
			temp.clear();
			}			
			break;
		case R.id.btn_find_search_user:
			if(et_find_user.getText().toString().equals("") ){
			Toast.makeText(AddFriendActivity.this, "�������û�����", Toast.LENGTH_SHORT).show();						
			}else{
			queryFriend(2,2);
			temp.clear();
			}	

			break;
			
		case R.id.btn_find_search_refresh:
			if(mark==0){
			Toast.makeText(AddFriendActivity.this, "��������Ϣ��ѯ��", Toast.LENGTH_SHORT).show();		    	
			}else{
			queryFriend(mark,3);
			
			}
			//Log.i("sssssss1", ""+mark);
			break;
			
		case R.id.add_friend_return:
            Intent intent=new Intent();
            intent.setClass(AddFriendActivity.this, XMBActivity.class);
            AddFriendActivity.this.startActivity(intent);
            finish();
			break;
		default:
			break;
		}
		
	}
	
    /**
     * �����û�
     * @param type ��¼�ĸ���ť�����
     * @param refresh 
     */
	private void queryFriend(final int type,final int refresh) {
	progress = new ProgressDialog(AddFriendActivity.this);
	progress.setMessage("��������...");
	progress.setCanceledOnTouchOutside(true);
	progress.show();
	BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
	//��ѯ�����ı�
	 if(type==1&&refresh==3){
	query.addWhereNotContainedIn(ConstantValue.DB_MetaData.USER_ID,temp);     	
	
	Log.i("lists", ""+temp);
	}
	if(type==1||refresh==1){
	
	query.addWhereContains(ConstantValue.DB_MetaData.USER_ID,
		et_find_user.getText().toString());	
	query.addWhereNotEqualTo(ConstantValue.DB_MetaData.USER_ID,currentUser);
		mark=type;
	}

    if(type==2&&refresh==3){
    query.addWhereNotContainedIn(ConstantValue.DB_MetaData.USER_ID,temp);
    query.addWhereNotEqualTo(ConstantValue.DB_MetaData.USER_ID,currentUser);
    }
    if(type==2||refresh==2){
    	query.addWhereContains(ConstantValue.DB_MetaData.NAME,
    			et_find_user.getText().toString());	
    	mark=type;
    }

	//����10������
	query.setLimit(10);
	query.findObjects(this, new FindListener<UserInfo>() {

		@Override
		public void onError(int arg0, String arg1) {
		Log.i("��ѯ����", ""+arg1);	
		progress.dismiss();
		}
	
		@Override
		public void onSuccess(List<UserInfo> info) {
						
			if(info.size()!=0 ){
				 list.clear();
			for (UserInfo uinfo : info) {
			 //     for (int i = 0; i < info.size(); i++) {
				  
			    Map<String, Object> map=new HashMap<String, Object>(); 
		          map.put("image", R.drawable.head_man);
			      map.put("title", uinfo.getName());
			      map.put("objectId", uinfo.getUserId());
			      map.put("currentUser", currentUser);
                  map.put("button", "���");
			      Log.i("info.size", ""+uinfo.getUserId());
			      			      
			      list.add(map);
			      temp.add(uinfo.getUserId());  			      
		    //  } 
			  }
			
	    adapter =new AddFriendAdapter(AddFriendActivity.this, list,scrollToLastCallBack);
		add_friend_list.setAdapter(adapter);
		progress.dismiss();	
		if(list.size()==10){
		Toast.makeText(AddFriendActivity.this, "��ѯ�ɹ���", Toast.LENGTH_SHORT).show();
		}else if(list.size()>0 && list.size()<10){
		Toast.makeText(AddFriendActivity.this, "û�и�������", Toast.LENGTH_SHORT).show();	
		}
			}else if(info.size()==0 &&refresh==3){
				Toast.makeText(AddFriendActivity.this, "û�и�������", Toast.LENGTH_SHORT).show();			
			}else{
		Toast.makeText(AddFriendActivity.this, "�����ڵ��û���", Toast.LENGTH_SHORT).show();		
			}
		progress.dismiss();
		}
		
	});
	
	}
	
	/**
	 * �������ص���������Ӧlist��button�����¼�
	 */
	ScrollToLastCallBack scrollToLastCallBack=new ScrollToLastCallBack(){  
		  
	    @Override  
	    public void onScrollToLast(final int pos) { 
	    	
	 		//�����ȡ��Ϣ
	 		BmobQuery<UserInfo> queryInfo = new BmobQuery<UserInfo>();
	 		queryInfo.addWhereEqualTo("userId", currentUser); 
	 		queryInfo.findObjects(AddFriendActivity.this, new FindListener<UserInfo>() {

	 		@Override
	 		public void onError(int arg0, String arg1) {					
	 		Log.i("onError", "��ȡ������Ϣʧ��"+requestPersonName); 	
	 		}

	 		@Override
	 		public void onSuccess(List<UserInfo> arg0) {					
	 		for(UserInfo info :arg0 ){
	 		requestPersonName=info.getName();
	 		Log.i("onSuccess", "��ȡ������Ϣ"+requestPersonName); 				
	 		}
	 		}					
	 		});		
	    	 
	    	//��ȡtextview�е�ֵ
            final String objectId=(String)list.get(pos).get("objectId");
            final String requestObjectName=(String)list.get(pos).get("title");
            
            Log.i("title", ""+objectId+"/"+currentUser+"/"+pos); 

            //�Ȳ�ѯ�Ƿ��͹�����
            BmobQuery<SendRequest> query = new BmobQuery<SendRequest>();     
            query.addWhereEqualTo("requestObject", objectId);
            query.addWhereEqualTo("requestPerson", currentUser);
            query.findObjects(AddFriendActivity.this, new FindListener<SendRequest>() {
				

				@Override
				public void onSuccess(List<SendRequest> srt) {
				if(srt!=null && srt.size()>0){
				Log.i("position", ""+pos);
			    ((Map)list.get(pos)).put("button","������");		        
			    
				adapter.notifyDataSetChanged();

				Toast.makeText(AddFriendActivity.this, "�������뵽�Ƽ��鿴�����", Toast.LENGTH_SHORT).show();									
				}else{			
																				
			 //û�м�¼����������¼					
	         SendRequest sr=new SendRequest();
		     sr.setRequestObject(objectId);
		     sr.setRequestPerson(currentUser);            
		     sr.setAddFlag("1");		     
		     sr.setRequestObjectName(requestObjectName);		     
		     sr.setRequestPersonName(requestPersonName);
		     sr.save(AddFriendActivity.this, new SaveListener() {
						
				 @Override
				 public void onSuccess() {
				 Toast.makeText(AddFriendActivity.this, "����ɹ����ȴ�ȷ�ϣ�", Toast.LENGTH_LONG).show();									
				 Log.i("position", ""+pos+requestPersonName); 
				// temp2.set(pos, "������");
				 
				((Map)list.get(pos)).put("button","������");
				adapter.notifyDataSetChanged();
						}



				@Override
				public void onFailure(int arg0, String arg1) {
				Log.i("onFailure", ""+arg0+arg1);
				 Toast.makeText(AddFriendActivity.this, "����ʧ�ܣ�", Toast.LENGTH_SHORT).show();	
							
						}
					}); 	
				}
				}
				
				@Override
				public void onError(int arg0, String arg1) {		
				Toast.makeText(AddFriendActivity.this, "����ʧ�ܣ�", Toast.LENGTH_SHORT).show();	
				
				}
			});           

	    }  
		      
	};
	
	
	/**
	 * ���ؼ�����
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		  
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
        	Intent intent=new Intent();
            intent.setClass(AddFriendActivity.this, XMBActivity.class);
            AddFriendActivity.this.startActivity(intent);
            finish();
           return true;
         }
         return super.onKeyDown(keyCode, event);
     }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_friend, menu);
		return true;
	}

	
}
