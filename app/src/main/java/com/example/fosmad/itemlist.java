package com.example.fosmad;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class itemlist extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    ListAdapter listAdapter;
    RecyclerView.Adapter adapter;
    ArrayList<Member> list;




//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.list_item);
//
//        recyclerView=findViewById(R.id.mrview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        list=new ArrayList<>();
//        listAdapter=new ListAdapter(this,list);
//        recyclerView.setAdapter(adapter);
//
//        database = FirebaseDatabase.getInstance().getReference("member");
//
//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    Member member=dataSnapshot.getValue(Member.class);
//                    list.add(member);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
}
