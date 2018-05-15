package com.example.he.testlistview;

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
 * Created by he on 2016/4/2.
 */
public class FruitAdapter extends ArrayAdapter<Fruit> {
    int resourceId;

    public FruitAdapter(Context context,  int textViewResourceId, List<Fruit> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = getItem(position);
        View view;
        ViewHolder fruitHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            fruitHolder=new ViewHolder();
            fruitHolder.fruitimage=(ImageView)view.findViewById(R.id.fruit_image);
            fruitHolder.fruitimage.setLayoutParams(new LinearLayout.LayoutParams(200,200));
            fruitHolder.fruitname=(TextView) view.findViewById(R.id.fruit_name);
            view.setTag(fruitHolder);

        } else {
            view = convertView;
            fruitHolder= (ViewHolder) view.getTag();
        }
        fruitHolder.fruitimage.setImageResource(fruit.getId());
        fruitHolder.fruitname.setText(fruit.getName());

        return view;
    }
}

class ViewHolder {
    ImageView fruitimage;
    TextView  fruitname;
}
