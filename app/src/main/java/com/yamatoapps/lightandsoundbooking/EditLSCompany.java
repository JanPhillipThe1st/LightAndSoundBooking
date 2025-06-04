package com.yamatoapps.lightandsoundbooking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditLSCompany extends AppCompatActivity {
    Uri fileUri;
    ImageView iv;
    String document_id = "";
    String image_url = "";
    Intent intent;
    TextView tvLSName,tvLSDescription;
    Button btnUpdate;
    RatingBar ratingBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ProgressDialog progressDialog;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK && data != null && data != null) {
            fileUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),fileUri);
                uploadImage();
                iv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lscompany);
        iv = findViewById(R.id.ivLSImage);
        tvLSName = findViewById(R.id.tvLSName);
        tvLSDescription = findViewById(R.id.tvLSDescription);
        ratingBar = findViewById(R.id.rbLSRating);
        Intent imageIntent = new Intent();
        progressDialog = new ProgressDialog(this);
        btnUpdate = findViewById(R.id.btnUpdate);

        progressDialog.setTitle("Working on it.");
        progressDialog.setMessage("Saving Information...");
        intent = getIntent();
        document_id = intent.getStringExtra("document_id");
        btnUpdate.setEnabled(false);
        db.collection("ls_companies").document(document_id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    tvLSName.setText(documentSnapshot.getString("name"));
                    ratingBar.setRating(Float.parseFloat (documentSnapshot.get("rating").toString()));
                    tvLSDescription.setText(documentSnapshot.getString("description"));
                    Picasso.get().load(documentSnapshot.getString("image_url")).into(iv);
                    image_url = documentSnapshot.getString("image_url");
                });


        btnUpdate.setOnClickListener(view ->{

            progressDialog.show();
            Map<String, Object> listing = new HashMap<>();
            listing.put("name", tvLSName.getText().toString());
            listing.put("description", tvLSDescription.getText().toString());
            listing.put("rating", ratingBar.getRating());
            listing.put("image_url",image_url );
            DocumentReference newListingRef = db.collection("ls_companies").document(document_id);
            newListingRef.update(listing).addOnSuccessListener(unused -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditLSCompany.this);
                builder.setTitle("Operation Success!");
                builder.setMessage("Light and Sound Company information successfully updated.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            });
            progressDialog.dismiss();


        });
        iv.setOnClickListener(view ->{
            imageIntent.setType("image/*");
            imageIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(imageIntent,"Pick image to upload"),22);

        });
    }
    public  void uploadImage(){
        if (fileUri != null){

            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child(UUID.randomUUID().toString());
            UploadTask uploadTask = (UploadTask) storageReference.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {

            }).addOnFailureListener(listener->{
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Fail to Upload Image..", Toast.LENGTH_SHORT)
                        .show();
            });
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        image_url =  task.getResult().toString();
                        btnUpdate.setEnabled(true);
                    } else {
                        // Handle failures
                        // ...

                    }
                }
            });

        }
    }
}