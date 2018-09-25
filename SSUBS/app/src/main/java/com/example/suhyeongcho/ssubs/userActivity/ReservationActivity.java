package com.example.suhyeongcho.ssubs.userActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.suhyeongcho.ssubs.R;
import com.example.suhyeongcho.ssubs.userServer.MakeReservation;

import org.json.JSONObject;



public class ReservationActivity extends AppCompatActivity {
    private Button button;

    private EditText reservationName;
    private EditText reservationTime;
    private EditText reservationPhone;
    private EditText reservationPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Intent intent = getIntent();
        final String R_name = intent.getStringExtra("R_name");
        button = findViewById(R.id.reservation_button);

        reservationName = findViewById(R.id.reservation_name);
        reservationTime = findViewById(R.id.reservation_time);
        reservationPhone = findViewById(R.id.reservation_phone);
        reservationPeople = findViewById(R.id.reservation_people);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject job = new JSONObject();
                    job.put("restaurantName",R_name);
                    job.put("name",reservationName.getText().toString());
                    job.put("time",reservationTime.getText().toString());
                    job.put("phone",reservationPhone.getText().toString());
                    job.put("people",reservationPeople.getText().toString());
                    String send = job.toString();
                    new MakeReservation(ReservationActivity.this, R_name, reservationName.getText().toString()).execute(send);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
