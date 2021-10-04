package com.example.fosmad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Contact_Us extends AppCompatActivity {

    EditText et_subject,et_message;
    Button btn;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        BottomNavigationView btv=findViewById(R.id.bottomnavview);
        btv.setBackground(null);


        et_subject = findViewById(R.id.et_name);
        et_message = findViewById(R.id.et_message);

        btn = findViewById(R.id.email_send);
        btn1 = findViewById(R.id.phone);
        String phone = "0775575282";
                btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = et_subject.getText().toString().trim();
                String message = et_message.getText().toString().trim();
                String email = "contact@cafebean.com";


                if(subject.isEmpty())
                {
                    Toast.makeText(Contact_Us.this, "Please add Subject", Toast.LENGTH_SHORT).show();
                }
                else if (message.isEmpty())
                {
                    Toast.makeText(Contact_Us.this, "Please add Message", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String mail = "mailto:" + email +
                            "?&subjects=" + Uri.encode(subject) +
                            "&body=" + Uri.encode(message);


                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse(mail));

                    try
                        {
                            startActivity(Intent.createChooser(intent,"Send Email"));

                        }
                        catch (Exception e)
                        {
                            Toast.makeText( Contact_Us.this, "Exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                    }
                });




                }
            }


