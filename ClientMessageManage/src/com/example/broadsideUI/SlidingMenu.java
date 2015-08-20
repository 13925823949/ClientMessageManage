package com.example.broadsideUI;

import com.example.clientmessagemanage.R;
import com.example.util.ScreenUtils;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class SlidingMenu extends HorizontalScrollView {
	
	private ViewGroup mMenu;
	private ViewGroup mMain;
	private int mRightPaddding =200;
	private int mScreenWidth;
	private int mMenuWidth;
	private float downX;
    private int i=0;
	
	
	public SlidingMenu(Context context,AttributeSet attrs) {
		super(context,attrs );
		//��Ļ���
		mScreenWidth= ScreenUtils.getScreenWidth(context);	
	}
	public SlidingMenu(Context context) {
		super(context );
		//��Ļ���
		mScreenWidth= ScreenUtils.getScreenWidth(context);	
	}
	//����ؼ������ӿռ�Ŀ��
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		LinearLayout llWrapper=(LinearLayout) getChildAt(0);
		//RelativeLayout rlWrapper=(RelativeLayout) getChildAt(1);
		//�˵��Ĳ���
		mMenu=(ViewGroup) llWrapper.getChildAt(0);
		 
		//������Ĳ���
		mMain=(ViewGroup) llWrapper.getChildAt(1);
		//�˵����
		mMenuWidth=mScreenWidth-mRightPaddding;
		mMenu.getLayoutParams().width = mMenuWidth;
		//������Ŀ��
		mMain.getLayoutParams().width = mScreenWidth;
		
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);	
	}
	//��ͼ��Ⱦʱ���ã�������ͼ�İڷ�λ��
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		//��������
		this.scrollBy(mMenuWidth, 0);
	}
	
	
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		RelativeLayout lin = (RelativeLayout)findViewById(R.id.menu_frame);
//    	ListView broadsideList=(ListView)lin.findViewById(R.id.broadsideList); 
//		Log.i("dx", "����"+ev.getAction());
//		switch (ev.getAction()) {  
//   	 
//    	case MotionEvent.ACTION_DOWN:  
//         	downX=0;
//        	//���µ�����
//        	downX=ev.getX();
//              return true;  
//              
//    	  case MotionEvent.ACTION_UP:  
//          	
//          	float dX=ev.getX()-downX;
//          	if(Math.abs(dX)>10){
//          	//����
//          	if(dX<0){
//
//          	this.smoothScrollTo(mMenuWidth, 0);  
//          	//���ز˵�
//          	broadsideList.setVisibility(View.INVISIBLE);
//          	}else{
//          	//����
//          	this.smoothScrollTo(0, 0);	
//          	//��ʾ�˵�
//          	broadsideList.setVisibility(View.VISIBLE);
//          	}
//          	}
//          	return true;      
//  	   
//  	   default:  
//  		      break;  
//
//          }	
//		
//		 
//		return super.dispatchTouchEvent(ev);
//	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
    	RelativeLayout lin = (RelativeLayout)findViewById(R.id.menu_frame);
    	ListView broadsideList=(ListView)lin.findViewById(R.id.broadsideList); 
    	Log.i("dx", "����"+ev.getAction());
    	switch (ev.getAction()) {  
    	 
//    	case MotionEvent.ACTION_DOWN:  
// 
//        	downX=0;
//        	//���µ�����
//        	downX=ev.getX();
//           Log.i("dx", "���µ�����"+downX); 
//              return true;  
    	       
	   case MotionEvent.ACTION_MOVE: 
		 if(i==0){  
		   downX=ev.getX();
		   
		 }
		   i++;
		   Log.i("dx", "�ƶ�������"+downX); 
    	   break; 	  
       
	   case MotionEvent.ACTION_UP:  
        	
        	float dX=ev.getX()-downX;        	
        	Log.i("dx", downX+"��������"+dX+"/"+i);
        	downX=0;
        	i=0;
        	//����
        	if(dX<0){

        	this.smoothScrollTo(mMenuWidth, 0);  
        	//���ز˵�
        	broadsideList.setVisibility(View.INVISIBLE);
        	}else{
        	//����
        	this.smoothScrollTo(0, 0);	
        	//��ʾ�˵�
        	broadsideList.setVisibility(View.VISIBLE);
        	
        	}
        	return true;      
	   
	   default:  
		      break;  

        }
        
		return super.onTouchEvent(ev);
	}
	
	
//�ڹ����в��ϵ��ô˷�����Ƶ�ʵ�	
@Override
protected void onScrollChanged(int l, int t, int oldl, int oldt) {
	// TODO Auto-generated method stub
	super.onScrollChanged(l, t, oldl, oldt);
	//����
	float scale=(float)l/mMenuWidth;
	//�˵����ŵı���
	float leftScale=1-0.3f*scale;
	ViewHelper.setScaleX(mMenu,leftScale);
	ViewHelper.setScaleY(mMenu,leftScale);
	//͸����
	float leftAlpha=1-scale;
	ViewHelper.setAlpha(mMenu, leftAlpha);	
	//ƽ�ƶ���
	ViewHelper.setTranslationX(mMenu, mMenuWidth*scale*0.7f);
	//���������ŵı���
	float rightScale=0.8f+scale*0.2f;
	ViewHelper.setScaleX(mMain,rightScale);
	ViewHelper.setScaleY(mMain,rightScale);
}


}
