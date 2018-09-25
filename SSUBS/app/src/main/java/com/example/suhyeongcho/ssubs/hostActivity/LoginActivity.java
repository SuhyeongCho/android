package com.example.suhyeongcho.ssubs.hostActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.suhyeongcho.ssubs.R;
import com.example.suhyeongcho.ssubs.hostServer.LoginServer;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private Button signin;
    private Button signup;

    private EditText loginID;
    private EditText loginPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);

        loginID = findViewById(R.id.login_ID);
        loginPW = findViewById(R.id.login_PW);

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    JSONObject job = new JSONObject();
                    job.put("ID",loginID.getText().toString());
                    job.put("PW",loginPW.getText().toString());
                    String[] send = new String[2];
                    send[0] = job.toString();
                    send[1] = loginID.getText().toString();
                    new LoginServer(LoginActivity.this, getApplicationContext(),"LogIn").execute(send);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
