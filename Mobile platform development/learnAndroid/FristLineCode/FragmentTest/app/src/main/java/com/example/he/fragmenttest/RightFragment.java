package com.example.he.fragmenttest;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by he on 2016/4/3.
 */
public class RightFragment extends Fragment {

//    在碎片中得到当前碎片相关联的活动的实例
    mainActivity ac = (com.example.he.fragmenttest.mainActivity) getActivity();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right_fragment, container, false);
        //通过活动获取另一个碎片的实例
//        FragmentManager manager= ac.getFragmentManager();
//        LeftFragment fragment= (LeftFragment) manager.findFragmentById(R.id.left_fragment);
        return view;
    }
}
