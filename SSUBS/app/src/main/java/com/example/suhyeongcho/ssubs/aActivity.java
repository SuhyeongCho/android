package com.example.suhyeongcho.ssubs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class aActivity extends AppCompatActivity{
    EditText edittext;
    TextView textView;
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity);
        edittext=(EditText)findViewById(R.id.editText);
        button=(Button)findViewById(R.id.button);
        textView=(TextView)findViewById(R.id.textView2);
        SharedPreferences pref = getSharedPreferences("Name", Activity.MODE_PRIVATE);
        String str = pref.getString("name", ""); // 키값으로 꺼냄
        edittext.setText(pref.getString("who","")); // EditText에 반영함
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(edittext.getText());
            }
        });

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new afragment()).commit();

    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences pref = getSharedPreferences("Name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("who",edittext.getText().toString());
        editor.commit();

    }
}
