package com.example.fosmad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class check_edit extends AppCompatActivity {

    //xml id
    EditText inputEmail, inputEmail5, inputEmail4;
    Button SUBMIT, edit;
    DatabaseReference reff;
    Member member;

    Integer id = new Random().nextInt();
    String keyId = Integer.toString(id);
    //create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_edit);
        inputEmail = findViewById(R.id.inputEmail);
        inputEmail5 = findViewById(R.id.inputEmail5);
        inputEmail4 = findViewById(R.id.inputEmail4);
//buttons
        SUBMIT = findViewById(R.id.btnLogin3);


        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        //insert data
        SUBMIT.setOnClickListener(view -> insertMemberData());
    }
    //insert member data
    private void insertMemberData(){

        String Name = inputEmail.getText().toString();
        String Address = inputEmail5.getText().toString();
        String ConNo= inputEmail4.getText().toString();

        Member member = new Member(Name,Address,ConNo, keyId);
//push
        reff.push().setValue(member);
        Toast.makeText(com.example.fosmad.check_edit.this,"Data inserted success",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, retrive_datac.class);
        intent.putExtra("NAME", Name);

        intent.putExtra("ADDRESS", Address);
        intent.putExtra("CONNO", ConNo);
        startActivity(intent);
    }

}