package com.example.suhyeongcho.ssubs.hostActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.suhyeongcho.ssubs.R;
import com.example.suhyeongcho.ssubs.hostServer.LoginServer;

import org.json.JSONObject;


public class SignUpActivity extends AppCompatActivity {
    private Button signup;

    private EditText name;
    private EditText id;
    private EditText pw;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.signupName);
        id = findViewById(R.id.signupID);
        pw = findViewById(R.id.signupPassword);
        phone = findViewById(R.id.signupPhone);

        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    JSONObject job = new JSONObject();
                    job.put("user_name",name.getText().toString());
                    job.put("user_id",id.getText().toString());
                    job.put("password",pw.getText().toString());
                    job.put("tel",phone.getText().toString());
                    String send = job.toString();
                    new LoginServer(getApplicationContext(),"SignUp").execute(send);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
