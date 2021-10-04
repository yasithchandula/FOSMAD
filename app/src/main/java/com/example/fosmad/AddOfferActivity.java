package com.example.fosmad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AddOfferActivity extends AppCompatActivity {

    EditText txt_title;
    EditText txt_price;
    EditText txt_description;
    Button btn_publish;
    ImageView img_offer;
    Uri imageUri;
    Offers offers;
    ProgressBar progressBar;

    DatabaseReference cafeDBRef;
    StorageReference cafeSTRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        txt_title = findViewById(R.id.et_offTitle);
        txt_price = findViewById(R.id.et_offPrice);
        txt_description = findViewById(R.id.et_offerDes);
        btn_publish = findViewById(R.id.btn_publish);
        img_offer = findViewById(R.id.btn_imageUpload);

        progressBar = findViewById(R.id.progressBar);

        cafeDBRef = FirebaseDatabase.getInstance().getReference().child("Offers");
        cafeSTRef = FirebaseStorage.getInstance().getReference();

        img_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        progressBar.setVisibility(View.INVISIBLE);

        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String offerTitle = txt_title.getText().toString();
                String offerPriceS = txt_price.getText().toString().trim();
                String offerDescription = txt_description.getText().toString();

                //InsertOfferData();
                if(TextUtils.isEmpty(offerTitle)){
                    txt_title.setError("Title is required!");
                }
                else if(TextUtils.isEmpty(offerPriceS)){
                    txt_price.setError("Price is required!");
                }
                else if(TextUtils.isEmpty(offerDescription)){
                    txt_description.setError("Description is required!");
                }
                else if(imageUri.equals(Uri.EMPTY)) {
                    Toast.makeText(AddOfferActivity.this, "Main image required!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Float offerPrice = Float.valueOf(offerPriceS);
                    offers = new Offers(offerTitle, offerPrice, offerDescription);
                    uploadToFirebase(imageUri);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            img_offer.setImageURI(imageUri);

        }
    }
//    private void InsertOfferData() {
//        String offerTitle = txt_title.getText().toString();
//        Float offerPrice = Float.valueOf(txt_price.getText().toString().trim());
//        String offerDescription = txt_description.getText().toString();
//
//        offers = new Offers(offerTitle, offerPrice, offerDescription);
//
//        uploadToFirebase(imageUri);
//
//    }

    private void uploadToFirebase(Uri uri) {

        StorageReference fileRef = cafeSTRef.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String offerImage = uri.toString();
                        offers.setOfferImage(offerImage);
                        cafeDBRef.push().setValue(offers);
                        Toast.makeText(AddOfferActivity.this, "Offer Added successfully",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
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
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddOfferActivity.this, "Uploading Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Get the file extension
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}

