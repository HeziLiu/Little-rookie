package com.example.he.gridviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private itemArrayAdapter adapter;
    private List<Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gv);
        list = new ArrayList<Item>();
        adapter = new itemArrayAdapter(this, R.layout.item, list);
        initItem();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }


    private void initItem() {
        list.add(new Item(R.drawable.address_book, "书"));
        list.add(new Item(R.drawable.calendar, "日历"));
        list.add(new Item(R.drawable.camera, "相机"));
        list.add(new Item(R.drawable.clock, "时钟"));
        list.add(new Item(R.drawable.games_control, "游戏"));
        list.add(new Item(R.drawable.messenger, "联系人"));
        list.add(new Item(R.drawable.address_book, "书"));
        list.add(new Item(R.drawable.calendar, "日历"));
        list.add(new Item(R.drawable.camera, "相机"));
        list.add(new Item(R.drawable.clock, "时钟"));
        list.add(new Item(R.drawable.games_control, "游戏"));
        list.add(new Item(R.drawable.messenger, "联系人"));
        list.add(new Item(R.drawable.address_book, "书"));
        list.add(new Item(R.drawable.calendar, "日历"));
        list.add(new Item(R.drawable.camera, "相机"));
        list.add(new Item(R.drawable.clock, "时钟"));
        list.add(new Item(R.drawable.games_control, "游戏"));
        list.add(new Item(R.drawable.messenger, "联系人"));

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, list.get(position).getItemName(), Toast.LENGTH_SHORT).show();
    }
}
