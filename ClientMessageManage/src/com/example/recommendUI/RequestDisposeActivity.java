package com.example.recommendUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.Adapter.RequestDisposeAdapter;
import com.example.Adapter.RequestDisposeAdapter.CallBackFlag;
import com.example.bean.ContactPerson;
import com.example.bean.Contacts;
import com.example.bean.SendRequest;
import com.example.broadsideUI.SeeInfoActivity;
import com.example.clientmessagemanage.R;
import com.example.clientmessagemanage.XMBActivity;
import com.example.util.ConstantValue;
import com.example.util.DBAccess;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RequestDisposeActivity extends Activity implements 
OnClickListener,OnItemClickListener{
    private ListView request_dispose_list;
	private TextView no_info_text;
    private List<SendRequest> srtList;
    private RequestDisposeAdapter adapter;
    private TextView deleteAddFriendInfo;
    private List<Map<String, Object>> list2=new ArrayList<Map<String,Object>>();
    //临时保存objectId用来删除添加信息
    private ArrayList<String> temp =new ArrayList<String>(); 
    //临时保存objectId用来自动删除云服务数据
    private ArrayList<String> temp2 =new ArrayList<String>(); 
    
	private ArrayList<List<List<String>>> ListCP;	
    //通过好友申请的用户ID
    private String passId;
    private String passName;
	private TextView request_dispose_return;
	private boolean data =false;
	private String objectId;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recommend_request_dispose);
	
		init();
		obtainRequest();
	}

	
	private void init() {
		   no_info_text=(TextView)findViewById(R.id.no_info_text);
	       request_dispose_list=(ListView)findViewById(R.id.request_dispose_list);
	       deleteAddFriendInfo=(TextView)findViewById(R.id.deleteAddFriendInfo);
	       request_dispose_return=(TextView)findViewById(R.id.request_dispose_return);
	        
	       deleteAddFriendInfo.setOnClickListener(this);
	       request_dispose_return.setOnClickListener(this);
	       request_dispose_list.setOnItemClickListener(this); 
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.deleteAddFriendInfo:
			onCreateDeleteDialog();	
			break;
		case R.id.request_dispose_return:
			Intent intent =new Intent();
        	Bundle bundle = new Bundle(); 
            bundle.putInt("setTab", 2);
        	intent.putExtras(bundle);
			intent.setClass(this, XMBActivity.class);
			this.startActivity(intent);
			finish(); 
			break;
		default:
			break;
		}
	}
	
	
	/**
	    * 获取本地的请求信息
	    */
		private void obtainRequest() {
		DBAccess ac=new DBAccess(this);
		File file = this.getExternalCacheDir();
		if(file!=null){

		if(ac.queryTable2()==false){
		request_dispose_list.setVisibility(View.GONE);
		no_info_text.setVisibility(View.VISIBLE);	
		}else{
		SharedPreferences sf=this.getSharedPreferences("test", 
				Activity.MODE_PRIVATE); 
		String currentUser=sf.getString("currentUser", "");
		srtList=ac.queryTableRequest(currentUser);
		if(srtList.size()>0&& srtList!=null){
		for(int i=0;i<srtList.size();i++){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("image2", R.drawable.head_man);
		map.put("requestObject", srtList.get(i).getRequestPerson());//这里需要反过来放
		map.put("requestPerson", srtList.get(i).getRequestObject());
		map.put("addFlag", srtList.get(i).getAddFlag());
		map.put("objectIds", srtList.get(i).getObjectIds());
		map.put("name", srtList.get(i).getRequestPersonName());
		if(srtList.get(i).getAddFlag().equals("1")){
			map.put("callBackShow", "出错了");	
			map.put("boolean", "true");
		}
		if(srtList.get(i).getAddFlag().equals("2")){
			map.put("callBackShow", "已同意");	
			map.put("boolean", "false");
		}
		if(srtList.get(i).getAddFlag().equals("3")){
			map.put("callBackShow", "已拒绝");	
			map.put("boolean", "false");
		}
		map.put("requestStateShow", "请求加你为好友");
		
		
		list2.add(map);
		temp.add(srtList.get(i).getObjectIds());
		}	
		}
		srtList.clear();
		srtList=ac.queryTablePending(currentUser);
		if(srtList.size()>0 && srtList!=null){
		for(int i=0;i<srtList.size();i++){
		Map<String ,Object> map2=new HashMap<String ,Object>();
		map2.put("image2", R.drawable.head_man);
		map2.put("requestObject", srtList.get(i).getRequestObject());
		map2.put("requestPerson", srtList.get(i).getRequestPerson());
		map2.put("addFlag", srtList.get(i).getAddFlag());
		map2.put("objectIds", srtList.get(i).getObjectIds());
		map2.put("name", srtList.get(i).getRequestObjectName());
		if(srtList.get(i).getAddFlag().equals("1")){
			map2.put("callBackShow", "待处理");	
		}
		if(srtList.get(i).getAddFlag().equals("2")){
			map2.put("callBackShow", "已同意");
			
		temp2.add(srtList.get(i).getObjectIds());
		}
		if(srtList.get(i).getAddFlag().equals("3")){
			map2.put("callBackShow", "被拒绝");
			
		temp2.add(srtList.get(i).getObjectIds());
		}		
		map2.put("requestStateShow", "添加好友请求");
		map2.put("boolean", "false");
		
		list2.add(map2);
		temp.add(srtList.get(i).getObjectIds());
 
		}
		}
		myThread mt =new myThread();
		mt.start();
		adapter=new RequestDisposeAdapter(RequestDisposeActivity.this, list2,callBackFlag);
		request_dispose_list.setAdapter(adapter);

		}	
		}	
		} 

		//回调操作
		CallBackFlag callBackFlag =new CallBackFlag(){

			@Override
			public void onClick(int flag, int pos) {
				//获取textview中的值
			String requestObject=(String)list2.get(pos).get("requestObject");
			String requestPerson=(String)list2.get(pos).get("requestPerson");
			String objid=(String)list2.get(pos).get("objectIds");
				Log.i("title", ""+requestObject+"/"+requestPerson+"/"+objid);
				SendRequest sr=new SendRequest();
				//同意事件
				if(flag==2){
				sr.setAddFlag("2");	
				((Map)list2.get(pos)).put("callBackShow", "已同意");
				((Map)list2.get(pos)).put("boolean", "false");
				adapter.notifyDataSetChanged();
				
				passId=list2.get(pos).get("requestObject").toString();
				passName=list2.get(pos).get("name").toString();
				addFriendThread afThread=new addFriendThread();
				afThread.start();
				}
				//拒绝事件
				if(flag==3){
				sr.setAddFlag("3");	
				((Map)list2.get(pos)).put("callBackShow", "已拒绝");
				((Map)list2.get(pos)).put("boolean", "false");
				adapter.notifyDataSetChanged();
					}
			sr.update(RequestDisposeActivity.this,objid, new UpdateListener() {
				
				@Override
				public void onSuccess() {
		    Toast.makeText(RequestDisposeActivity.this, "反馈成功！"+passId, Toast.LENGTH_LONG).show();	    	
					
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
			Toast.makeText(RequestDisposeActivity.this, "反馈失败！", Toast.LENGTH_SHORT).show(); 	
					
				}
			});
				
			}
			  
		   };


	/**
	  * 删除整个信息列表 
	  */
	 private void onCreateDeleteDialog() {

		AlertDialog.Builder builder=new Builder(this);
	    
		builder.setTitle("删除");
		builder.setMessage("是否删除整个列表信息?");
		builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			
			no_info_text.setVisibility(View.VISIBLE);
			
			//清除列表信息
			list2.clear();
			adapter.notifyDataSetChanged();
			DBAccess ac=new DBAccess(RequestDisposeActivity.this);
			//清除本地数据
			ac.deleteTableSendRequest();
			SendRequest sr=new SendRequest();
//			sr.deleteBatch(RequestDisposeActivity.this, temp, new DeleteListener(){
		    for(int i=0;i<temp.size();i++){
			sr.setObjectId(temp.get(i).toString());	    
			sr.delete(RequestDisposeActivity.this,new DeleteListener() {
				
				@Override
				public void onSuccess() {				
				Log.i("success", "删除成功");	
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {

				}
			});
		    }
		    
			}
			});
		
		builder.setNegativeButton("否", null);
		builder.show();
		}
	 
		/**
		 * 返回键监听
		 */
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			  
	        if (keyCode == KeyEvent.KEYCODE_BACK
	                 && event.getRepeatCount() == 0) {
	        	Intent intent=new Intent();
	        	Bundle bundle = new Bundle(); 
                bundle.putInt("setTab", 2);
	        	intent.putExtras(bundle);	        	
	            intent.setClass(this, XMBActivity.class);
	            this.startActivity(intent);
	            finish();
	           return true;
	         }
	         return super.onKeyDown(keyCode, event);
	     }


		/**
		 * item监听事件
		 */
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent=new Intent();
			Bundle bundle=new Bundle();
			bundle.putString("id",(String) list2.get(arg2).get("requestObject"));
			bundle.putString("name",(String) list2.get(arg2).get("name"));			  
			intent.putExtras(bundle);
			intent.setClass(RequestDisposeActivity.this, SeeInfoActivity.class);	
			startActivity(intent);
		}

	/**
	 * 删除云服务已处理信息的线程
	 * @author Stt
	 *
	 */
  class myThread extends Thread{
	
	  @Override
	public void run() {
		  
		SendRequest sr=new SendRequest();
		for(int i=0;i<temp2.size();i++){			
		sr.setObjectId(temp2.get(i).toString());	    
		sr.delete(RequestDisposeActivity.this,new DeleteListener() {				
		   @Override
			public void onSuccess() {				
			Log.i("success2", "删除成功"+temp2.get(0).toString());	
			}
				
			@Override
			public void onFailure(int arg0, String arg1) {
			Log.i("onFailure2", +arg0+"删除失败"+arg1);	
			}
		});
		}
		
	}
  }
