package com.example.he.recycleviewtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by he on 2016/12/9.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private  List<Fruit> mList;

    public FruitAdapter(List<Fruit> list){
        mList=list;
    }


   static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.fruit_image);
            name = (TextView) itemView.findViewById(R.id.fruit_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        final ViewHolder holder=new ViewHolder(itemView);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Fruit fruit=mList.get(position);
                Toast.makeText(itemView.getContext(),"you click "+fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit=mList.get(position);
        holder.image.setImageResource(fruit.getImageId());
        holder.name.setText(fruit.getName());
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }
}
