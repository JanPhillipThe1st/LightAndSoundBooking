package com.yamatoapps.lightandsoundbooking.ui;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.yamatoapps.lightandsoundbooking.AddLSCompany;
import com.yamatoapps.lightandsoundbooking.ManageCompany;
import com.yamatoapps.lightandsoundbooking.R;
import com.yamatoapps.lightandsoundbooking.databinding.FragmentAddLSCompanyBinding;
import com.yamatoapps.lightandsoundbooking.databinding.FragmentViewLSCompaniesBinding;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddLSCompanyFragment extends Fragment {
    Uri fileUri;
    ImageView ivLSImage;
    TextView tvLSName,tvLSDescription;
    RatingBar ratingBar;

    private FragmentAddLSCompanyBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddLSCompanyBinding.inflate(inflater, container, false);

        Intent imageIntent = new Intent();
        tvLSName = binding.tvLSName;
        tvLSDescription = binding.tvLSDescription;
        ratingBar = binding.rbLSRating;
         ivLSImage = binding.ivLSImage;
        Button btnSave = binding.btnSave;
        Button btnManage = binding.btnManage;
        btnSave.setOnClickListener(view -> {
            uploadImage();
        });
        btnManage.setOnClickListener(view -> {
            startActivity(new Intent(AddLSCompanyFragment.this.getContext(), ManageCompany.class));
        });
        ivLSImage.setOnClickListener(view ->{
            imageIntent.setType("image/*");
            imageIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(imageIntent,"Pick image to upload"),22);
        });
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK && data != null && data != null) {
            fileUri = data.getData();
                Picasso.get().load(fileUri).into(ivLSImage);
        }
    }
    public  void uploadImage(){
        if (fileUri != null){
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Working on it.");
            progressDialog.setMessage("Saving Information...");
            progressDialog.show();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child(UUID.randomUUID().toString());
            UploadTask uploadTask = (UploadTask) storageReference.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {

            }).addOnFailureListener(listener->{
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Fail to Upload Image..", Toast.LENGTH_SHORT)
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
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> listing = new HashMap<>();
                        listing.put("name", tvLSName.getText().toString());
                        listing.put("description", tvLSDescription.getText().toString());
                        listing.put("rating", ratingBar.getRating());
                        listing.put("image_url", task.getResult());
                        DocumentReference newListingRef = db.collection("ls_companies").document();
                        newListingRef.set(listing);
                        progressDialog.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Operation Success!");
                        builder.setMessage("Light and Sound Company information successfully saved.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create().show();
                    } else {
                        // Handle failures
                        // ...

                    }
                }
            });

        }
    }
}