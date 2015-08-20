package com.example.recommendUI;

import com.example.clientmessagemanage.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FriandRecommendFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        View frLayout = inflater.inflate(R.layout.client_layout, container, false);  
        return frLayout;  
    } 

}
