package com.example.he.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by he on 2016/12/25.
 */

public class myAdapter extends BaseAdapter{
    private List<ItemBean> mList;
    private Context mContext;


    public myAdapter(List<ItemBean> mList,Context context) {
        this.mList = mList;
        mContext=context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        ItemBean bean=mList.get(position);
        if(convertView==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.listview_item,parent,false);
            holder=new ViewHolder();
            holder.title= (TextView) view.findViewById(R.id.title_);
            view.setTag(holder);
        }else{
            view=convertView;
            holder= (ViewHolder) view.getTag();
        }
        holder.title.setText(bean.getTitle());
        final TextView delete= (TextView) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                notifyDataSetChanged();
            }
        });
        bean.lin_root= (LinearLayout) view.findViewById(R.id.lin_root);
        bean.lin_root.scrollTo(0,0);
        return view;
    }


    class ViewHolder{
        TextView title;
    }

    public  static class ItemBean{
        public String title;
        public LinearLayout lin_root;
        public ItemBean(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

}
