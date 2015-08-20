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
		// �������˽��tabʱ��ѡ�е�1��tab
		setTabSelection2(0);
		break;
	case R.id.clientText:
		// ������˿ͻ�tabʱ��ѡ�е�2��tab
		setTabSelection2(1);
		break;
	case R.id.groupText:
		// �������Ⱥ��tabʱ��ѡ�е�3��tab
		setTabSelection2(2);
		break;
	default:
		break;
	}
}

private void setTabSelection2(int index2) {
	// ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬
	clearSelection2();
	// ����һ��Fragment����
	FragmentTransaction transaction2 = fragmentManager.beginTransaction();
	// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
	hideFragments(transaction2);
	switch (index2) {
	case 0:
		// �������˽��tabʱ���ı�ؼ��ı�����ɫ
		privateText.setTextColor(Color.WHITE);
		if (privateFragment == null) {
			// ���privateFragmentΪ�գ��򴴽�һ������ӵ�������
			privateFragment = new PrivateFragment();
			transaction2.add(R.id.content2, privateFragment);
		} else {
			// ���privateFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
			transaction2.show(privateFragment);
		}
		break;
	case 1:
		// ������˿ͻ�tabʱ���ı�ؼ���������ɫ
		clientText.setTextColor(Color.WHITE);
		if (clientFragment == null) {
			// ���clientFragmentΪ�գ��򴴽�һ������ӵ�������
			clientFragment = new ClientFragment();
			transaction2.add(R.id.content2, clientFragment);
		} else {
			// ���clientFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
			transaction2.show(clientFragment);
		}
		break;
	case 2:
	default:
		// �������Ⱥ��tabʱ���ı�ؼ���������ɫ
		groupText.setTextColor(Color.WHITE);
		if (groupFragment == null) {
			// ���GroupFragmentΪ�գ��򴴽�һ������ӵ�������
			groupFragment = new GroupFragment();
			transaction2.add(R.id.content2, groupFragment);
		} else {
			// ���GroupFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
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
