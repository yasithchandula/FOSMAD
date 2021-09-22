package com.example.fosmad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class additem extends AppCompatActivity {

    EditText name,price,description;
    int maxid=0;
    item item;
    Button btn;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        name=findViewById(R.id.pt_input_p_name);
        price=findViewById(R.id.pt_input_price);
        description=findViewById(R.id.pt_input_description);
        btn=findViewById(R.id.btn_reset_additem);

        item=new item();
        myRef=database.getInstance().getReference().child("item");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = (int) snapshot.getChildrenCount();
                }else{


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setName(name.getText().toString());
                item.setPrice(price.getText().toString());
                item.setDiscription(description.getText().toString());

                myRef.child(String.valueOf(maxid+1)).setValue(item);
            }
        });


    }
}