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
    //ͨ���ص���activity��ֵ
    private CallBackTips callBackTips;
	/**
	 * ���ڶ�Fragment���й���
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
		//����Ҫ�ص����ݵ�ֵ
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
	 * ���ݴ����index����������ѡ�е�tabҳ��
	 * 
	 * @param index
	 *            ÿ��tabҳ��Ӧ���±ꡣ
	 */
	private void setTabSelection5(int index) {
    	fragmentManager5 = getFragmentManager(); 
    	//���ص������
		container_layout.setVisibility(View.INVISIBLE);
		// ����һ��Fragment����
		FragmentTransaction transaction = fragmentManager5.beginTransaction();
		// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
		hideFragments(transaction);
        switch (index) {
		case 0:
			if (rfFragment == null) {
				// ���rfFragmentΪ�գ��򴴽�һ������ӵ�������
				rfFragment = new RecommendFriandFragment();
				transaction.add(R.id.content5, rfFragment);
			} else {
				// ���RecommendFriandFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(rfFragment);
			}
			break;

		case 1:
			if (frFragment == null) {
				// ���frFragmentΪ�գ��򴴽�һ������ӵ�������
				frFragment = new FriandRecommendFragment();
				transaction.add(R.id.content, frFragment);
			} else {
				// ���FriandRecommendFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(frFragment);
			}
			break;

		default:
			break;
		}
       
        transaction.commit();
	}
	/**
	 * �����е�Fragment����Ϊ����״̬��
	 * 
	 * @param transaction
	 *            ���ڶ�Fragmentִ�в���������
	 */
	private void hideFragments(FragmentTransaction transaction) {

		if (frFragment != null) {
			transaction.hide(frFragment);
		}
		if (rfFragment != null) {
			transaction.hide(rfFragment);
		}

	}
	
	//��д���ص��ӿ�
	public interface CallBackTips{
		
	public void	CallBackTips(int message);
	
	}
	
	//����дonAttach
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

