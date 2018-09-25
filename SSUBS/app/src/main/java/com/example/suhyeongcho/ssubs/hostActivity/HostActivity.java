package com.example.suhyeongcho.ssubs.hostActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.suhyeongcho.ssubs.R;


public class HostActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        button = findViewById(R.id.registerButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        /*
        여긴 만약에 등록이 되어있는 경우 예약승인 및 승인 대기 리스트 보여주기
        이 정보를 읽어올 통신 추가 구현 필요
         */
    }
}
