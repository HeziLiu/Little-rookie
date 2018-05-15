package com.example.he.recycleviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Fruit> fruitList;
    private FruitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        fruitList = new ArrayList<Fruit>();
        adapter = new FruitAdapter(fruitList);
        initFruit();
//        LinearLayoutManager manager = new LinearLayoutManager(this);//线性布局
        //瀑布流布局
        StaggeredGridLayoutManager manager =new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }


    public void initFruit() {
        Fruit apple = new Fruit(R.drawable.apple_pic, "AppleAppleAppleAppleAppleAppleAppleAppleApple");
        fruitList.add(apple);
        Fruit banana = new Fruit(R.drawable.banana_pic, "Banana");
        fruitList.add(banana);
        fruitList.add(apple);
        fruitList.add(banana);
        fruitList.add(apple);
        fruitList.add(banana);
        fruitList.add(apple);
        fruitList.add(banana);
        fruitList.add(apple);
        fruitList.add(banana);
        fruitList.add(apple);
        fruitList.add(banana);
    }
}
