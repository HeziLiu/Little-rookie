package com.example.he.listviewitemanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private int prePosition=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list_view);
        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter(adapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                view.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.item_select_anim));
                if (prePosition>=0){
                    View v= mListView.getChildAt(prePosition);
                    v.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.item_unselect_anim));
                }
                prePosition=position;
            }
        });
    }
}


class MyAdapter extends BaseAdapter {


    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        }
        return view;
    }


}

