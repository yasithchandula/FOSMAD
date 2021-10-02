package com.example.fosmad;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder>{

        Context context;
        ArrayList<Offers> List;

        public OfferAdapter(Context context, ArrayList<Offers> List){

            this.context = context;
            this.List = List;
        }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.offer_list, parent, false);
        return new OfferViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {

        Offers offset = List.get(position);
        holder.offerTitle.setText(offset.getOfferTitle());
        Glide.with(context).load(List.get(position).getOfferImage()).into(holder.offerImg);

        // Send data to the selected offer
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mytest", "ONclick");
                Intent intent = new Intent(view.getContext(), ViewOfferActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Title", offset.getOfferTitle());
                Log.d("mytest", offset.getOfferTitle());
                intent.putExtra("Price", offset.getOfferPrice().toString());
                intent.putExtra("Description", offset.getOfferDescription());
                intent.putExtra("OfferImage", offset.getOfferImage());

                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public static class OfferViewHolder extends RecyclerView.ViewHolder{
            TextView offerTitle;
            ImageView offerImg;

        public OfferViewHolder(@NonNull View offView) {
            super(offView);

            offerTitle = offView.findViewById(R.id.tv_admin_off_title);
            offerImg = offView.findViewById(R.id.img_admin_off1);
        }
    }
}
