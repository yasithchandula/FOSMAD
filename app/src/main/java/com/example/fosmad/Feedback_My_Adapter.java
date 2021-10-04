package com.example.fosmad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Feedback_My_Adapter extends RecyclerView.Adapter<Feedback_My_Adapter.FeedbackViewMyHolder> {
    Context context;
    ArrayList<RatingObj> list = null;
    AlertDialog.Builder builder;

    public Feedback_My_Adapter(Context context, ArrayList<RatingObj> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FeedbackViewMyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feedback_my_card, parent, false);
        return new FeedbackViewMyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewMyHolder holder, int position) {
        RatingObj ratingObj = list.get(position);

        holder.review.setText(ratingObj.getReview());
        holder.ratingBar.setRating(Float.parseFloat(ratingObj.getRating()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Feedback_Edit_My.class);
                intent.putExtra("RatingObj", ratingObj);
                view.getContext().startActivity(intent);
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Review")
                        .setMessage("Are you sure you want to delete this review?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                                        .child("Rating")
                                        .child(ratingObj.getItemKey())
                                        .child(ratingObj.getUserID())
                                        .child(ratingObj.getRatingKey());

                                databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Not deleted", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FeedbackViewMyHolder extends RecyclerView.ViewHolder {
        TextView review;
        RatingBar ratingBar;
        ImageButton btn_delete;

        public FeedbackViewMyHolder(@NonNull View itemView) {
            super(itemView);

            review = itemView.findViewById(R.id.review0);
            ratingBar = itemView.findViewById(R.id.ratingbar);
            btn_delete = itemView.findViewById(R.id.btn_delete_review);
        }
    }
}
