package com.example.fosmad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Feedback_View_Adapter extends RecyclerView.Adapter<Feedback_View_Adapter.FeedbackViewHolder> {
    Context context;
    ArrayList<RatingObj> list = null;

    public Feedback_View_Adapter(Context context, ArrayList<RatingObj> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feedback_view_card, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        RatingObj ratingObj = list.get(position);

        holder.review.setText(ratingObj.getReview());
        holder.ratingBar.setRating(Float.parseFloat(ratingObj.getRating()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView review;
        RatingBar ratingBar;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);

            review = itemView.findViewById(R.id.review0);
            ratingBar = itemView.findViewById(R.id.ratingbar);
        }
    }
}
