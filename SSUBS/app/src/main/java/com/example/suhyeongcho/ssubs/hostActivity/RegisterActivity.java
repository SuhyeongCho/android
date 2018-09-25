package com.example.suhyeongcho.ssubs.hostActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.suhyeongcho.ssubs.R;
import com.example.suhyeongcho.ssubs.hostServer.RegisterServer;

import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {
    private Button register;

    private EditText register_table;
    private EditText register_start_time;
    private EditText register_end_time;
    private EditText register_address;
    private EditText register_phone;

    private CheckBox register_category_1;
    private CheckBox register_category_2;
    private CheckBox register_category_3;
    private CheckBox register_category_4;
    private CheckBox register_category_5;
    private CheckBox register_category_6;
    private CheckBox register_category_7;
    private CheckBox register_category_8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_table = findViewById(R.id.register_table);
        register_start_time = findViewById(R.id.register_start_time);
        register_end_time = findViewById(R.id.register_end_time);
        register_address = findViewById(R.id.register_address);
        register_phone = findViewById(R.id.register_phone);

        register_category_1 = findViewById(R.id.register_category_1);
        register_category_2 = findViewById(R.id.register_category_2);
        register_category_3 = findViewById(R.id.register_category_3);
        register_category_4 = findViewById(R.id.register_category_4);
        register_category_5 = findViewById(R.id.register_category_5);
        register_category_6 = findViewById(R.id.register_category_6);
        register_category_7 = findViewById(R.id.register_category_7);
        register_category_8 = findViewById(R.id.register_category_8);


        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    JSONObject job = new JSONObject();
                    job.put("register_table",register_table.getText().toString());
                    job.put("register_start_time",register_start_time.getText().toString());
                    job.put("register_end_time",register_end_time.getText().toString());
                    job.put("register_address",register_address.getText().toString());
                    job.put("register_phone",register_phone.getText().toString());

                    job.put("register_category_1",register_category_1.isChecked());
                    job.put("register_category_2",register_category_2.isChecked());
                    job.put("register_category_3",register_category_3.isChecked());
                    job.put("register_category_4",register_category_4.isChecked());
                    job.put("register_category_5",register_category_5.isChecked());
                    job.put("register_category_6",register_category_6.isChecked());
                    job.put("register_category_7",register_category_7.isChecked());
                    job.put("register_category_8",register_category_8.isChecked());
                    String send = job.toString();
                    new RegisterServer().execute(send);
                }catch (Exception e){
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
}
