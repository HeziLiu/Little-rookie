package com.example.he.gridviewtest;

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

public class itemArrayAdapter extends ArrayAdapter<Item> {
    private int resourceId;

    public itemArrayAdapter(Context context, int resource, List<Item> objects) {

        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            holder = new ViewHolder();
            holder.itemImage = (ImageView) view.findViewById(R.id.item_image);
            holder.itemName = (TextView) view.findViewById(R.id.item_name);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        holder.itemImage.setImageResource(item.getItemImageId());
        holder.itemName.setText(item.getItemName());
        return view;
    }


    private class ViewHolder {
        ImageView itemImage;
        TextView itemName;
    }


}
