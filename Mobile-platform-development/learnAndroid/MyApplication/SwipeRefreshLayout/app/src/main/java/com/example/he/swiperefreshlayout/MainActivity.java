package com.example.he.swiperefreshlayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final int REFRESH_COMPLETE = 0X110;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private Random random=new Random(47);
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("Java", "Javascript", "C++", "Ruby", "Json",
            "HTML","Lucene", "Canvas", "Bitmap","Lucene", "Canvas", "Bitmap"));

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
                    for (String s:mDatas)
                        mDatas.set(random.nextInt(mDatas.size()),s);
                    mAdapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);//隐藏刷新按钮
                    break;

            }
        };
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.lv);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.ly);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeColors(android.R.color.holo_blue_bright,android.R.color.holo_red_light);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_red_light);//转圈的颜色
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {

        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }
}
