package com.example.Adapter;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.listener.UpdateListener;

import com.example.bean.SendRequest;
import com.example.clientmessagemanage.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RequestDisposeAdapter extends BaseAdapter{
	private List<Map<String, Object>> data2; 
	private LayoutInflater layoutInflater;
	private Context context;

	private CallBackFlag callBackFlag;
	public RequestDisposeAdapter(Context context,List<Map<String, Object>> data2,CallBackFlag mcallBackFlag){  
      this.context=context;  
      this.data2=data2;  
      this.layoutInflater=LayoutInflater.from(context);  
      callBackFlag=mcallBackFlag;
	} 
	//回调函数接口
	public interface CallBackFlag{
		public void onClick(int flag,int pos);
	}
	
	 /** 
	   * 组件集合，对应list.xml中的控件 
	   * @author Administrator 
	   */  
	public final class Control2{  
	  public ImageView image2;  
	  public TextView name2; 
	  public TextView state2;
	  public Button consent_button;
	  public Button deny_button;
	  public TextView id2;
	  public TextView callBackShow;
	  public TextView request_state_text;
	} 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data2.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data2.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Control2 control2 =null;
		
		if(convertView==null){
			control2=new Control2();
			//获得组件，实例化组件
			convertView =layoutInflater.inflate(R.layout.adapter_request_dispose_list, null);
			control2.image2=(ImageView)convertView.findViewById(R.id.request_head_img);
			control2.name2=(TextView)convertView.findViewById(R.id.request_name_text);
			control2.state2=(TextView)convertView.findViewById(R.id.request_state_text);
			control2.consent_button=(Button)convertView.findViewById(R.id.request_consent_button);
			control2.deny_button=(Button)convertView.findViewById(R.id.request_deny_button);
			control2.id2=(TextView)convertView.findViewById(R.id.add_id_text2);
			control2.callBackShow=(TextView)convertView.findViewById(R.id.callBack_show_text);
			control2.request_state_text=(TextView)convertView.findViewById(R.id.request_state_text);
			convertView.setTag(control2);
		}else{
			control2=(Control2)convertView.getTag();
		}
		
		//绑定数据  
		control2.image2.setBackgroundResource((Integer)data2.get(position).get("image2"));  
		control2.name2.setText((String)data2.get(position).get("name"));  
	    control2.id2.setText((String)data2.get(position).get("requestObject"));
        control2.callBackShow.setText((String)data2.get(position).get("callBackShow"));
	    control2.request_state_text.setText((String)data2.get(position).get("requestStateShow"));
        String b=(String)data2.get(position).get("boolean");
        String flag=(String)data2.get(position).get("addFlag");
        Log.i("flag", ""+flag+"/"+b+"/"+control2.name2.getText().toString());
	    if(b.equals("false")|| flag.equals("2") || flag.equals("3")){
	    control2.consent_button.setVisibility(View.GONE);
	    control2.deny_button.setVisibility(View.GONE);
	    control2.callBackShow.setVisibility(View.VISIBLE);
	    }

        //2代表同意       
        control2.consent_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			callBackFlag.onClick(2,position);
			}
		});
        //3代表拒绝
        control2.deny_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {				
			callBackFlag.onClick(3,position);
			}
		});
		return convertView;
	}
}
