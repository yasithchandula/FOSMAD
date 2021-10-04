package com.example.fosmad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;

public class Feedback_Rating extends AppCompatActivity {


    TextView rateCount, showRating;
    EditText review;
    Button submit;
    RatingBar ratingBar;
    Float rateValue;
    String temp, itemKey, userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_rating);

        rateCount = findViewById(R.id.tv_ratecount);
        ratingBar = findViewById(R.id.ratingbar);
        review = findViewById(R.id.et_write_review);
        submit = findViewById(R.id.submitBtn);
        showRating = findViewById(R.id.showRating);
        itemKey = getIntent().getExtras().getString("itemKey");

        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            if (firebaseAuth.getCurrentUser() != null) {
                // Get the current user
                userID = firebaseAuth.getCurrentUser().getUid();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                rateValue = ratingBar.getRating();

                if (rateValue <= 1 && rateValue > 0)
                    rateCount.setText(rateValue.toString());
                else if (rateValue <= 2 && rateValue > 1)
                    rateCount.setText(rateValue.toString());
                else if (rateValue <= 3 && rateValue > 2)
                    rateCount.setText(rateValue.toString());
                else if (rateValue <= 4 && rateValue > 3)
                    rateCount.setText(rateValue.toString());
                else if (rateValue <= 5 && rateValue > 4)
                    rateCount.setText(rateValue.toString());
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String rvw= review.getText().toString();
                temp = rateCount.getText().toString();
                showRating.setText("Your Rating: \n" + temp + "\n" + review.getText());

                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Rating");


                Map<String, Object> user = new HashMap<>();

                user.put("Rating", temp);
                user.put("Review", rvw);

                myRef.child(itemKey).child(userID).push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Feedback_Rating.this, "Review added successfully!",
                                Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Feedback_Rating.this, "ERROR!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });




            }
        });
    }
}


