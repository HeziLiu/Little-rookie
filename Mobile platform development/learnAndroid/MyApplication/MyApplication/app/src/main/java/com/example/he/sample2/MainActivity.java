package com.example.he.sample2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int OK = 1;

    private myListView listView;
    private ArrayAdapter adapter;
    private List<Integer> list;
    private int footerHeight;
    private Random random = new Random(47);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            /**
             * 因为ListView添加数据能直接显示出来，所以在这里进行添加
             */
            switch (msg.what) {
                case OK:
                    for (int i = 0; i < 10; i++) {
                        list.add(random.nextInt(100));
                    }
                    adapter.notifyDataSetChanged();
                    listView.paddingBottom(-footerHeight);//隐藏
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (myListView) findViewById(R.id.listView);
        list = new ArrayList<Integer>();
        initData();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        footerHeight = listView.getFooterHeight();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollStat) {
                Log.i("ttt", "" + scrollStat);
                //加上底部判断
                if (listView.getState() == FooterState.REFRESH && listView.getLastVisiblePosition() == listView.getCount() - 1) {
                    onRefresh();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


    }


    private void initData() {
        for (int i = 0; i < 10; i++)
            list.add(random.nextInt(100));
    }

    /**
     * 更新数据
     */
    private void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(OK, 2000);
            }
        }).start();
    }

}
