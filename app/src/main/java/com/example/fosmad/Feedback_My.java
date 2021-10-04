package com.example.fosmad;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Feedback_My extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<RatingObj> list;
    Feedback_My_Adapter feedback_my_adapter;

    String itemKey, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_my);

        itemKey = getIntent().getExtras().getString("itemKey");

        recyclerView = findViewById(R.id.recyclerview_reviews);
        recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("Rating").child(itemKey);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            if (firebaseAuth.getCurrentUser() != null) {
                // Get the current user
                userID = firebaseAuth.getCurrentUser().getUid();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RatingObj ratingObj = dataSnapshot.getValue(RatingObj.class);
                    assert ratingObj != null;
                    ratingObj.setItemKey(itemKey);
                    ratingObj.setRatingKey(dataSnapshot.getKey());
                    ratingObj.setUserID(userID);
                    list.add(ratingObj);
                    System.out.println(ratingObj.getRating());
                }
                feedback_my_adapter = new Feedback_My_Adapter(Feedback_My.this, list);
                recyclerView.setAdapter(feedback_my_adapter);
                feedback_my_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

