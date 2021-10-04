package com.example.fosmad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class activity_register extends AppCompatActivity {
    public static final String TAG = "TAG";
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    EditText regName, regEmail, regPhone, regPassword;
    Spinner district_spinner, gender_spinner;
    Button accountCreate;
    FirebaseAuth fAuth;
    String userID;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z0-9]+\\.+[a-z]+";
    String phonePattern = "[0-9]{10}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regName = findViewById(R.id.etRegName);
        regEmail = findViewById(R.id.etRegEmail);
        regPhone = findViewById(R.id.etRegPhone);
        regPassword = findViewById(R.id.etRegPass);
        accountCreate=findViewById(R.id.btnRegister);

        //get current instance of firebase authentication
        fAuth = FirebaseAuth.getInstance();

        rootNode=FirebaseDatabase.getInstance();




        //if user is already registered send to Userprofile page
//        if (fAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), UserloginActivity.class));
//            //using finish method then the user cannot access the back button after going to the homepage
//            finish();
//        }




        // assigning the function when the user clicks the accountCreate button
        accountCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code in here is executed once clicked

                //assign edit text values to  string variables and using .trim() to remove spaces if necessary
                String dregName = regName.getText().toString().trim();
                String dregEmail = regEmail.getText().toString().trim();
                String dregPhone = regPhone.getText().toString().trim();
                String dregPassword = regPassword.getText().toString().trim();


                //Validations
                if(regEmail.getText().toString().isEmpty()) {
                    regEmail.setError("Email is Required");
                }else {
                    if (!regEmail.getText().toString().trim().matches(emailPattern)) {
                        regEmail.setError("Invalid Email Address");
                        return;
                    }
                }
                if (TextUtils.isEmpty(dregPassword)) {
                    regPassword.setError("Password is required");
                    return;
                }
                if (dregPassword.length() < 6) {
                    regPassword.setError("Password must greater than 6 characters");
                }
                if (TextUtils.isEmpty(dregName)) {
                    regName.setError("First name is required");
                    return;
                }

                if (TextUtils.isEmpty(dregPhone)) {
                    regPhone.setError("Phone number is required");
                    return;
                }else {
                    if (!regPhone.getText().toString().trim().matches(phonePattern)) {
                        regPhone.setError("Invalid Phone Number");
                        return;
                    }
                }




                //create a new user with email and password
                //custom firebase Authentication
                fAuth.createUserWithEmailAndPassword(dregEmail,dregPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){


                            //sending other data to realtime DB
                            userID = fAuth.getCurrentUser().getUid();
                            reference = rootNode.getReference().child("user").child(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Email",dregEmail);
                            user.put("Name",dregName);
                            user.put("Phone",dregPhone);


                            reference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(activity_register.this, "Registered Succesfully", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG,"user profile is created for "+ userID);
                                }
                            });


                            startActivity(new Intent(getApplicationContext(), activity_login.class));
                        }
                        else {
                            Toast.makeText(activity_register.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}