/**
 * 将已同意的好友保存
 * @author Administrator
 *
 */
   class addFriendThread extends Thread {
	   
	   private String currentUser;


	@Override
	public void run(){
		 //获取当前登录的用户		
		SharedPreferences sp= getSharedPreferences("test", 
		        Activity.MODE_PRIVATE); 
		currentUser =sp.getString("currentUser", "");    		
		
		//获取网络好友列表
		BmobQuery<Contacts> query=new BmobQuery<Contacts>();
		query.addWhereEqualTo(ConstantValue.DB_MetaData3.USER_ID, currentUser);	
		query.findObjects(RequestDisposeActivity.this, new FindListener<Contacts>() {
			
			@Override
			public void onSuccess(List<Contacts> arg0) {
				
			if(arg0.size()>0 && arg0 !=null){
			for(Contacts cs : arg0){
			List<String> value =new ArrayList<String>();
			value.add(passId);
			value.add(passName);

			if(cs.getPrivateFriend()!=null){
			
			ListCP=cs.getPrivateFriend();
			//不是第一次添加好友，在已有集合里添加好友名字	
			((List)ListCP.get(0)).add(value);
			Log.i("ListCP", ""+ListCP);
			      }else{
			//第一次添加好友，创建ArrayList<List<Object>>集合
			ListCP=new ArrayList<List<List<String>>>();	
			//0代表未分组，默认添加到未分组
			List<List<String>> map=new ArrayList<List<String>>();
			map.add(value);
			ListCP.add(0, map);
			Log.i("ListCP2", ""+ListCP);
			     }
			
			objectId=cs.getObjectId();
			Log.i("objectId", ""+objectId);
			   }
			  }
		ContactPerson cp=new ContactPerson();  
		DBAccess ac=new DBAccess(RequestDisposeActivity.this); 
		try {
		cp.setPrivateFriends(ListCP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		  //更新信息		
		 ac.updateTable3Single(currentUser, ConstantValue.DB_MetaData3.PRIVATE_FRIEND,
		 cp.getPrivateFriend()); 
		 Log.i("输出map3", ""+ListCP+objectId);
		 networkDatabase();
			}
			
		@Override
		public void onError(int arg0, String arg1) {
			
		}
		});		
	}

	/**
	 * 更新网络数据
	 */
	private void networkDatabase() {
		Contacts c=new Contacts();		
		c.setPrivateFriend(ListCP);
		c.update(RequestDisposeActivity.this, objectId, 
				new UpdateListener() {
					
					@Override
					public void onSuccess() {						
					Log.i("onSuccess", "更新好友列表成功！");	
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {					
					Log.i("onFailure", "更新好友列表失败！"+arg0+arg1);	
					}
				});	
	}
	 
	
   }
}
