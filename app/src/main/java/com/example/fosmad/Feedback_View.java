package com.example.fosmad;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Feedback_View extends Activity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<RatingObj> list;
    Feedback_View_Adapter feedback_view_adapter;

    String itemKey;
    Button addReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_view);

        itemKey = getIntent().getExtras().getString("itemKey");

        addReview = findViewById(R.id.btn_add_review);

        recyclerView = findViewById(R.id.recyclerview_reviews);
        recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("Rating").child(itemKey);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RatingObj ratingObj = dataSnapshot.getValue(RatingObj.class);
                    list.add(ratingObj);
                    assert ratingObj != null;
                    System.out.println(ratingObj.getRating());
                }
                feedback_view_adapter = new Feedback_View_Adapter(Feedback_View.this, list);
                recyclerView.setAdapter(feedback_view_adapter);
                feedback_view_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Feedback_Rating.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("itemKey", itemKey);
                v.getContext().startActivity(intent);
            }
        });



    }
}
