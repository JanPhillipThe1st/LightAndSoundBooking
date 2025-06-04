package com.yamatoapps.lightandsoundbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

public class SearchLightandSound extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_lightand_sound);
        ArrayList<PackageCardItem> arrayOfPackages= new ArrayList<PackageCardItem>();
        EventAdapter adapter = new EventAdapter(this, arrayOfPackages);
        Intent lightAndSoundIntent = getIntent();
        String lightAndSound =  lightAndSoundIntent.getStringExtra("lightAndSound");
        Log.d("LS Intent",lightAndSound);
        adapter.add(new PackageCardItem("Full set up Lights and sound with band instruments 5 set LED video wall", R.drawable.concert_badsbro,25000.00,"Concert"));
        adapter.add(new PackageCardItem("4 units moving heads\n4 units Active AD Speaker\n16 units LED Par Light\n6 units Amber Light\n1 Unit Low Laying Fog\n4 Units Sparkular Machines", R.drawable.wedding,20000.00,"Wedding"));
        adapter.add(new PackageCardItem("Full set up Lights and sound with band instruments 5 set LED video wall", R.drawable.wedding_party_fx,25000.00,"Party"));
        adapter.add(new PackageCardItem("4 units moving heads\n4 units Active AD Speaker\n16 units LED Par Light\n6 units Amber Light\n1 Unit Low Laying Fog\n4 Units Sparkular Machines", R.drawable.debut_party_fx,20000.00, "Debut"));
        adapter.add(new PackageCardItem("Full set up Lights and sound with band instruments 5 set LED video wall", R.drawable.concert_badsbro,25000.00,"Concert"));
        adapter.add(new PackageCardItem("Full set up Lights and sound with band instruments 5 set LED video wall", R.drawable.debut_ls,25000.00,"Debut"));
        adapter.add(new PackageCardItem("4 units moving heads\n4 units Active AD Speaker\n16 units LED Par Light\n6 units Amber Light\n1 Unit Low Laying Fog\n4 Units Sparkular Machines", R.drawable.wedding_ls,20000.00,"Wedding"));
        adapter.add(new PackageCardItem("Full set up Lights and sound with band instruments 5 set LED video wall", R.drawable.pageant_m_audio,25000.00,"Pageant"));
        adapter.add(new PackageCardItem("Full set up Lights and sound with band instruments 5 set LED video wall", R.drawable.concert_m_audio,25000.00,"Concert"));
        adapter.add(new PackageCardItem("4 units moving heads\n4 units Active AD Speaker\n16 units LED Par Light\n6 units Amber Light\n1 Unit Low Laying Fog\n4 Units Sparkular Machines", R.drawable.wedding_m_audio,20000.00,"Wedding"));
        adapter.add(new PackageCardItem("Full set up Lights and sound with band instruments 5 set LED video wall", R.drawable.wedding_rba,25000.00,"Wedding"));
        adapter.add(new PackageCardItem("Full set up Lights and sound with band instruments 5 set LED video wall", R.drawable.rave_party_volcano,25000.00,"Party"));
        adapter.add(new PackageCardItem("4 units moving heads\n4 units Active AD Speaker\n16 units LED Par Light\n6 units Amber Light\n1 Unit Low Laying Fog\n4 Units Sparkular Machines", R.drawable.pageant_volcano,20000.00,"Pageant"));
        GridView gridView = (GridView) findViewById(R.id.gvCard);
        gridView.setAdapter(adapter);
        
    }
}