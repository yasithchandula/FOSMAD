package com.example.fosmad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class itemdetview extends AppCompatActivity {

    ImageView itemimage;
    TextView tv_name,tv_price,tv_description;
    Button addtocart, reviews;
    String name,price,description,imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetview);

        name=getIntent().getExtras().getString("name");
        price=getIntent().getExtras().getString("price");
        description=getIntent().getExtras().getString("description");
        imageUrl=getIntent().getExtras().getString("imageUrl");

        tv_name=findViewById(R.id.tv_itdet_name);
        tv_price=findViewById(R.id.tv_itdet_price);
        tv_description=findViewById(R.id.tv_itdet_description);
        itemimage=findViewById(R.id.iv_itemdetimage);

        tv_name.setText(name);
        tv_price.setText(price);
        tv_description.setText(description);

        Glide.with(itemdetview.this).load(imageUrl).into(itemimage);


        itemimage=findViewById(R.id.iv_itemimage);

    }
}