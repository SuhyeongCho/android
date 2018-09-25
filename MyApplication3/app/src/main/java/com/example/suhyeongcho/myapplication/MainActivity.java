package com.example.suhyeongcho.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button login,register;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        listView = findViewById(R.id.listview);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        ArrayList<ReservationItem> items = new ArrayList<>();
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());
        items.add(new ReservationItem());

        ListAdapter listAdapter = new ListAdapter(items);

        listView.setAdapter(listAdapter);
    }
}
