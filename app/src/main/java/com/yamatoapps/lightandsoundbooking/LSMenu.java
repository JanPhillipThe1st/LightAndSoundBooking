package com.yamatoapps.lightandsoundbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LSMenu extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsmenu);
        ArrayList<LSMenuCardItem> lsMenuCardItems= new ArrayList<LSMenuCardItem>();
        LSAdapter adapter = new LSAdapter(this, lsMenuCardItems);
/*
        adapter.add(new LSMenuCardItem("Badsbro Pro Audio",5,R.drawable.banner_badsbro,"Professional Lights and Sound, LED Wall and Stage roofing"));
*/
/*
        adapter.add(new LSMenuCardItem("Party Fx Audio",3,R.drawable.banner_pfx,"Professional Lights and Sound"));
*/
/*
        adapter.add(new LSMenuCardItem("M-Audio",5,R.drawable.banner_m_audio,"Professional Lights and Sound."));
*/
/*
        adapter.add(new LSMenuCardItem("EAV Lights and Sound",5,R.drawable.banner_eav,"Professional Lights and Sound"));
*/
/*
        adapter.add(new LSMenuCardItem("LS Audio Werx",5,R.drawable.banner_ls,"Professional Lights and Sound"));
*/
/*
        adapter.add(new LSMenuCardItem("Volcano Lights and Sound",5,R.drawable.banner_volcano,"Professional Lights and Sound"));
*/
/*
        adapter.add(new LSMenuCardItem("RBA Pro Audio",5,R.drawable.banner_rba,"Professional Lights and Sound"));
*/
        db.collection("ls_companies").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    adapter.add(new LSMenuCardItem(documentSnapshot.getString("name"),
                            Double.parseDouble (documentSnapshot.get("rating").toString()),
                            documentSnapshot.getString("description"),
                            documentSnapshot.getString("image_url"),documentSnapshot.getId())
                    );
                }
        GridView gridView = (GridView) findViewById(R.id.gvCard);
        gridView.setAdapter(adapter);
            }
        });
    }
}