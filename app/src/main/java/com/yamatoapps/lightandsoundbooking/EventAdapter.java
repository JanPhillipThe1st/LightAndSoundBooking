package com.yamatoapps.lightandsoundbooking;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<PackageCardItem> {

        public EventAdapter(@NonNull Context context, ArrayList<PackageCardItem> packages) {
            super(context, 0, packages);
        }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PackageCardItem packageCardItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.package_card_item, parent, false);
        }
        // Lookup view for data population
        TextView tvDetails = (TextView) convertView.findViewById(R.id.tvDetails);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        TextView tvEventName = (TextView) convertView.findViewById(R.id.tvEventName);
        ImageView ivPackage = (ImageView) convertView.findViewById(R.id.ivpackage);
        Button btnbookNow = (Button) convertView.findViewById(R.id.btnbookNow);
        // Populate the data into the template view using the data object
        tvDetails.setText(packageCardItem.packageDetails);
        tvPrice.setText("Price: PHP"+packageCardItem.packagePrice.toString());
        tvEventName.setText(packageCardItem.name);
        ivPackage.setImageResource(packageCardItem.image);
        Intent intent = new Intent(convertView.getContext(),BookEvent.class);

        btnbookNow.setOnClickListener(view -> {
            intent.putExtra("event_name",packageCardItem.name);
            intent.putExtra("event_price",packageCardItem.packagePrice.toString());
            parent.getContext().startActivity(intent);
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
