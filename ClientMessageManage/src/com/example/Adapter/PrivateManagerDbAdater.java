package com.example.Adapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.example.clientmessagemanage.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class PrivateManagerDbAdater extends BaseExpandableListAdapter{

	private Context context;
	private LayoutInflater layoutInflater;
	//组视图的显示文字
	private ArrayList<String> privateGroup = new ArrayList<String>();
    //子视图显示文字
    private ArrayList<List<List<String>>> contactsGather =new ArrayList<List<List<String>>>();
  
    public PrivateManagerDbAdater(Context context,ArrayList<String>
    privateGroup,ArrayList<List<List<String>>> contactsGather) {
	
    	this.context = context;	
    	this.privateGroup=privateGroup;
    	this.contactsGather=contactsGather;
    	this.layoutInflater=LayoutInflater.from(context);
    	
	}
	//自己定义一个获得文字信息的方法(组)未使用
    TextView getGroupTextView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, 64);
        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(80, 5, 3, 5); //textView左、上、右、下的距离
        textView.setTextSize(20);
        textView.setMinLines(10);
        textView.setTextColor(Color.BLACK);
        return textView;   	        
    }
    //自己定义一个获得文字信息的方法(child)
    TextView getChildTextView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, 64);
        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(36, 0, 0, 0);
        textView.setTextSize(15);
        textView.setTextColor(Color.BLACK);
        return textView;   	        
    }
   
    //重写ExpandableListAdapter中的各个方法
    @Override
    public int getGroupCount() {

        return privateGroup.size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return privateGroup.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        
    if(contactsGather!=null && contactsGather.isEmpty()==false
     && contactsGather.size()>groupPosition){  
    	if(contactsGather.get(groupPosition)!=null)	{
    	return contactsGather.get(groupPosition).size();
    	}else{
    	return 0;	
    	}
    	}else{       
        return 0;
    	}
    }

    @Override
    public List<String> getChild(int groupPosition, int childPosition) {

    	if(contactsGather!=null && contactsGather.isEmpty()==false
    	&&contactsGather.get(groupPosition).size()>=childPosition ){
        if(contactsGather.get(groupPosition)!=null)	{
        return contactsGather.get(groupPosition).get(childPosition);
            }else{
        return null;	
            }    		    		   		
        	}else{     
        return null;
        	}
    }
  
    

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

	public final class Control{  
        public TextView groupName;
        public TextView groupCount;

		    } 
	public final class Control2{  
        public ImageView child_img;
        public TextView child_name;
        public TextView child_id;
	    public TextView child_group_index;
	}
    
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View view, ViewGroup parent) {
    	Control control = null;
        
        if(view==null){
        control=new Control();
        view =layoutInflater.inflate(R.layout.apapter_group_layout, null);
        control.groupName=(TextView)view.findViewById(R.id.groupName);
        control.groupCount=(TextView)view.findViewById(R.id.groupCount);
        view.setTag(control);
        }else{
        control=(Control)view.getTag();
        }
        
        control.groupName.setTextColor(Color.BLACK);
        control.groupName.setText(getGroup(groupPosition).toString()); 
        
        if(contactsGather!=null && contactsGather.size()>groupPosition){
        control.groupCount.setText("["+getChildrenCount(groupPosition)+"]");
        }else{
        control.groupCount.setText("[0]");	
        }
        
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
    	Control2 control2 =null;
       	
    	if(convertView==null){
    	control2=new Control2();	
    	convertView =layoutInflater.inflate(R.layout.adapter_child_item, null);
        control2.child_img=(ImageView)convertView.findViewById(R.id.child_img);
    	control2.child_name=(TextView)convertView.findViewById(R.id.child_name);
    	control2.child_id=(TextView)convertView.findViewById(R.id.child_id);
    	control2.child_group_index=(TextView)convertView.findViewById(R.id.child_group_index);
    	convertView.setTag(control2);
    	}else{
    	control2=(Control2)convertView.getTag();
    	}
    	
        Log.i("getChild", ""+getChild(groupPosition, childPosition).get(0)
        +"groupPosition="+groupPosition+"/childPosition="+childPosition);
        
        control2.child_img.setImageResource(R.drawable.head_man);
        List<String> childs=getChild(groupPosition, childPosition); 
        //如果childs.size等于3，说明有备注，更改名字的显示
        if(childs.size()==3){
        control2.child_name.setText(childs.get(2)+"("+childs.get(1)+")");	
        }else{
        control2.child_name.setText(childs.get(1));	
        }        
        control2.child_id.setText(childs.get(0));
   //     control2.child_group_index.setText(groupPosition);
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition,
            int childPosition) {

        return true;
    }


}
