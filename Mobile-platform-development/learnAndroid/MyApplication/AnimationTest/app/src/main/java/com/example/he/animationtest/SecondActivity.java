package com.example.he.animationtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by he on 2016/11/13.
 */

public class SecondActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter adapter;
    private List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listView = (ListView) findViewById(R.id.lv);
        list = new ArrayList<String>();
        init();
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        //界面动画控制器
        LayoutAnimationController controller=new LayoutAnimationController(AnimationUtils.loadAnimation(this,R.anim.act_in));
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);//设置动画顺序
        listView.setLayoutAnimation(controller);//listView设置界面动画
        listView.startLayoutAnimation();

    }

    private void init() {
        list.add("Item      1");
        list.add("Item      2");
        list.add("Item      3");
        list.add("Item      4");
        list.add("Item      5");
        list.add("Item      6");
        list.add("Item      7");
        list.add("Item      8");
        list.add("Item      9");
        list.add("Item      10");
        list.add("Item      11");


    }


}
