package com.example.recommendUI;



import com.example.clientmessagemanage.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RecommendFragment extends Fragment implements OnClickListener{

 
    private RecommendFriandFragment rfFragment;
    private FriandRecommendFragment frFragment;
	private RelativeLayout rf_layout;
	private RelativeLayout fr_layout;
	private RelativeLayout rd_layout;
    private View recommendLayout;
    //通过回调给activity传值
    private CallBackTips callBackTips;
	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager5;
	private RelativeLayout container_layout;
	private ImageView recommend_msg_tips_img;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
       recommendLayout = inflater.inflate(R.layout.recommend_layout, container, false);  
    
        init(); 
 
        return recommendLayout;  
    }

	private void init() {
		rf_layout=(RelativeLayout)recommendLayout.findViewById(R.id.rf_layout);
        fr_layout=(RelativeLayout)recommendLayout.findViewById(R.id.fr_layout);
        rd_layout=(RelativeLayout)recommendLayout.findViewById(R.id.rd_layout);
        container_layout=(RelativeLayout)recommendLayout.findViewById(R.id.container_layout);
        recommend_msg_tips_img=(ImageView)recommendLayout.findViewById(R.id.recommend_msg_tips_img);
        
	
        
        int msg= getFragmentManager().findFragmentByTag("myTag")
        		.getArguments().getInt("msg");
        Log.i("msg", ""+msg);
        if(msg==2){
        recommend_msg_tips_img.setVisibility(View.VISIBLE);	
        }
        
        rf_layout.setOnClickListener(this);
        fr_layout.setOnClickListener(this);
        rd_layout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rf_layout:
			setTabSelection5(0);
			break;
		case R.id.fr_layout:
			setTabSelection5(1);
			break;
		case R.id.rd_layout:
			
		recommend_msg_tips_img.setVisibility(View.GONE);
		//放入要回调传递的值
		callBackTips.CallBackTips(2);
		Intent intent =new Intent();
		intent.setClass(getActivity(), RequestDisposeActivity.class);
		getActivity().startActivity(intent);
			break;
		default:
			break;
		}
		
	} 
	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。
	 */
	private void setTabSelection5(int index) {
    	fragmentManager5 = getFragmentManager(); 
    	//隐藏点击布局
		container_layout.setVisibility(View.INVISIBLE);
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager5.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
        switch (index) {
		case 0:
			if (rfFragment == null) {
				// 如果rfFragment为空，则创建一个并添加到界面上
				rfFragment = new RecommendFriandFragment();
				transaction.add(R.id.content5, rfFragment);
			} else {
				// 如果RecommendFriandFragment不为空，则直接将它显示出来
				transaction.show(rfFragment);
			}
			break;

		case 1:
			if (frFragment == null) {
				// 如果frFragment为空，则创建一个并添加到界面上
				frFragment = new FriandRecommendFragment();
				transaction.add(R.id.content, frFragment);
			} else {
				// 如果FriandRecommendFragment不为空，则直接将它显示出来
				transaction.show(frFragment);
			}
			break;

		default:
			break;
		}
       
        transaction.commit();
	}
	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {

		if (frFragment != null) {
			transaction.hide(frFragment);
		}
		if (rfFragment != null) {
			transaction.hide(rfFragment);
		}

	}
	
	//先写个回调接口
	public interface CallBackTips{
		
	public void	CallBackTips(int message);
	
	}
	
	//再重写onAttach
	public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
	try {
	
	callBackTips = (CallBackTips) activity;
	} catch (ClassCastException e) {
	throw new ClassCastException(activity.toString()
	+ "must implement OnGridViewSelectedListener");
	}

	}

}

