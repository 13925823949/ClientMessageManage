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
    private ArrayList<String> ArrayList =new ArrayList<String>();//��ȡ��
    private ArrayList<List<List<String>>> list=new ArrayList<List<List<String>>>();//��ȡ��ϵ��
    private PrivateManagerDbAdater adapter;
    private ExpandableListView expandableListView;
	private String currentUser;
    
	private static final int ADD_GROUP=0;//��ӷ���
	private static final int DELETE_GROUP=1;//ɾ������
	private static final int REN_GROUP=2;//������
	private static final int ADD_FRIEND_GROUP=3;//��Ӻ���
	private static final int DELETE_FRIEND=1;//ɾ������
	private static final int MOVE=0;//�ƶ�����
	private static final int MARK=2;//��ע
	

    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) { 
    	
       privateLayout = inflater.inflate(R.layout.private_layout, container, false);  
       expandableListView = (ExpandableListView)privateLayout.findViewById(R.id.privateList);
       
     //��ȡ��ǰ��¼�û�
       SharedPreferences sharedPreferences= getActivity().getSharedPreferences("test", 
       Activity.MODE_PRIVATE); 
       currentUser =sharedPreferences.getString("currentUser", "");
      setHasOptionsMenu(true);
      addListInfo();
       // ���ó���ʱ���¼�   
      registerForContextMenu(expandableListView);  

                            
        //����item����ļ�����
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

   //�����߼�
   if (type == 0) {// ���鳤���¼�
	  String title=((TextView)info.targetView.findViewById(R.id.groupName)).getText().toString();

	  if(!title.equals("δ����")||!title.equals("�ر��ע")){	  
	  menu.setHeaderTitle(title);
	  menu.add(0, ADD_GROUP, 0, "��ӷ���");
	  menu.add(0, DELETE_GROUP, 0, "ɾ������");
	  menu.add(0, REN_GROUP, 0, "������");
	  menu.add(0, ADD_FRIEND_GROUP, 0, "�����ϵ��");
	  menu.add(0,4,0,"ȡ��");
	  }
   } else if (type == 1) {// ���������б���
	   String title=((TextView)info.targetView.findViewById(R.id.child_name)).getText().toString();
	   String id=((TextView)info.targetView.findViewById(R.id.child_id)).getText().toString(); 
	          	
	    int groupIndex=ExpandableListView.getPackedPositionGroup(info.packedPosition);
	    Log.i("������ȡ�±�", "groupIndex="+groupIndex+"/id="+id);
	    if(groupIndex!=0){	
	    menu.setHeaderTitle(title);
		menu.add(0, MOVE, 0, "�ƶ���ϵ�˵�...");
		menu.add(0, DELETE_FRIEND, 0, "ɾ����ϵ��");
		menu.add(0, MARK, 0, "��ע");
        menu.add(0, 3, 0,"ȡ��");
	   }else{
	   menu.setHeaderTitle("�ƶ���...");
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
	  
	Log.i("������ȡ�±�2", "groupIndex="+groupIndex); 
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
    	//������ϵ�˵�����
       	String childName=((TextView)info.targetView.findViewById(R.id.child_name)).getText().toString();
       	//��������±�
       	int groupIndex=ExpandableListView.getPackedPositionGroup(info.packedPosition);
       	//������ϵ�˵��±�
       	int childIndex=(int) ExpandableListView.getPackedPositionChild(info.packedPosition);
		Log.i("�¼�����", "groupIndex="+groupIndex+"/childIndex="+childIndex+"/childName="+childName);
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
     * ������ϵ���б�
     */
    private void addListInfo() {
    	 	
    	 File file = getActivity().getExternalCacheDir();  
    	 DBAccess ac =new DBAccess(getActivity());
    	 Log.i("��ʼ��ȡ��������", ""+currentUser);     
    	 if(!file.equals(null) && ac.queryTable3()==true){
 		 try {
 		List<ContactPerson> cpList;
 		cpList=ac.queryTableInfo3(currentUser);
		 if(cpList.size()>0 && cpList!=null 
			&& cpList.get(0).getPrivateGroup()!=null){
		ArrayList=cpList.get(0).getPrivateGroups();
		if(cpList.get(0).getPrivateFriend()!=null){
		list=cpList.get(0).getPrivateFriends();	
		Log.i("����˽�˺�����Ϣ", ""+ArrayList+list);
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
     * ��ȡ��������
     * @param flag 0 ��ȡ���� ��1 ��ȡ��ϵ�� ��2 �������ű�
     */
    private void networkDatabase(final int flag) {

   	 Log.i("��ʼ��ȡ��������3", ""+currentUser);
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
		Log.i("�����������", ""+cs.getPrivateGroup()+cs.getPrivateFriend()+"/"+flag);	
				}
		adapter = new PrivateManagerDbAdater(getActivity(),ArrayList,list);          
		expandableListView.setAdapter(adapter);
		
		//�������ȡ�ĺ����б���ӵ�����	
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
     * ����һ�������,��ӷ���
     */
      private void onInputDialog(){
      final EditText input = new EditText(getActivity());
      AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
      builder.setTitle("����������");
      builder.setView(input);
      builder.setPositiveButton("ȷ��", null);
      builder.setNegativeButton("ȡ��", null); 
      final AlertDialog alertDialog = builder.create(); 
      alertDialog.show();
     
      alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
 	    @Override
 	    public void onClick(View v) {
 	 String value = input.getText().toString().trim();
 	 if (value.length() == 0) {
 	 Toast.makeText(getActivity(), "����Ϊ��", Toast.LENGTH_SHORT).show();	   
 	     return;
 	 }
 	 ArrayList.add(input.getText().toString());
 	 updateData(ArrayList,null);
 	 alertDialog.dismiss();
 	    }
     });
      }
    
    /**
     * ����һ��ɾ������ĶԻ���
     * @param groupIndex ����±�
     */
      private void deleteGroup(final int groupIndex) {
            
		 
		 if(list.get(groupIndex)!=null &&list.get(groupIndex).size()>0){
		 //��ɾ�������д��ں���ʱ������һ���µĶԻ����������û�
		 AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());	
		 builder.setTitle("������ں��ѣ�ȷ��Ҫɾ����");
		 builder.setPositiveButton("ȷ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ArrayList.remove(groupIndex);
				list.remove(groupIndex);
				updateData(ArrayList,list);						
			}
		});	
		 builder.setNegativeButton("ȡ��", null);
		 builder.show();
		      }else{
		    	ArrayList.remove(groupIndex);
		    	list.remove(groupIndex);
		        updateData(ArrayList,list);  
		      }
			}

   /**
    * ������
    */
  	private void renGroup(final int groupIndex) {
  		final EditText input = new EditText(getActivity());
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("������������");
        builder.setView(input);
        builder.setPositiveButton("ȷ��",null);
        builder.setNegativeButton("ȡ��", null);
        final AlertDialog alertDialog= builder.create();
        alertDialog.show();	
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        .setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			String value= input.getText().toString().trim();
			if(value.length()==0){
			Toast.makeText(getActivity(), "����Ϊ��", Toast.LENGTH_SHORT);
			return;
			}
			 ArrayList.set(groupIndex, value);
		 	 updateData(ArrayList,null);
		 	 alertDialog.dismiss();
			}
		});  		
      
  	} 
      
    /**
     * ��������
     * @param ArrayLIst ������
     * @param list ������ϵ��
     */
     private void  updateData(final ArrayList<String> ArrayList,
    		 final ArrayList<List<List<String>>> list){
    	  final DBAccess ac=new DBAccess(getActivity());
          String ObjectId= ac.queryTable3ObjectId(currentUser);
  		
          //������������
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
  		Toast.makeText(getActivity(), "�����ɹ�", Toast.LENGTH_SHORT).show();	
  		adapter.notifyDataSetChanged();	
  			
  		
  	   //���±�������
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
  			Log.i("����ʧ��", ""+arg0+arg1);
  		 Toast.makeText(getActivity(), "����ʧ��", Toast.LENGTH_SHORT).show();	
  			}
  		});
  		
  		
  	  	  	    
  		}
     
    	 
     /**
      * ɾ������ 
      */
 	private void deleteFriend(final int groupIndex,final int childIndex,String childName) {
 	 AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());	
 	 builder.setTitle("ȷ��Ҫɾ�����ѡ�"+childName+"����?");	
 	 builder.setNegativeButton("ȡ��", null);
 	 builder.setPositiveButton("ȷ��", new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
		list.get(groupIndex).remove(childIndex);	
		Log.i("��ʾɾ������֮����б�", "list="+list);
		updateData(null,list);	
		}
	});
 	 builder.show();
 	}

 	/**
 	 * �ƶ�����
 	 * @param groupIndex ��ǰ������������±�
 	 * @param toGroupIndex �ƶ����������±�
 	 * @param childIndex ��ǰ�������������ϵ���±�
 	 */
	private void moveFriend(int groupIndex,int toGroupIndex,int childIndex) {
	    List<List<String>> temp=new ArrayList<List<String>>(); 
	    List<String> temp2=new ArrayList<String>();
	    temp.add(list.get(groupIndex).get(childIndex));
	    for(int i=0;i<list.get(groupIndex).get(childIndex).size();i++){
		temp2.add(list.get(groupIndex).get(childIndex).get(i));	
	    }
		list.get(groupIndex).remove(childIndex);
		Log.i("�ƶ����ѵ���Ϣ", ""+temp+"/list.size="+list.size()
			+"/toGroupIndex="+toGroupIndex);
		if(list.size()<=toGroupIndex){		
		//�м��п�ֵ������toGroupIndex-1���±�
		for(int i=0;i<toGroupIndex;i++){
		 if(list.size()>i){
		    
		   }else{
		   list.add(null);	
		   }
		Log.i("�����б�", ""+list);
		 }
		//����toGroupIndexʱ�������
		list.add(temp);
		Log.i("�����б�2", ""+list);
		}else{
		
		
		list.get(toGroupIndex).add(temp2);
		Log.i("�����б�3", ""+list);
		}
	
        updateData(null,list);
	} 
    
	/**
	 * ����һ���ƶ�ѡ����飬�����ƶ����ѷ���
	 * @param childName ��������
	 * @param groupIndex ��ǰ����±�
	 * @param childIndex ��ǰ�����ϵ���±�
	 */
	private void addGroup(String childName,final int groupIndex,final int childIndex) {
	AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());	
    builder.setTitle("�����ѡ�"+childName+"���ƶ�����");
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
    builder.setNegativeButton("ȡ��", null);
    builder.show();
	}
	
	/**
	 * ��ӱ�ע
	 * @param childName ��������
	 * @param groupIndex ��ǰ��������±�
	 * @param childIndex ��ǰ���������ϵ���±�
	 */
	private void addMark(String childName,final int groupIndex,final int childIndex) {
	 
		final EditText input = new EditText(getActivity());
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("����"+childName+"�����뱸ע����");
        builder.setView(input);
        builder.setPositiveButton("ȷ��",null);
        builder.setNegativeButton("ȡ��", null);
        final AlertDialog alertDialog= builder.create();
        alertDialog.show();	
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        .setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			String value= input.getText().toString().trim();
			if(value.length()==0){
			Toast.makeText(getActivity(), "����Ϊ��", Toast.LENGTH_SHORT);
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

    	//���л�

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
	 


