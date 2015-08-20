package com.example.ContactsUI;

import com.example.clientmessagemanage.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactsFragment extends Fragment implements OnClickListener{

	
	private TextView privateText;
	private TextView clientText;
	private TextView groupText;
	private PrivateFragment privateFragment;
	private ClientFragment clientFragment;
	private GroupFragment groupFragment;
	private FragmentManager fragmentManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
        Bundle savedInstanceState) {  
        View contactsLayout = inflater.inflate(R.layout.contacts_layout, container, false);
        
	    privateText=(TextView)contactsLayout.findViewById(R.id.privateText);
        clientText=(TextView)contactsLayout.findViewById(R.id.clientText);
        groupText=(TextView)contactsLayout.findViewById(R.id.groupText);
        
        privateText.setOnClickListener(this);
        clientText.setOnClickListener(this);
        groupText.setOnClickListener(this);
        
        fragmentManager = getFragmentManager();
        setTabSelection2(0);
        return contactsLayout;
      
     
    }



@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.privateText:
		// 当点击了私人tab时，选中第1个tab
		setTabSelection2(0);
		break;
	case R.id.clientText:
		// 当点击了客户tab时，选中第2个tab
		setTabSelection2(1);
		break;
	case R.id.groupText:
		// 当点击了群组tab时，选中第3个tab
		setTabSelection2(2);
		break;
	default:
		break;
	}
}

private void setTabSelection2(int index2) {
	// 每次选中之前先清楚掉上次的选中状态
	clearSelection2();
	// 开启一个Fragment事务
	FragmentTransaction transaction2 = fragmentManager.beginTransaction();
	// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
	hideFragments(transaction2);
	switch (index2) {
	case 0:
		// 当点击了私人tab时，改变控件的背景颜色
		privateText.setTextColor(Color.WHITE);
		if (privateFragment == null) {
			// 如果privateFragment为空，则创建一个并添加到界面上
			privateFragment = new PrivateFragment();
			transaction2.add(R.id.content2, privateFragment);
		} else {
			// 如果privateFragment不为空，则直接将它显示出来
			transaction2.show(privateFragment);
		}
		break;
	case 1:
		// 当点击了客户tab时，改变控件的文字颜色
		clientText.setTextColor(Color.WHITE);
		if (clientFragment == null) {
			// 如果clientFragment为空，则创建一个并添加到界面上
			clientFragment = new ClientFragment();
			transaction2.add(R.id.content2, clientFragment);
		} else {
			// 如果clientFragment不为空，则直接将它显示出来
			transaction2.show(clientFragment);
		}
		break;
	case 2:
	default:
		// 当点击了群组tab时，改变控件的文字颜色
		groupText.setTextColor(Color.WHITE);
		if (groupFragment == null) {
			// 如果GroupFragment为空，则创建一个并添加到界面上
			groupFragment = new GroupFragment();
			transaction2.add(R.id.content2, groupFragment);
		} else {
			// 如果GroupFragment不为空，则直接将它显示出来
			transaction2.show(groupFragment);
		}
		break;
	}
	transaction2.commit();
}

  private void clearSelection2() {

	privateText.setTextColor(Color.parseColor("#82858b"));
	clientText.setTextColor(Color.parseColor("#82858b"));
	groupText.setTextColor(Color.parseColor("#82858b"));
  }
	private void hideFragments(FragmentTransaction transaction) {
		if (privateFragment != null) {
			transaction.hide(privateFragment);
		}
		if (clientFragment != null) {
			transaction.hide(clientFragment);
		}
		if (groupFragment != null) {
			transaction.hide(groupFragment);
		}
	}
}
