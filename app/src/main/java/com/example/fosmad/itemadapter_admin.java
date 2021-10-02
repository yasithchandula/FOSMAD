package com.example.fosmad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.api.LogDescriptor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class itemadapter_admin extends RecyclerView.Adapter<itemadapter_admin.MyViewHolder> {
    Context context;
    ArrayList<item> list;
    AlertDialog.Builder builder;
    String childRef="item";

    public itemadapter_admin(Context context, ArrayList<item>list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public itemadapter_admin.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.adminitemview,parent,false);
        return new itemadapter_admin.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull itemadapter_admin.MyViewHolder holder, int position) {

        item item=list.get(position);
        System.out.println(item.getImageUrl()+"ASDF");
//        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.imageUrl);
//        holder.imageUrl.setImageURI(Uri.parse(item.setImageUrl()));
        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.imageUrl1);
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());
//        holder.description.setText(item.getDescription());

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
        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder=new AlertDialog.Builder(context);
                Log.d("mytest", "del onclick");
                builder.setTitle("Delete Item").setMessage("Are you sure you want to delete this?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //remove data from database
                                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference()
                                        .child(childRef).child(item.getItemKey());
                                Log.d("mytest", item.getItemKey());

                                databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        String text="Success";
                                        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        String text="Failed";
                                        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                                        toast.show();

                                    }
                                });

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageUrl1;
        TextView name,price,description;
        Button delbtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.tv_itname);
//            description=itemView.findViewById(R.id.tv_description);
            price=itemView.findViewById(R.id.tv_itprice);
            imageUrl1=itemView.findViewById(R.id.iv_itemimage);
            delbtn=itemView.findViewById(R.id.admin_del_btn);
        }
    }
}


