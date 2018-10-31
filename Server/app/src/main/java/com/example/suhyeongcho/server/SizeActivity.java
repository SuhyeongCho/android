package com.example.suhyeongcho.server;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class SizeActivity extends AppCompatActivity {
    ImageButton but;
    Button submit;
    EditText size;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_size);

        but = findViewById(R.id.but);
        size = findViewById(R.id.size);
        submit = findViewById(R.id.submit);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent exit_intent = new Intent(SizeActivity.this,MainActivity.class);
                exit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                exit_intent.putExtra("KILL_APP",true);
                startActivity(exit_intent);
                finish();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = size.getText().toString();


                Intent intent1 = getIntent();
                Result result = (Result) intent1.getSerializableExtra("RESULT");


                Intent intent2 = new Intent(SizeActivity.this,ResultActivity.class);
                //put Extra로 Serializable을 implements한 객체를 보냄
                result.setDResult(str);
                result.calculateTotalResult();
                intent2.putExtra("RESULT",result);
                startActivity(intent2);
            }
        });
    }
}
