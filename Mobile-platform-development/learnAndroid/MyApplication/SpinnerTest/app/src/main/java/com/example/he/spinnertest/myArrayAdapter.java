package com.example.he.spinnertest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by he on 2016/10/27.
 */

public class myArrayAdapter extends ArrayAdapter<Item> {
    private int resourceID;

    public myArrayAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
        resourceID = resource;
    }


    public myArrayAdapter(Context context, int resource, int textViewResourceId, List<Item> objects) {
        super(context, resource, textViewResourceId, objects);
        resourceID = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        Item item = getItem(position);

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceID, null);
            holder = new ViewHolder();
            holder.cityImage = (ImageView) view.findViewById(R.id.imageV);
            holder.cityName = (TextView) view.findViewById(R.id.item_tv);
            view.setTag(holder);

        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();

        }

        holder.cityImage.setImageResource(item.getImageID());
        holder.cityName.setText(item.getName());
        return view;
    }


    class ViewHolder {
        ImageView cityImage;
        TextView cityName;
    }

}
