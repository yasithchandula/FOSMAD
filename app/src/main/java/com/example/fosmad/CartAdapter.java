package com.example.fosmad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;


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

        String key = addCart.getItemKey();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Cart").child("User").child("Items").child(key);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove data from database
                databaseRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

            }
        });

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //increment quantity
                Integer qty = addCart.getProductQty();
                addCart.setProductQty(++qty);
                databaseRef.setValue(addCart);
            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //decrement quantity
                Integer qty = addCart.getProductQty();

                if (qty > 1){
                    addCart.setProductQty(--qty);
                    databaseRef.setValue(addCart);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return CList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{

        TextView productName, productPrice, productQty;
        ImageView productImage, plusButton, minusButton, deleteButton;



        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.tv_item_name);
            productPrice = itemView.findViewById(R.id.tv_qtyPrice);
            productImage = itemView.findViewById(R.id.img_item);
            productQty = itemView.findViewById(R.id.tv_qty);
            plusButton = itemView.findViewById(R.id.btn_plus);
            minusButton = itemView.findViewById(R.id.btn_minus);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }
}
