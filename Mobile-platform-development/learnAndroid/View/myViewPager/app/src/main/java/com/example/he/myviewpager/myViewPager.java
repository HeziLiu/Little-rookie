package com.example.he.myviewpager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class myViewPager extends AppCompatActivity {
    private UI_ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view_pager);

        initView();


    }


    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        viewGroup = (UI_ViewGroup) findViewById(R.id.myView);
        final int screenWidth = getScreenMetrics(this).widthPixels;//让每个View的宽度都充满屏幕
        //添加5个子View
        for (int i = 0; i < 5; i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.content_view, viewGroup,false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            viewGroup.addView(layout);
        }


    }

    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.listView);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(myViewPager.this, "click item",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
