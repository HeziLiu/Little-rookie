package com.example.he.toolbartest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Glide是一个图片加载库，具有图片压缩的功能
 *
 * Created by he on 2016/12/14.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitHolder> {
    private List<Fruit> mList;
    private Context mContext;

    public FruitAdapter(List<Fruit> mList) {
        this.mList = mList;
    }

    @Override
    public FruitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item, parent, false);
        return new FruitHolder(view);
    }

    @Override
    public void onBindViewHolder(FruitHolder holder, int position) {
        final Fruit fruit=mList.get(position);
        holder.fruit_name.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruit_image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext,fruit_activity.class);
                intent.putExtra("fruit_name",fruit.getName());
                intent.putExtra("fruit_imageId",fruit.getImageId());
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class FruitHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView fruit_image;
        TextView fruit_name;

        public FruitHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            fruit_image = (ImageView) itemView.findViewById(R.id.fruit_image);
            fruit_name = (TextView) itemView.findViewById(R.id.fruit_name);
        }
    }


}
