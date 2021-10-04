package com.example.fosmad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class itemdetview extends AppCompatActivity {

    ImageView itemimage;
    TextView tv_name,tv_price,tv_description;
    Button addtocart, reviews;
    String name,price,description,imageUrl, itemKey;

    DatabaseReference Dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetview);

        name=getIntent().getExtras().getString("name");
        price=getIntent().getExtras().getString("price");
        description=getIntent().getExtras().getString("description");
        imageUrl=getIntent().getExtras().getString("imageUrl");
        itemKey = getIntent().getExtras().getString("itemKey");


        tv_name=findViewById(R.id.tv_itdet_name);
        tv_price=findViewById(R.id.tv_itdet_price);
        tv_description=findViewById(R.id.tv_itdet_description);
        itemimage=findViewById(R.id.iv_itemdetimage);

        reviews = findViewById(R.id.btn_view_reviews);

        addtocart = findViewById(R.id.btn_add_to_cart_item);



        tv_name.setText(name);
        tv_price.setText(price);
        tv_description.setText(description);

        Glide.with(itemdetview.this).load(imageUrl).into(itemimage);
        itemimage=findViewById(R.id.iv_itemimage);

   itemimage=findViewById(R.id.iv_itemimage);

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Feedback_View.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("itemKey", itemKey);
                v.getContext().startActivity(intent);
            }
        });

        Dbref = FirebaseDatabase.getInstance().getReference().child("Cart").child("User").child("Items");

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
                Toast.makeText(itemdetview.this, "Item Added", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //insert data to cart table
    public  void InsertData() {
        String productName = name;
        Float productPrice = Float.valueOf(price);
        int quantity = 1;
        String productImage = imageUrl;

        CartItems cartlist = new CartItems(productName, productPrice, quantity, productImage);
        Dbref.push().setValue(cartlist);

    }

}