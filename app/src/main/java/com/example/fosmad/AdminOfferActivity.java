package com.example.fosmad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminOfferActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Offers> adList;
    AdminOfferAdapter adapter;

    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_offer);

        recyclerView = findViewById(R.id.recyclerview_offers_admin);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adList = new ArrayList<>();
        adapter = new AdminOfferAdapter(this, adList);
        recyclerView.setAdapter(adapter);

        dbRef = FirebaseDatabase.getInstance().getReference("Offers");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Offers adOffer = dataSnapshot.getValue(Offers.class);
                    adOffer.setItemKey(dataSnapshot.getKey());
                    adList.add(adOffer);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}