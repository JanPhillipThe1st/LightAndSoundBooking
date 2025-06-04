package com.yamatoapps.lightandsoundbooking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = findViewById(R.id.lvLogin);
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvPassword = findViewById(R.id.tvPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

    layout.setVisibility(View.GONE);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                layout.setVisibility(View.VISIBLE);
            }
        };
        handler.postDelayed(runnable,2000);
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Logging in");
        progressDialog.setMessage("Please wait...");
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

    btnLogin.setOnClickListener(view ->{
        progressDialog.show();
        if (tvPassword.getText().toString().equals("admin")){
            progressDialog.dismiss();
            builder.setTitle("Login Success!");
            builder.setMessage("Logged in as Admin.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    startActivity(new Intent(MainActivity.this, AdminPanel.class));
                }
            });
            builder.create().show();
        }
        else {
            if (tvPassword.getText().toString().equals("customer")){
                progressDialog.dismiss();
                builder.setTitle("Login Success!");
                builder.setMessage("Logged in as Customer.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        startActivity(new Intent(MainActivity.this,LSMenu.class));
                    }
                });
                builder.create().show();
            }
            else{
                progressDialog.dismiss();
                builder.setTitle("Login Failed!");
                builder.setMessage("No user found!!!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        }
    });

    }
}