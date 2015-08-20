package com.example.Adapter;

import com.example.clientmessagemanage.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BroadsideAdapter extends BaseAdapter{
	private LayoutInflater mInflater; 
	String[] items;
	Context context;
	private TextView mTextView;   
	public BroadsideAdapter(Context context,String[] items){  
	this.context=context;  
	this.items=items;  
	              
	          }  

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		 if (convertView == null)  
		  {  
	        convertView = mInflater.inflate(R.layout.activity_log_in, parent,  
	                 false);   
	      mTextView = (TextView) convertView.findViewById(R.id.textView1);  

		
	      mTextView.setText(items[position]);  
	      mTextView.setTextSize(15);  
		}
     
		return convertView;  

	}
	

}
