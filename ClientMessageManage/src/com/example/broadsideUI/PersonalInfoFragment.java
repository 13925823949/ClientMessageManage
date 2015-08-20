package com.example.broadsideUI;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.example.bean.UserInfo;
import com.example.clientmessagemanage.R;
import com.example.util.ConstantValue;
import com.example.util.DBAccess;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PersonalInfoFragment extends Fragment{
	

    private TextView show_name;
	private TextView show_sex;
	private TextView show_age;
	private TextView show_QQ;
	private TextView show_email;
	private TextView show_phone;
	private TextView show_hobby;
	private TextView show_province;
	private TextView show_dateOfBirth;
	private TextView show_contactAddress;
	private TextView show_maritalStatus;
	private TextView show_memorial;
	private TextView show_day;
	private TextView show_memorial2;
	private TextView show_day2;
	private TextView show_memorial3;
	private TextView show_day3;
	
	private List<UserInfo> list;

    private String See_userId;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        View personalInfoLayout = inflater.inflate(R.layout.broadside_person_personal_info, container, false);  
               
        show_name=(TextView)personalInfoLayout.findViewById(R.id.show_name);
        show_sex=(TextView)personalInfoLayout.findViewById(R.id.show_sex);
        show_age=(TextView)personalInfoLayout.findViewById(R.id.show_age);
        show_QQ=(TextView)personalInfoLayout.findViewById(R.id.show_QQ);
        show_phone=(TextView)personalInfoLayout.findViewById(R.id.show_phone);
        show_email=(TextView)personalInfoLayout.findViewById(R.id.show_email);
        show_hobby=(TextView)personalInfoLayout.findViewById(R.id.show_hobby);
        show_province=(TextView)personalInfoLayout.findViewById(R.id.show_province);
        show_dateOfBirth=(TextView)personalInfoLayout.findViewById(R.id.show_dateOfBirth);
        show_contactAddress=(TextView)personalInfoLayout.findViewById(R.id.show_contactAddress);
        show_maritalStatus=(TextView)personalInfoLayout.findViewById(R.id.show_maritalStatus);
        show_memorial=(TextView)personalInfoLayout.findViewById(R.id.show_memorial);
        show_day=(TextView)personalInfoLayout.findViewById(R.id.show_day);
        show_memorial2=(TextView)personalInfoLayout.findViewById(R.id.show_memorial2);
        show_day2=(TextView)personalInfoLayout.findViewById(R.id.show_day2);
        show_memorial3=(TextView)personalInfoLayout.findViewById(R.id.show_memorial3);
        show_day3=(TextView)personalInfoLayout.findViewById(R.id.show_day3);
        
        showInfo();
        return personalInfoLayout;          
    }

	private void showInfo() {
		//判断本地数据库是否为空
		File file = getActivity().getExternalCacheDir();
		if(!file.equals(null)){ 
		//先获取activity的名字来判断查询方式
		Intent intent = getActivity().getIntent();
		String className = getArguments() != null ? getArguments().getString("classname") : null;	
		if (className == null) { 
			className = intent.getComponent().getClassName();
			}
		//本地加载用户自己的信息
		if (className.equals(PersonInfoActivity.class.getName())){
			//在读取SharedPreferences数据前要实例化出一个SharedPreferences对象 
			SharedPreferences sharedPreferences= getActivity().getSharedPreferences("test", 
			Activity.MODE_PRIVATE); 
			// 使用getString方法获得value，注意第2个参数是value的默认值 
			See_userId =sharedPreferences.getString("currentUser", ""); 
			
			DBAccess access=new DBAccess(getActivity());
			list= access.queryInfo(ConstantValue.DB_MetaData.USER_ID, See_userId);
			showUserInfo();
		}
		//网络查询所查看的用户信息
		 if(className.equals(SeeInfoActivity.class.getName())){
			See_userId= ((SeeInfoActivity)getActivity()).getSee_userId();
			
			BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
			query.addWhereEqualTo(ConstantValue.DB_MetaData.USER_ID,See_userId);
			query.findObjects(getActivity(), new FindListener<UserInfo>() {

						@Override
						public void onError(int arg0, String arg1) {							
							
						}

						@Override
						public void onSuccess(List<UserInfo> uinfo) {						
                      for(UserInfo info : uinfo){
                    	show_name.setText(info.getName());
              		    show_sex.setText(info.getSex()); 
              		    show_age.setText(info.getAge()); 
              		    show_QQ.setText(info.getQQ());
              		    show_phone.setText(info.getPhone());
              		    show_email.setText(info.getEmail());
              		    show_hobby.setText(info.getHobby());
              		    show_province.setText(info.getProvince());
              		    show_dateOfBirth.setText(info.getDateOfBirth());
              		    show_contactAddress.setText(info.getContactAddress());
              		    show_maritalStatus.setText(info.getMaritalStatus());
              		    show_memorial.setText(info.getMemorial());
              		    show_day.setText(info.getDay());
              		    show_memorial2.setText(info.getMemorial2());
              		    show_day2.setText(info.getDay2());
              		    show_memorial3.setText(info.getMemorial3());
              		    show_day3.setText(info.getDay3());
                      }
						}				
			});
		}
				
		}
	}

	private void showUserInfo() {
     if(list.size()==0){
			
		}else{
		show_name.setText(list.get(0).getName());
		show_sex.setText(list.get(0).getSex());
		show_age.setText(list.get(0).getAge());
		show_QQ.setText(list.get(0).getQQ());
		show_phone.setText(list.get(0).getPhone());
		show_hobby.setText(list.get(0).getHobby());
		show_email.setText(list.get(0).getEmail());
		show_province.setText(list.get(0).getProvince());
		show_dateOfBirth.setText(list.get(0).getDateOfBirth());
		show_contactAddress.setText(list.get(0).getContactAddress());
		show_maritalStatus.setText(list.get(0).getMaritalStatus());
		show_memorial.setText(list.get(0).getMemorial());
		show_day.setText(list.get(0).getDay());
		show_memorial2.setText(list.get(0).getMemorial2());
		show_day2.setText(list.get(0).getDay2());
		show_memorial3.setText(list.get(0).getMemorial3());
		show_day3.setText(list.get(0).getDay3());
		}	
		
	}


		
		
	 
}
