package com.example.he.fragmentdemo;


import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        if (findViewById(R.id.fragment_container) != null) {


            if (savedInstanceState != null)
                return;


            LeftFragment fragment = new LeftFragment();


            fragment.setArguments(getIntent().getExtras());
            FragmentTransaction f = getFragmentManager().beginTransaction();
            f.add(R.id.fragment_container,fragment).commit();


        }

    }
}
