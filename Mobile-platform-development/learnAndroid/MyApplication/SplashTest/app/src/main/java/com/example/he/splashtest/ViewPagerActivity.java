package com.example.he.splashtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by he on 2017/1/24.
 */

public class ViewPagerActivity extends AppCompatActivity {

    private static final String TAG = "ViewPagerActivity";

    private ViewPager viewPager;
    private PagerAdapter adapter;
    private List<View> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_activity_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        list = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater().from(this);
        View v1 = inflater.inflate(R.layout.viewpager_item1, null);
        final View v2 = inflater.inflate(R.layout.viewpager_item2, null);

        list.add(v1);
        list.add(v2);

        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public int getItemPosition(Object object) {
                return list.indexOf(object);
            }

            //确定需要加载的页面
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list.get(position));

                Button button = (Button) v2.findViewById(R.id.button_);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ViewPagerActivity.this, MainActivity.class);
                        startActivity(intent);
                        ViewPagerActivity.this.finish();
                    }
                });

                return list.get(position);
            }

            //页面删除
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position));
            }
        };
        viewPager.setAdapter(adapter);

        Log.d(TAG, "onCreate: ");

    }
}




