package com.example.he.spinnertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    // private Spinner spinner;
    private AppCompatSpinner spinner;

    private myArrayAdapter adapter;


    private List<Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText("欢迎来到北京");
        //  spinner = (Spinner) findViewById(R.id.sp);
        spinner = (AppCompatSpinner) findViewById(R.id.sp);
        list = new ArrayList<Item>();
        init();
        adapter = new myArrayAdapter(this,R.layout.myitem, list);
       // adapter=new myArrayAdapter(this,R.layout.myitem,R.id.item_tv,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Item t = list.get(position);
                tv.setText(t.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void init() {
        list.add(new Item(R.mipmap.ic_launcher, "北京"));
        list.add(new Item(R.mipmap.ic_launcher, "上海"));
        list.add(new Item(R.mipmap.ic_launcher, "广州"));
        list.add(new Item(R.mipmap.ic_launcher, "深圳"));
    }


}
