package com.example.he.viewpagertest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private List<View> viewList;
    private List<String> titleList;
    private myViewPagerAdapter adapter;
    private myFragmentPagerAdapter adapter2;
    private List<Fragment> fragmentList;

    private PagerTabStrip tabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewList = new ArrayList<View>();
        titleList = new ArrayList<String>();
        titleList.add("这是第一页");
        titleList.add("这是第二页");
        titleList.add("这是第三页");

        View v1 = View.inflate(this, R.layout.view1, null);
        View v2 = View.inflate(this, R.layout.view2, null);
        View v3 = View.inflate(this, R.layout.view3, null);
        viewList.add(v1);
        viewList.add(v2);
        viewList.add(v3);
        viewPager = (ViewPager) findViewById(R.id.vp);
        tabStrip = (PagerTabStrip) findViewById(R.id.ts);
        tabStrip.setBackgroundColor(Color.RED);
        tabStrip.setTextColor(Color.WHITE);
        tabStrip.setDrawFullUnderline(false);
        tabStrip.setTabIndicatorColor(Color.GREEN);


        adapter = new myViewPagerAdapter(viewList, titleList);


        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new Fragment1());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment3());


        adapter2 = new myFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter2);
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Toast.makeText(MainActivity.this,"这是第"+(position+1)+"页",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MainActivity.this,"这是第"+(position+1)+"页",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        // viewPager.setAdapter(adapter);
    }
}
