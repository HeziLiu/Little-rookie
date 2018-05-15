package com.example.he.ptrdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class MainActivity extends AppCompatActivity {

    PtrFrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (PtrFrameLayout) findViewById(R.id.ptr_frame);

        /**经典的头部风格
         *
         */
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        frameLayout.setHeaderView(header);
        frameLayout.addPtrUIHandler(header);
        frameLayout.setPtrHandler(new PtrHandler() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            //需要加载数据时触发
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                System.out.println("MainActivity.onRefreshBegin");
                frameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //加载数据完成后调用
                        frameLayout.refreshComplete();
                        //mPtrFrame.autoRefresh();//自动刷新
                    }
                }, 1800);
            }

        });
    }
}