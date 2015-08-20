package com.example.Adapter;

import java.util.List;
import java.util.Map;

import com.example.clientmessagemanage.R;

import android.content.Context;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AddFriendAdapter extends BaseAdapter{
	private List<Map<String, Object>> data; 
	private LayoutInflater layoutInflater;
	private Context context;
	private ScrollToLastCallBack mScrollToLastCallBack=null; 
 
    public interface MyOnClickListener {  
    	    public void onClick();  
    	}  
 
    public interface ScrollToLastCallBack  
    {  
        public void onScrollToLast(int pos);  
    } 


	public AddFriendAdapter(Context context,List<Map<String, Object>> data,
			final ScrollToLastCallBack scrollToLastCallBack){  
      this.context=context;  
      this.data=data;  
      this.layoutInflater=LayoutInflater.from(context);  
      mScrollToLastCallBack=scrollToLastCallBack;
	} 

	 /** 
	   * ������ϣ���Ӧlist.xml�еĿؼ� 
	   * @author Administrator 
	   */  
	public final class Control{  
	  public ImageView image;  
	  public TextView title;  
	  public Button button;
	  public TextView idhao;
	  public TextView id;
	    } 

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	/*
	 * ���ĳһλ�õ����� 
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);  
	}

	/** 
	  * ���Ψһ��ʶ 
	  */ 

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	//ע��ԭ��getView�����е�int position�����Ƿ�final�ģ����ڸ�Ϊfinal  
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final Control control;  
		if(convertView==null){
			
			control=new Control();
			//��������ʵ�������
			convertView =layoutInflater.inflate(R.layout.adapter_add_friend_parts_list, null);
			control.image=(ImageView)convertView.findViewById(R.id.head_img);
			control.title=(TextView)convertView.findViewById(R.id.add_name_text);
			control.button=(Button)convertView.findViewById(R.id.add_button);
		    control.idhao=(TextView)convertView.findViewById(R.id.add_idhao_text);
			control.id=(TextView)convertView.findViewById(R.id.add_id_text);
			//control.button.setTag(position); 

			convertView.setTag(control);			 
		}else{
			
			control=(Control)convertView.getTag();
		}		

		//������  
		control.image.setBackgroundResource((Integer)data.get(position).get("image"));  
		control.title.setText((String)data.get(position).get("title"));  
        control.id.setText((String)data.get(position).get("objectId"));
        control.button.setText((String)data.get(position).get("button"));
        
        //final int currentPosition=position; 
                 
        //��Button��ӵ����¼�  ���Button֮��ListView��ʧȥ����  ��Ҫ��ֱ�Ӱ�Button�Ľ���ȥ��   
        control.button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			mScrollToLastCallBack.onScrollToLast(position);  	
				
			}
		});
		return convertView;
	}
   
}	
