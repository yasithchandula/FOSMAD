package com.example.fosmad;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class itmadapter extends RecyclerView.Adapter<itmadapter.MyViewHolder> {

    Context context;
    ArrayList<item> list;

    public itmadapter(Context context, ArrayList<item>list){
        this.context=context;
        this.list=list;
    }


    @NonNull
    @Override
    public itmadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.itemview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itmadapter.MyViewHolder holder, int position) {


        item item=list.get(position);
        System.out.println(item.getImageUrl()+"ASDF");
//        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.imageUrl);
//        holder.imageUrl.setImageURI(Uri.parse(item.setImageUrl()));
        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.imageUrl1);
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());
        holder.category.setText(item.getCategory());

        // Send data to the selected offer
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), itemdetview.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name", item.getName());
                intent.putExtra("price", item.getPrice());
                intent.putExtra("description", item.getDescription());
                intent.putExtra("imageUrl", item.getImageUrl());
                view.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageUrl1;
        TextView name,price,category;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.tv_itname);
            category=itemView.findViewById(R.id.tv_category);
            price=itemView.findViewById(R.id.tv_itprice);
            imageUrl1=itemView.findViewById(R.id.iv_itemimage);

        }
    }
}
