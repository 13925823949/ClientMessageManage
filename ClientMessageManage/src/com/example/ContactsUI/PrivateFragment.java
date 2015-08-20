package com.example.ContactsUI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.Adapter.PrivateManagerDbAdater;
import com.example.bean.ContactPerson;
import com.example.bean.Contacts;
import com.example.broadsideUI.AddFriendActivity;
import com.example.broadsideUI.SeeInfoActivity;
import com.example.clientmessagemanage.R;
import com.example.recommendUI.RecommendFragment.CallBackTips;
import com.example.util.ConstantValue;
import com.example.util.DBAccess;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;


public class PrivateFragment extends Fragment{
	
	private View privateLayout;
    private ArrayList<String> ArrayList =new ArrayList<String>();//获取组
    private ArrayList<List<List<String>>> list=new ArrayList<List<List<String>>>();//获取联系人
    private PrivateManagerDbAdater adapter;
    private ExpandableListView expandableListView;
	private String currentUser;
    
	private static final int ADD_GROUP=0;//添加分组
	private static final int DELETE_GROUP=1;//删除分组
	private static final int REN_GROUP=2;//重命名
	private static final int ADD_FRIEND_GROUP=3;//添加好友
	private static final int DELETE_FRIEND=1;//删除好友
	private static final int MOVE=0;//移动好友
	private static final int MARK=2;//备注
	

    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) { 
    	
       privateLayout = inflater.inflate(R.layout.private_layout, container, false);  
       expandableListView = (ExpandableListView)privateLayout.findViewById(R.id.privateList);
       
     //读取当前登录用户
       SharedPreferences sharedPreferences= getActivity().getSharedPreferences("test", 
       Activity.MODE_PRIVATE); 
       currentUser =sharedPreferences.getString("currentUser", "");
      setHasOptionsMenu(true);
      addListInfo();
       // 设置长按时的事件   
      registerForContextMenu(expandableListView);  

                            
        //设置item点击的监听器
        expandableListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {

            	Intent intent=new Intent();
            	intent.setClass(getActivity(), SeeInfoActivity.class);
            	Bundle bundle=new Bundle();
            	bundle.putString("id", adapter.getChild(groupPosition, childPosition).get(0));
                bundle.putString("name", adapter.getChild(groupPosition, childPosition).get(1));
            	intent.putExtras(bundle);
            	getActivity().startActivity(intent);      
                return false;
            }
        });
    
       return privateLayout;
}
    
    
    
    
   @Override 
   public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
   ExpandableListContextMenuInfo info=(ExpandableListContextMenuInfo)menuInfo;
   int type = ExpandableListView.getPackedPositionType(info.packedPosition);

   //处理逻辑
   if (type == 0) {// 分组长按事件
	  String title=((TextView)info.targetView.findViewById(R.id.groupName)).getText().toString();

	  if(!title.equals("未分组")||!title.equals("特别关注")){	  
	  menu.setHeaderTitle(title);
	  menu.add(0, ADD_GROUP, 0, "添加分组");
	  menu.add(0, DELETE_GROUP, 0, "删除分组");
	  menu.add(0, REN_GROUP, 0, "重命名");
	  menu.add(0, ADD_FRIEND_GROUP, 0, "添加联系人");
	  menu.add(0,4,0,"取消");
	  }
   } else if (type == 1) {// 长按好友列表项
	   String title=((TextView)info.targetView.findViewById(R.id.child_name)).getText().toString();
	   String id=((TextView)info.targetView.findViewById(R.id.child_id)).getText().toString(); 
	          	
	    int groupIndex=ExpandableListView.getPackedPositionGroup(info.packedPosition);
	    Log.i("长按获取下标", "groupIndex="+groupIndex+"/id="+id);
	    if(groupIndex!=0){	
	    menu.setHeaderTitle(title);
		menu.add(0, MOVE, 0, "移动联系人到...");
		menu.add(0, DELETE_FRIEND, 0, "删除联系人");
		menu.add(0, MARK, 0, "备注");
        menu.add(0, 3, 0,"取消");
	   }else{
	   menu.setHeaderTitle("移动到...");
	   for(int y=0;y<ArrayList.size();y++){
	   if(y==0){
		   
	   }else{
	   menu.add(0, y, 0, ArrayList.get(y));
	   }
	   }
	   }
   }

   }
    
 
   public boolean onContextItemSelected(MenuItem item) {
		
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();
       int type = ExpandableListView.getPackedPositionType(info.packedPosition);
       if (type == 0) {
      int groupIndex=ExpandableListView.getPackedPositionGroup(info.packedPosition);	        
	  
	Log.i("长按获取下标2", "groupIndex="+groupIndex); 
    switch (item.getItemId()) {
	   case ADD_GROUP:		
		  onInputDialog(); 
		break;
	   case DELETE_GROUP:
		  deleteGroup(groupIndex);
		break;
	   case REN_GROUP:
		   renGroup(groupIndex);
		   break;
	   case ADD_FRIEND_GROUP:
		  Intent intent=new Intent();
		  intent.setClass(getActivity(), AddFriendActivity.class);
		  getActivity().startActivity(intent);		   
		   break;
	   default:
		break;
	}                         
           return true;
       } else if (type == 1) {
    	//长按联系人的名字
       	String childName=((TextView)info.targetView.findViewById(R.id.child_name)).getText().toString();
       	//长按组的下标
       	int groupIndex=ExpandableListView.getPackedPositionGroup(info.packedPosition);
       	//长按联系人的下标
       	int childIndex=(int) ExpandableListView.getPackedPositionChild(info.packedPosition);
		Log.i("事件处理", "groupIndex="+groupIndex+"/childIndex="+childIndex+"/childName="+childName);
       	if(groupIndex!=0){
		switch(item.getItemId()){
       	 case DELETE_FRIEND:
       		 deleteFriend(groupIndex,childIndex,childName);       	
	         break;
       	 case MOVE:
             addGroup(childName,groupIndex,childIndex);
       		 break;
       	 case MARK:
       		addMark(childName,groupIndex,childIndex); 
       		 break;
       	 default:
     		break; 
	            }       	
       	       	
       }else{    	   
    	    moveFriend(groupIndex,item.getItemId(),childIndex);
       }
       	return true;	
       }
       return false;
       
       
	}

	/*
     * 导入联系人列表
     */
    private void addListInfo() {
    	 	
    	 File file = getActivity().getExternalCacheDir();  
    	 DBAccess ac =new DBAccess(getActivity());
    	 Log.i("开始获取好友数据", ""+currentUser);     
    	 if(!file.equals(null) && ac.queryTable3()==true){
 		 try {
 		List<ContactPerson> cpList;
 		cpList=ac.queryTableInfo3(currentUser);
		 if(cpList.size()>0 && cpList!=null 
			&& cpList.get(0).getPrivateGroup()!=null){
		ArrayList=cpList.get(0).getPrivateGroups();
		if(cpList.get(0).getPrivateFriend()!=null){
		list=cpList.get(0).getPrivateFriends();	
		Log.i("本地私人好友信息", ""+ArrayList+list);
		}else{
		    networkDatabase(1);  
		 }
		adapter = new PrivateManagerDbAdater(getActivity(),ArrayList,list);          
		expandableListView.setAdapter(adapter);
		 	 		
		 }else{
		    networkDatabase(0);  
		 }
		} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				    	               
	     }else{ 
	    	
	    	 networkDatabase(2); 
	    			
	}    	 
	}

    /**
     * 获取网络数据
     * @param flag 0 获取组名 ，1 获取联系人 ，2 更新整张表
     */
    private void networkDatabase(final int flag) {

   	 Log.i("开始获取好友数据3", ""+currentUser);
	 BmobQuery<Contacts> query = new BmobQuery<Contacts>();
	 query.addWhereEqualTo("userId", currentUser);
	 final ContactPerson cp=new ContactPerson();
	final DBAccess ac =new DBAccess(getActivity());	
	 query.findObjects(getActivity(),new FindListener<Contacts>() {

			@Override
			public void onError(int arg0, String arg1) {					
			adapter = new PrivateManagerDbAdater(getActivity(),null,null);          
			expandableListView.setAdapter(adapter);	
						
					}

		@Override
		public void onSuccess(List<Contacts> c) {					
		if(c.size()>0 ){
		for(Contacts cs : c){
	    if(flag==0){
	    if(cs.getPrivateGroup()!=null){
		ArrayList=cs.getPrivateGroup();	
		ac.updateTable3Single(currentUser, 
		ConstantValue.DB_MetaData3.PRIVATE_GROUP, cp.getClientGroup());
	    	}
	    
	    }else if(flag==1){
	    	
	    	if(cs.getPrivateFriend()!=null){
	    	list=cs.getPrivateFriend();    		
	    	ac.updateTable3Single(currentUser, 
	    	ConstantValue.DB_MetaData3.PRIVATE_FRIEND, cp.getPrivateFriend());		
	    	}
	    }else{	    
	    ArrayList=cs.getPrivateGroup();
	    list=cs.getPrivateFriend();	
	    cp.setObjectIds(cs.getObjectId());
	    cp.setUserId(cs.getUserId());
	    try {
			cp.setClientGroups(cs.getClientGroup());
			cp.setClientFriends(cs.getClientFriend());
			} catch (IOException e) {
			e.printStackTrace();
		}
	    }
		Log.i("网络好友数据", ""+cs.getPrivateGroup()+cs.getPrivateFriend()+"/"+flag);	
				}
		adapter = new PrivateManagerDbAdater(getActivity(),ArrayList,list);          
		expandableListView.setAdapter(adapter);
		
		//将网络获取的好友列表添加到本地	
		try {
		cp.setPrivateGroups(ArrayList);
		cp.setPrivateFriends(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(flag==2){
	    ac.insertTable3(cp);	
		}
		}												
				}   					
	      }); 

	 }	
		
    /**
     * 弹出一个输入框,添加分组
     */
      private void onInputDialog(){
      final EditText input = new EditText(getActivity());
      AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
      builder.setTitle("输入组名：");
      builder.setView(input);
      builder.setPositiveButton("确定", null);
      builder.setNegativeButton("取消", null); 
      final AlertDialog alertDialog = builder.create(); 
      alertDialog.show();
     
      alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
 	    @Override
 	    public void onClick(View v) {
 	 String value = input.getText().toString().trim();
 	 if (value.length() == 0) {
 	 Toast.makeText(getActivity(), "不能为空", Toast.LENGTH_SHORT).show();	   
 	     return;
 	 }
 	 ArrayList.add(input.getText().toString());
 	 updateData(ArrayList,null);
 	 alertDialog.dismiss();
 	    }
     });
      }
    
    /**
     * 弹出一个删除分组的对话框
     * @param groupIndex 组的下标
     */
      private void deleteGroup(final int groupIndex) {
            
		 
		 if(list.get(groupIndex)!=null &&list.get(groupIndex).size()>0){
		 //当删除的组中存在好友时，创建一个新的对话框来提醒用户
		 AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());	
		 builder.setTitle("该组存在好友，确定要删除吗？");
		 builder.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ArrayList.remove(groupIndex);
				list.remove(groupIndex);
				updateData(ArrayList,list);						
			}
		});	
		 builder.setNegativeButton("取消", null);
		 builder.show();
		      }else{
		    	ArrayList.remove(groupIndex);
		    	list.remove(groupIndex);
		        updateData(ArrayList,list);  
		      }
			}

   /**
    * 重命名
    */
  	private void renGroup(final int groupIndex) {
  		final EditText input = new EditText(getActivity());
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("输入新组名：");
        builder.setView(input);
        builder.setPositiveButton("确定",null);
        builder.setNegativeButton("取消", null);
        final AlertDialog alertDialog= builder.create();
        alertDialog.show();	
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        .setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			String value= input.getText().toString().trim();
			if(value.length()==0){
			Toast.makeText(getActivity(), "不能为空", Toast.LENGTH_SHORT);
			return;
			}
			 ArrayList.set(groupIndex, value);
		 	 updateData(ArrayList,null);
		 	 alertDialog.dismiss();
			}
		});  		
      
  	} 
      
    /**
     * 更新数据
     * @param ArrayLIst 更新组
     * @param list 更新联系人
     */
     private void  updateData(final ArrayList<String> ArrayList,
    		 final ArrayList<List<List<String>>> list){
    	  final DBAccess ac=new DBAccess(getActivity());
          String ObjectId= ac.queryTable3ObjectId(currentUser);
  		
          //更新网络数据
  		Contacts cs=new Contacts();
  		if(ArrayList!=null){
  		cs.setPrivateGroup(ArrayList);
  		}
  		if(list!=null){
  		cs.setPrivateFriend(list);
  		}
  		cs.update(getActivity(), ObjectId, new UpdateListener() {
  			
  			@Override
  			public void onSuccess() {				
  		Toast.makeText(getActivity(), "操作成功", Toast.LENGTH_SHORT).show();	
  		adapter.notifyDataSetChanged();	
  			
  		
  	   //更新本地数据
  		ContactPerson cp=new ContactPerson();
  		
  		if(ArrayList!=null){
  		try {
  			cp.setPrivateGroups(ArrayList);
  		} catch (IOException e) {  			
  			e.printStackTrace();
  		} 
  		ac.updateTable3Single(currentUser, 
  		ConstantValue.DB_MetaData3.PRIVATE_GROUP,cp.getPrivateGroup());  		
  		}
  		
  		if(list!=null){
  	  	try {
  			cp.setPrivateFriends(list);
  			} catch (IOException e) {			
  			e.printStackTrace();
  			}
  	  ac.updateTable3Single(currentUser,
  	  ConstantValue.DB_MetaData3.PRIVATE_FRIEND,cp.getPrivateFriend());  			
  		}  	
  		
  		}
  			
  			@Override
  			public void onFailure(int arg0, String arg1) {
  			Log.i("操作失败", ""+arg0+arg1);
  		 Toast.makeText(getActivity(), "操作失败", Toast.LENGTH_SHORT).show();	
  			}
  		});
  		
  		
  	  	  	    
  		}
     
    	 
     /**
      * 删除好友 
      */
 	private void deleteFriend(final int groupIndex,final int childIndex,String childName) {
 	 AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());	
 	 builder.setTitle("确定要删除好友“"+childName+"”吗?");	
 	 builder.setNegativeButton("取消", null);
 	 builder.setPositiveButton("确定", new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
		list.get(groupIndex).remove(childIndex);	
		Log.i("显示删除好友之后的列表", "list="+list);
		updateData(null,list);	
		}
	});
 	 builder.show();
 	}

 	/**
 	 * 移动好友
 	 * @param groupIndex 当前好友所在组的下标
 	 * @param toGroupIndex 移动到这个组的下标
 	 * @param childIndex 当前好友所在组的联系人下标
 	 */
	private void moveFriend(int groupIndex,int toGroupIndex,int childIndex) {
	    List<List<String>> temp=new ArrayList<List<String>>(); 
	    List<String> temp2=new ArrayList<String>();
	    temp.add(list.get(groupIndex).get(childIndex));
	    for(int i=0;i<list.get(groupIndex).get(childIndex).size();i++){
		temp2.add(list.get(groupIndex).get(childIndex).get(i));	
	    }
		list.get(groupIndex).remove(childIndex);
		Log.i("移动好友的信息", ""+temp+"/list.size="+list.size()
			+"/toGroupIndex="+toGroupIndex);
		if(list.size()<=toGroupIndex){		
		//中间有空值，补充toGroupIndex-1的下标
		for(int i=0;i<toGroupIndex;i++){
		 if(list.size()>i){
		    
		   }else{
		   list.add(null);	
		   }
		Log.i("好友列表", ""+list);
		 }
		//等于toGroupIndex时添加数据
		list.add(temp);
		Log.i("好友列表2", ""+list);
		}else{
		
		
		list.get(toGroupIndex).add(temp2);
		Log.i("好友列表3", ""+list);
		}
	
        updateData(null,list);
	} 
    
	/**
	 * 创建一个移动选择分组，调用移动好友方法
	 * @param childName 好友名字
	 * @param groupIndex 当前组的下标
	 * @param childIndex 当前组的联系人下标
	 */
	private void addGroup(String childName,final int groupIndex,final int childIndex) {
	AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());	
    builder.setTitle("将好友“"+childName+"”移动到：");
	String[] items =new String[ArrayList.size()-1];
    for(int i=0;i<ArrayList.size();i++){
    	if(i==0){
    	}else{
    	items[i-1]= ArrayList.get(i);	
    	}
    }
    builder.setItems(items, new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {			
		moveFriend(groupIndex,which+1,childIndex);	
		}
	});
    builder.setNegativeButton("取消", null);
    builder.show();
	}
	
	/**
	 * 添加备注
	 * @param childName 好友名字
	 * @param groupIndex 当前所在组的下标
	 * @param childIndex 当前所在组的联系人下标
	 */
	private void addMark(String childName,final int groupIndex,final int childIndex) {
	 
		final EditText input = new EditText(getActivity());
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("给“"+childName+"”输入备注名：");
        builder.setView(input);
        builder.setPositiveButton("确定",null);
        builder.setNegativeButton("取消", null);
        final AlertDialog alertDialog= builder.create();
        alertDialog.show();	
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        .setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			String value= input.getText().toString().trim();
			if(value.length()==0){
			Toast.makeText(getActivity(), "不能为空", Toast.LENGTH_SHORT);
			return;
			}
			 list.get(groupIndex).get(childIndex).add(value);
		 	 updateData(ArrayList,null);
		 	 alertDialog.dismiss();
			}
		}); 	
		
    }
	
    
		public static byte[] serialize(Object object) {

    	ObjectOutputStream oos = null;

    	ByteArrayOutputStream baos = null;

    	try {

    	//序列化

    	baos = new ByteArrayOutputStream();

    	oos = new ObjectOutputStream(baos);

    	oos.writeObject(object);

    	byte[] bytes = baos.toByteArray();
    	 oos.close();
    	 baos.close();

    	return bytes;

    	} catch (Exception e) {

    	 
    	}

    	return null;

    	}
   
}
	 


