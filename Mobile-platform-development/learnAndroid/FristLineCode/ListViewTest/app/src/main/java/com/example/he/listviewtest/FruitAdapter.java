package com.example.he.listviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by he on 2016/3/31.
 */
public class FruitAdapter extends ArrayAdapter<Fruit> {
    private int resourceId;

    class ViewHolder {
        ImageView fruitimage;
        TextView fruitname;
    }


    public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = getItem(position);
        View view;
        ViewHolder viewholder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewholder = new ViewHolder();
            viewholder.fruitimage = (ImageView) view.findViewById(R.id.fruit_image);
            viewholder.fruitimage.setLayoutParams(new LinearLayout.LayoutParams(200, 200));//设置图片大小
            viewholder.fruitname = (TextView) view.findViewById(R.id.fruit_name);

            view.setTag(viewholder);

        } else {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }

        viewholder.fruitimage.setImageResource(fruit.getImageId());//设置image路径
        viewholder.fruitname.setText(fruit.getName());//
        return view;
    }
}
