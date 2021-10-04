package com.example.fosmad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class activity_login extends AppCompatActivity {
    EditText email,password;
    Button login;
    TextView fgt_password,signin;
    ImageView login_back_btn;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.etLoginEmail);
        password = findViewById(R.id.etLoginPass);
        login = findViewById(R.id.btnLogin);
        signin = findViewById(R.id.tvRegisterHere);


        //get current instance of firebase authentication
        fAuth = FirebaseAuth.getInstance();


        // if the user is not registered
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), activity_register.class));
            }
        });

        // Login to the system when clicks the login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String text_email = email.getText().toString().trim();
                String text_password = password.getText().toString().trim();

                //validation
                if(TextUtils.isEmpty(text_email)){
                    email.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(text_password)){
                    password.setError("Password is required");
                    return;
                }

                if(text_password.length() < 6){
                    password.setError("Password must greater than 6 characters");
                }


                //authenticate user
                fAuth.signInWithEmailAndPassword(text_email, text_password).addOnSuccessListener(new OnSuccessListener<AuthResult>(){
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        if( text_email.equals("admin@gmail.com")){
                            Toast.makeText(activity_login.this, "Admin Logged", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), adminpannel.class));


                        }
                        else{
                            Toast.makeText(activity_login.this, "User Logged", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), home.class));
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity_login.this, "Login Unsuccessful!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




    }


}