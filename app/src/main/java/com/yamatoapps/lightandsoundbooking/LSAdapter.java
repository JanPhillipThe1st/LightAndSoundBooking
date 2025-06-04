package com.yamatoapps.lightandsoundbooking;

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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LSAdapter extends ArrayAdapter<LSMenuCardItem> {

    public LSAdapter(@NonNull Context context, ArrayList<LSMenuCardItem> lightSoundGroups) {
        super(context, 0, lightSoundGroups);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for th
        // is position
        LSMenuCardItem lsMenuCardItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ls_card_item, parent, false);
        }
        // Lookup view for data population
        TextView tvLSName = (TextView) convertView.findViewById(R.id.tvLSName);
        ImageView ivPackage = (ImageView) convertView.findViewById(R.id.ivLS);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.rb);
        Button btnSeeOptions = (Button) convertView.findViewById(R.id.btnSeeOptions);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        // Populate the data into the template view using the data object
        tvDescription.setText(lsMenuCardItem.description);
        tvLSName.setText(lsMenuCardItem.name);
        ratingBar.setRating(Float.parseFloat(lsMenuCardItem.rating.toString()));
        Picasso.get().load(lsMenuCardItem.image_url).into(ivPackage);
        btnSeeOptions.setOnClickListener(view -> {
            Intent lsOptions = new Intent(getContext(),SearchLightandSound.class);
            lsOptions.putExtra("lightAndSound",lsMenuCardItem.name);
            getContext().startActivity(lsOptions);
        });
        // Return the completed view to render on screen
            return convertView;
    }
}
