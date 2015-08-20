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

public class EducationInfoFragment extends Fragment{

    private TextView show_graduationDate;
	private TextView show_school;
	private TextView show_userClass;
	private TextView show_major;
	private TextView show_diploma;
	
	private List<UserInfo> list;
	private String See_userId;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        View educationLayout = inflater.inflate(R.layout.broadside_person_education_info, container, false);  
        
        show_diploma=(TextView)educationLayout.findViewById(R.id.show_diploma);
        show_major=(TextView)educationLayout.findViewById(R.id.show_major);
        show_userClass=(TextView)educationLayout.findViewById(R.id.show_userClass);
        show_school=(TextView)educationLayout.findViewById(R.id.show_school);
        show_graduationDate=(TextView)educationLayout.findViewById(R.id.show_graduationDate);
        
        showInfo();
        return educationLayout;  
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
								show_diploma.setText(info.getDiploma());
							    show_major.setText(info.getMajor());
							    show_userClass.setText(info.getUserClass());
							    show_school.setText(info.getSchool());
							    show_graduationDate.setText(info.getGraduationDate());	
							}
							}				
				});
			}

		}
		
	}

	private void showUserInfo() {
		if(list.size()==0){
			
		}else{
			show_diploma.setText(list.get(0).getDiploma());
			show_major.setText(list.get(0).getMajor());
			show_userClass.setText(list.get(0).getUserClass());
			show_school.setText(list.get(0).getSchool());
			show_graduationDate.setText(list.get(0).getGraduationDate());	
		}
	} 
	
}
