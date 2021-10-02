package com.example.fosmad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
//retrieve
public class retrive_datac extends AppCompatActivity {

    ListView mylistview;
    List<Member> memberList;

    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_datac);

//member list add
        mylistview = findViewById(R.id.mylistview);
        memberList = new ArrayList<>();
        reff = FirebaseDatabase.getInstance().getReference().child("Member");


        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                memberList.clear();
                for(DataSnapshot ignored : snapshot.getChildren()){

                    Member member = ignored.getValue(Member.class);
                    memberList.add(member);

                }
                ListAdapter adapter=new ListAdapter(retrive_datac.this,memberList);
                mylistview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //set item listener on listview item
        mylistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Member member = memberList.get(position);
                showUpdateDialog(member.getAddress(), member.getConNo(), member.getId(),member.getName());
                return false;
            }
        });

    }
//update dialog box display
    private void showUpdateDialog(String Address, String ConNo, final String id, String Name){

        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater= getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.uppdate_debug,null);

        mDialog.setView(mDialogView);

        //create views referance
        EditText updateName = mDialogView.findViewById(R.id.updateName);
        EditText updateAddress = mDialogView.findViewById(R.id.updateAddress);
        EditText updateConNo = mDialogView.findViewById(R.id.updateConNo);

        Button updatebutton = mDialogView.findViewById(R.id.updatebutton);

        mDialog.setTitle("Updating " + Name+ "record");
        mDialog.show();

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update data in database

                //value view
                String newName = updateName.getText().toString();
                String newAddress = updateAddress.getText().toString();
                String newConNo = updateConNo.getText().toString();
                updateData(id,newName,newAddress,newConNo);

                Toast.makeText(retrive_datac.this,"Recoreded Updated",Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void updateData(String id, String name,String address,String ConNo){

        //database referance
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Member").child(id);


        Member member = new Member(id,name,address,ConNo);
        reff.setValue(member);

    }
}