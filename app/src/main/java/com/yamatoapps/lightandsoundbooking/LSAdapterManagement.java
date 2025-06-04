package com.yamatoapps.lightandsoundbooking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LSAdapterManagement extends ArrayAdapter<LSMenuCardItem> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public LSAdapterManagement(@NonNull Context context, ArrayList<LSMenuCardItem> lightSoundGroups) {
        super(context, 0, lightSoundGroups);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for th
        // is position
        LSMenuCardItem lsMenuCardItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lscompany_card, parent, false);
        }
        // Lookup view for data population
        TextView tvLSName = (TextView) convertView.findViewById(R.id.tvLSName);
        ImageView ivPackage = (ImageView) convertView.findViewById(R.id.ivLS);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.rb);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
        Button btnEdit = (Button) convertView.findViewById(R.id.btnEdit);
        // Populate the data into the template view using the data object
        tvDescription.setText(lsMenuCardItem.description);
        tvLSName.setText(lsMenuCardItem.name);
        ratingBar.setRating(Float.parseFloat(lsMenuCardItem.rating.toString()));
        Picasso.get().load(lsMenuCardItem.image_url).into(ivPackage);

        btnDelete.setOnClickListener(view -> {

            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
            alertDialogBuilder.setTitle("Delete Company");
            alertDialogBuilder.setMessage("Are you sure you want to delete this item?");
            alertDialogBuilder.setPositiveButton("NO", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            alertDialogBuilder.setNegativeButton("YES", (dialogInterface, i) -> {

                MaterialAlertDialogBuilder deleteDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
                deleteDialogBuilder.setTitle("Delete success");
                deleteDialogBuilder.setMessage("Company data deleted successfully!");
                deleteDialogBuilder.setPositiveButton("OK", (deleteDialogBuilderDialogInterface,j)->{
                    deleteDialogBuilderDialogInterface.dismiss();
                    Activity context = (Activity) parent.getContext();
                });
                db.collection("ls_companies").document(lsMenuCardItem.id).delete().addOnSuccessListener(unused -> {
                    deleteDialogBuilder.create().show();
                    dialogInterface.dismiss();
                });
            });
            alertDialogBuilder.create().show();
        });
        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(parent.getContext(), EditLSCompany.class);
            intent.putExtra("document_id",lsMenuCardItem.id);
            parent.getContext().startActivity(intent);
        });
        // Return the completed view to render on screen
            return convertView;
    }
}
