package com.yamatoapps.lightandsoundbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookEvent extends AppCompatActivity {

    TextView tvPayment, tvEventName;
    Button btnSelectDate, btnConfirmBooking;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Intent packageIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_event);
        btnSelectDate = findViewById(R.id.btnEventDate);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);



        tvPayment = findViewById(R.id.tvPayment);
        tvEventName = findViewById(R.id.tvEventName);

       packageIntent =  getIntent();
        tvPayment.setText(getIntent().getStringExtra("event_price"));
        tvEventName.setText(getIntent().getStringExtra("event_name"));

        final Calendar c = Calendar.getInstance();

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.

                // on below line we are getting
                // our day, month and year.
                int month1,date1,year1;
                month1 = c.get(Calendar.MONTH);
                date1 = c.get(Calendar.DAY_OF_MONTH);
                year1 = c.get(Calendar.YEAR);
                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        BookEvent.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                c.set(Calendar.DATE,dayOfMonth);
                                c.set(Calendar.MONTH,monthOfYear);
                                c.set(Calendar.YEAR,year);
                                TimePickerDialog timePickerDialog = new TimePickerDialog(
                                        // on below line we are passing context.
                                        BookEvent.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                                c.set(Calendar.HOUR_OF_DAY,i);
                                                c.set(Calendar.MINUTE,i1);
                                                c.set(Calendar.SECOND,0);
                                                c.set(Calendar.MILLISECOND,0);
                                                btnSelectDate.setText(c.getTime().toLocaleString());
                                            }
                                        },0,0,true);
                                // at last we are calling show to
                                // display our date picker dialog.
                                timePickerDialog.show();
                            }
                        },year1,month1,date1);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });
        btnConfirmBooking.setOnClickListener(view -> {
            Map<String,Object> booking  = new HashMap<String,Object>();
            booking.put("date_booked",Calendar.getInstance().getTime());
            booking.put("event_name",packageIntent.getStringExtra("event_name"));
            booking.put("event_price",Double.parseDouble(packageIntent.getStringExtra("event_price")));
            db.collection("light_and_sound_bookings").add(booking).addOnSuccessListener( documentReference ->{

            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(BookEvent.this);
            alertDialogBuilder.setTitle("Operation Successful");
            alertDialogBuilder.setMessage("Congratulations. your booking is now complete!");
            alertDialogBuilder.setPositiveButton("OK",(dialogInterface, i) -> {
                dialogInterface.dismiss();
                finish();
            });
            alertDialogBuilder.create().show();
            });
        });
    }
}