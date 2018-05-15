package com.example.he.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * ListView中每个item的适配器
 * Created by he on 2016/11/16.
 */

public class myBaseAdapter extends BaseAdapter {

    private List<ItemBean> itemBeanList;
    private Context context;


    public myBaseAdapter(Context context, List<ItemBean> list) {

        this.context = context;
        itemBeanList = list;
    }


    @Override
    public int getCount() {
        return itemBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        ItemBean bean = itemBeanList.get(position);

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_layout, null);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.image_view);
            holder.title = (TextView) view.findViewById(R.id.tv_title);
            holder.content = (TextView) view.findViewById(R.id.tv_content);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        holder.image.setImageResource(bean.getImage());
        holder.title.setText(bean.getTitle());
        holder.content.setText(bean.getContent());

        return view;

    }

    /**
     * 使用ViewHolder进行优化
     */
    class ViewHolder {
        ImageView image;
        TextView title;
        TextView content;
    }

}
