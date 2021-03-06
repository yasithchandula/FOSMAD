package com.example.fosmad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewOfferActivity extends AppCompatActivity {

    TextView tv_offerTitle,  tv_offerPrice,  tv_description;
    String title, price, description, offerImageURL;
    ImageView offerImage;
    Button btn_addtoCart;

    String userID;

    DatabaseReference Dbref;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offer);

        title = getIntent().getExtras().getString("Title");
        price = getIntent().getExtras().getString("Price");
        description = getIntent().getExtras().getString("Description");
        offerImageURL = getIntent().getExtras().getString("OfferImage");

        tv_offerTitle = findViewById(R.id.tv_offerTitle_clicked);
        tv_offerPrice = findViewById(R.id.tv_offerPrice_clicked);
        tv_description = findViewById(R.id.tv_offerDescription);
        offerImage = findViewById(R.id.img_clicked_offer);
        btn_addtoCart = findViewById(R.id.btn_addtocart_clicked);

        tv_offerTitle.setText(title);
        tv_offerPrice.setText(price);
        tv_description.setText(description);

        Glide.with(ViewOfferActivity.this).load(offerImageURL).into(offerImage);

        firebaseAuth=FirebaseAuth.getInstance();

        btn_addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseAuth.getCurrentUser() != null) {
                    // Get the current user
                    userID = firebaseAuth.getCurrentUser().getUid();
                    Dbref = FirebaseDatabase.getInstance().getReference().child("Cart").child(userID);
                    InsertData();
                    Toast.makeText(ViewOfferActivity.this, "Offer Added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ViewOfferActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    //insert data to cart child
    public  void InsertData(){
        String productName = title;
        Float productPrice = Float.valueOf(price);
        int quantity = 1;
        String productImage = offerImageURL;

        CartItems cartlist = new CartItems(productName, productPrice, quantity, productImage);
        Dbref.push().setValue(cartlist);

    }
}