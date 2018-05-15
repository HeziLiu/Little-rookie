package com.example.he.testlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fruit> list = new ArrayList<Fruit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        init();
        FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_layout, list);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, list.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void init() {
        Fruit apple = new Fruit(R.drawable.ap, "apple");
        Fruit banana = new Fruit(R.drawable.ba, "banana");
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
        list.add(apple);
        list.add(banana);

    }

}
