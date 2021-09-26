package com.example.fosmad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminpannel extends AppCompatActivity {
    Button bt_additem,bt_viewitem;
    CardView cv_additem,cv_viewitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpannel);

        cv_additem = (CardView) findViewById(R.id.cv_admin_additem);
        cv_viewitem = (CardView) findViewById(R.id.cv_admin_viewitem);

        cv_additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openadditem();

            }
        });

        cv_viewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openviewitem();
            }
        });
    }

        public void openadditem(){
            Intent intent = new Intent(this, additem.class);
            startActivity(intent);
        }

        public void openviewitem(){
            Intent intent=new Intent(this,admin_allitem_view.class);
            startActivity(intent);
        }
    }
