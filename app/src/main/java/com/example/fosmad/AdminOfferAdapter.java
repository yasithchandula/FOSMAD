package com.example.fosmad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminOfferAdapter extends RecyclerView.Adapter<AdminOfferAdapter.AdminOfferViewHolder>{

    Context context;
    ArrayList<Offers> List;

    public AdminOfferAdapter(Context context, ArrayList<Offers> List){

        this.context = context;
        this.List = List;
    }

    @NonNull
    @Override
    public AdminOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.admin_offer_list, parent, false);
        return new AdminOfferAdapter.AdminOfferViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOfferViewHolder holder, int position) {

        Offers offset = List.get(position);
        holder.offerTitle.setText(offset.getOfferTitle());
        Glide.with(context).load(List.get(position).getOfferImage()).into(holder.offerImg);

        String Key = offset.getItemKey();
        DatabaseReference DbReferance = FirebaseDatabase.getInstance().getReference().child("Offers").child(Key);

        //delete offer
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbReferance.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Offer deleted", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public static class AdminOfferViewHolder extends RecyclerView.ViewHolder{
        TextView offerTitle,deleteText;
        ImageView offerImg;
        ImageButton btnDelete;

        public AdminOfferViewHolder(@NonNull View itemView) {
            super(itemView);

            offerTitle = itemView.findViewById(R.id.tv_admin_off_title);
            offerImg = itemView.findViewById(R.id.img_admin_off1);
            deleteText = itemView.findViewById(R.id.tv_delete_offer);
            btnDelete = itemView.findViewById(R.id.btn_delete_offer);
        }
    }
}
