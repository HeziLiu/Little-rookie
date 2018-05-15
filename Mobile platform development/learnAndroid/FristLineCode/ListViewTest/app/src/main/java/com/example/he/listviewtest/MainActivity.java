package com.example.he.listviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<Fruit> list = new ArrayList<Fruit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruit();//初始化数据
        FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_item, list);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = list.get(position);
                Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void initFruit() {
        Fruit apple = new Fruit(R.drawable.apple_pic, "Apple");
        list.add(apple);
        Fruit banana = new Fruit(R.drawable.banana_pic, "Banana");
        list.add(banana);
        list.add(apple);
        list.add(banana);
        list.add(apple);
        list.add(banana);
        list.add(apple);
        list.add(banana);
        list.add(apple);
        list.add(banana);
        list.add(apple);
        list.add(banana);

    }
}
