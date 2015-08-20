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

public class ProfessionInfoFragment extends Fragment{

    private TextView show_profession;
	private TextView show_companyName;
	private TextView show_companyAddress;
	private TextView show_department;
	private TextView show_yearsOfWorking;
	
	private List<UserInfo> list;
    private String See_userId;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        View professionLayout = inflater.inflate(R.layout.broadside_person_profession_info, container, false);  
    
        show_profession=(TextView)professionLayout.findViewById(R.id.show_profession);
        show_companyName=(TextView)professionLayout.findViewById(R.id.show_companyName);
        show_companyAddress=(TextView)professionLayout.findViewById(R.id.show_companyAddress);
        show_department=(TextView)professionLayout.findViewById(R.id.show_department);
        show_yearsOfWorking=(TextView)professionLayout.findViewById(R.id.show_yearsOfWorking);
        
        showInfo();
        return professionLayout;  
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
							for( UserInfo info : uinfo){
								show_profession.setText(info.getProfession());
							    show_companyName.setText(info.getCompanyName());
							    show_companyAddress.setText(info.getCompanyAddress());
							    show_department.setText(info.getDepartment());
							    show_yearsOfWorking.setText(info.getYearsOfWorking());
							}
							}				
				});
			}

		}
		
	}

	private void showUserInfo() {
		if(list.size()==0){
			
		}else{
			show_profession.setText(list.get(0).getProfession());
			show_companyName.setText(list.get(0).getCompanyName());
			show_companyAddress.setText(list.get(0).getCompanyAddress());
			show_department.setText(list.get(0).getDepartment());
			show_yearsOfWorking.setText(list.get(0).getYearsOfWorking());	
		}
	} 
		
	
}
