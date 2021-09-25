package com.example.fosmad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<CartItems> CList;

    public CartAdapter(Context context, ArrayList<CartItems> CList){
        this.context = context;
        this.CList = CList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_list, parent,false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItems addCart = CList.get(position);
        holder.productName.setText(addCart.getProductName());
        holder.productPrice.setText(addCart.getProductPrice().toString());
        holder.productQty.setText(addCart.getProductQty().toString());
        Glide.with(context).load(CList.get(position).getItemImage()).into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return CList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{

        TextView productName, productPrice, productQty;
        ImageView productImage;



        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.tv_item_name);
            productPrice = itemView.findViewById(R.id.tv_qtyPrice);
            productImage = itemView.findViewById(R.id.img_item);
            productQty = itemView.findViewById(R.id.tv_qty);
        }
    }
}
