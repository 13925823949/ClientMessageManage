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
		//屏幕宽度
		mScreenWidth= ScreenUtils.getScreenWidth(context);	
	}
	public SlidingMenu(Context context) {
		super(context );
		//屏幕宽度
		mScreenWidth= ScreenUtils.getScreenWidth(context);	
	}
	//计算控件或者子空间的宽高
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		LinearLayout llWrapper=(LinearLayout) getChildAt(0);
		//RelativeLayout rlWrapper=(RelativeLayout) getChildAt(1);
		//菜单的布局
		mMenu=(ViewGroup) llWrapper.getChildAt(0);
		 
		//主界面的布局
		mMain=(ViewGroup) llWrapper.getChildAt(1);
		//菜单宽度
		mMenuWidth=mScreenWidth-mRightPaddding;
		mMenu.getLayoutParams().width = mMenuWidth;
		//主界面的宽度
		mMain.getLayoutParams().width = mScreenWidth;
		
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);	
	}
	//视图渲染时调用，决定视图的摆放位置
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		//打开主界面
		this.scrollBy(mMenuWidth, 0);
	}
	
	
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		RelativeLayout lin = (RelativeLayout)findViewById(R.id.menu_frame);
//    	ListView broadsideList=(ListView)lin.findViewById(R.id.broadsideList); 
//		Log.i("dx", "按下"+ev.getAction());
//		switch (ev.getAction()) {  
//   	 
//    	case MotionEvent.ACTION_DOWN:  
//         	downX=0;
//        	//按下的坐标
//        	downX=ev.getX();
//              return true;  
//              
//    	  case MotionEvent.ACTION_UP:  
//          	
//          	float dX=ev.getX()-downX;
//          	if(Math.abs(dX)>10){
//          	//向左
//          	if(dX<0){
//
//          	this.smoothScrollTo(mMenuWidth, 0);  
//          	//隐藏菜单
//          	broadsideList.setVisibility(View.INVISIBLE);
//          	}else{
//          	//向右
//          	this.smoothScrollTo(0, 0);	
//          	//显示菜单
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
    	Log.i("dx", "按下"+ev.getAction());
    	switch (ev.getAction()) {  
    	 
//    	case MotionEvent.ACTION_DOWN:  
// 
//        	downX=0;
//        	//按下的坐标
//        	downX=ev.getX();
//           Log.i("dx", "按下的坐标"+downX); 
//              return true;  
    	       
	   case MotionEvent.ACTION_MOVE: 
		 if(i==0){  
		   downX=ev.getX();
		   
		 }
		   i++;
		   Log.i("dx", "移动的坐标"+downX); 
    	   break; 	  
       
	   case MotionEvent.ACTION_UP:  
        	
        	float dX=ev.getX()-downX;        	
        	Log.i("dx", downX+"滑动距离"+dX+"/"+i);
        	downX=0;
        	i=0;
        	//向左
        	if(dX<0){

        	this.smoothScrollTo(mMenuWidth, 0);  
        	//隐藏菜单
        	broadsideList.setVisibility(View.INVISIBLE);
        	}else{
        	//向右
        	this.smoothScrollTo(0, 0);	
        	//显示菜单
        	broadsideList.setVisibility(View.VISIBLE);
        	
        	}
        	return true;      
	   
	   default:  
		      break;  

        }
        
		return super.onTouchEvent(ev);
	}
	
	
//在滚动中不断调用此方法，频率低	
@Override
protected void onScrollChanged(int l, int t, int oldl, int oldt) {
	// TODO Auto-generated method stub
	super.onScrollChanged(l, t, oldl, oldt);
	//进度
	float scale=(float)l/mMenuWidth;
	//菜单缩放的比例
	float leftScale=1-0.3f*scale;
	ViewHelper.setScaleX(mMenu,leftScale);
	ViewHelper.setScaleY(mMenu,leftScale);
	//透明度
	float leftAlpha=1-scale;
	ViewHelper.setAlpha(mMenu, leftAlpha);	
	//平移动画
	ViewHelper.setTranslationX(mMenu, mMenuWidth*scale*0.7f);
	//主界面缩放的比例
	float rightScale=0.8f+scale*0.2f;
	ViewHelper.setScaleX(mMain,rightScale);
	ViewHelper.setScaleY(mMain,rightScale);
}


}
