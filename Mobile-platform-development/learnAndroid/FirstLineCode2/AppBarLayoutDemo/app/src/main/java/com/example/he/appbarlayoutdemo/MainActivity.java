package com.example.he.appbarlayoutdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager= (ViewPager) findViewById(R.id.mViewPager);
        mTabLayout= (TabLayout) findViewById(R.id.mTabLayout);
        Toolbar toolbar= (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);
        initViewPager();


    }

    private void initViewPager(){
        List<String> titleList=new ArrayList<>();
        for (int i=0;i<10;i++){
            titleList.add("标题"+i);
            mTabLayout.addTab(mTabLayout.newTab().setText(titleList.get(i)));
        }
        mTabLayout.setupWithViewPager(mViewPager);

        List<Fragment> fragmentList=new ArrayList<>();
        for (int i=0;i<10;i++){
            fragmentList.add(new MyFragment(this));
        }
        MyViewPagerAdapter adapter=new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
        mViewPager.setAdapter(adapter);
//        mTabLayout.setTabsFromPagerAdapter();


    }

}

