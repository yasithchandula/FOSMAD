package com.example.fosmad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class additem extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText name,price,description;
    Spinner category;
    int maxid=0;
    item item;
    Button btn;
    private ImageView imageView;
    private ProgressBar progressBar;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef ;
//    FirebaseStorage storage=FirebaseStorage.getInstance();
//    StorageReference storageRef= storage.getReference();
    private StorageReference storageRef=FirebaseStorage.getInstance().getReference();
    private Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        name=findViewById(R.id.pt_input_p_name);
        price=findViewById(R.id.pt_input_price);
        description=findViewById(R.id.pt_input_description);
        btn=findViewById(R.id.btn_submit_additem);
        imageView=findViewById(R.id.ibtn_uploadimage);
        btn=findViewById(R.id.btn_submit_additem);
        progressBar=findViewById(R.id.pb_additem);
        category= (Spinner) findViewById(R.id.sp_category);

        progressBar.setVisibility(View.INVISIBLE);

        Spinner spinner=findViewById(R.id.sp_category);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



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
                if(TextUtils.isEmpty(name.getText().toString())){
                    name.setError("Name is Required");
                }
                else if (TextUtils.isEmpty((price.getText().toString()))){
                    price.setError("Price is Required");
                }
                else if(TextUtils.isEmpty((description.getText().toString()))){
                    description.setError("Description is Required");
                }
                else if(imageUri==null){
                    Toast.makeText(additem.this,"Image is required",Toast.LENGTH_SHORT).show();
                }
                else{
                    uploadToFirebase(imageUri);
                }

//                item.setName(name.getText().toString());
//                item.setPrice(price.getText().toString());
//                item.setDescription(description.getText().toString());
//
//                myRef.child(String.valueOf(maxid+1)).setValue(item);

//                if(imageUri!=null){
//                }else{
//
//                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2 && resultCode == RESULT_OK && data !=null){

            imageUri=data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadToFirebase(Uri uri){

        StorageReference fileRef=storageRef.child(System.currentTimeMillis()+"."+getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl();
                progressBar.setVisibility(View.INVISIBLE);

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        item.setName(name.getText().toString());
                        item.setPrice(price.getText().toString());
                        item.setDescription(description.getText().toString());
                        item.setCategory(category.getSelectedItem().toString());
                        String imageURL=uri.toString();
                        item.setImageUrl(imageURL);
//                        myRef.push().setValue(item);
                        myRef.child(String.valueOf(maxid+1)).setValue(item);

                        Toast.makeText(additem.this,"Item Added Successfully",Toast.LENGTH_SHORT);
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(additem.this,"Upload Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getFileExtension(Uri muri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(muri));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}