package com.example.clientmessagemanage;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RemindFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        View remindLayout = inflater.inflate(R.layout.remind_layout, container, false);  
        return remindLayout;  
    } 

}
