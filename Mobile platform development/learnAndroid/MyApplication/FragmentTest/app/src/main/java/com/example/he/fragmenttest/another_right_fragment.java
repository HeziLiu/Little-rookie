package com.example.he.fragmenttest;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by he on 2016/10/31.
 */

public class another_right_fragment extends Fragment {
    private static  final String CODE="thank you";

    private  Listener listener;

    @Override
    public void onAttach(Activity activity) {
        listener= (Listener) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.another_right_layout, container,false);
        TextView textView= (TextView) view.findViewById(R.id.another_fragment_text);
        String info= (String) getArguments().get("info");
        textView.setText(info);
        listener.thanks(CODE);
        return view;
    }
}
