package com.yamatoapps.lightandsoundbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ManageCompany extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_company);

        ArrayList<LSMenuCardItem> lsMenuCardItems= new ArrayList<LSMenuCardItem>();
        LSAdapterManagement adapter = new LSAdapterManagement(this, lsMenuCardItems);
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
                ListView lvLS = (ListView) findViewById(R.id.lvLS);
                lvLS.setAdapter(adapter);
            }
        });
    }
}