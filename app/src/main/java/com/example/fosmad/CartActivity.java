package com.example.fosmad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CartItems> CList;
    CartAdapter adapter;
    TextView total_tv, subtotal;

    DatabaseReference DbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerview_offers_admin);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CList = new ArrayList<>();
        adapter = new CartAdapter(this, CList);
        recyclerView.setAdapter(adapter);
        total_tv = findViewById(R.id.tv_tPrice);
        subtotal = findViewById(R.id.tv_subPrice);

        DbRef = FirebaseDatabase.getInstance().getReference("Cart/User/Items");

        DbRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CList.clear();
                Double total = 0.0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CartItems cartList = dataSnapshot.getValue(CartItems.class);
                    cartList.setItemKey(dataSnapshot.getKey());
                    total = total + (cartList.getProductPrice() * cartList.getProductQty());
                    CList.add(cartList);
                }

                total_tv.setText("Rs."+total.toString());
                subtotal.setText("Rs."+total.toString());

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}