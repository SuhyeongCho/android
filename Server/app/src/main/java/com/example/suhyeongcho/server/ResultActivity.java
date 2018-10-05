package com.example.suhyeongcho.server;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;

public class ResultActivity extends AppCompatActivity {
    Fragment1 fr1;
    Fragment2 fr2;
    Fragment3 fr3;
    Fragment4 fr4;

    int page = 0;
    Button left,right;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        //받을 때는
        Intent intent = getIntent();
        Result result = (Result) intent.getSerializableExtra("RESULT");
        //형식으로 받음

        Log.d("Haqqq2",result.getTotalResult());


        //액티비티에서 값을 넣어서 전달하고
        fr1 = new Fragment1();
        Bundle bundle1 = new Bundle();
        bundle1.putString("TOTAL", result.getTotalResult());
        fr1.setArguments(bundle1);

        //액티비티에서 값을 넣어서 전달하고
        fr2 = new Fragment2();
        Bundle bundle2 = new Bundle();
        bundle2.putString("A", result.getAResult());
        fr2.setArguments(bundle2);

        //액티비티에서 값을 넣어서 전달하고
        fr3 = new Fragment3();
        Bundle bundle3 = new Bundle();
        bundle3.putString("B", result.getBResult());
        fr3.setArguments(bundle3);

        //액티비티에서 값을 넣어서 전달하고
        fr4 = new Fragment4();
        Bundle bundle4 = new Bundle();
        bundle4.putString("C", result.getCResult());
        fr4.setArguments(bundle4);

        Button left = findViewById(R.id.left);
        Button right = findViewById(R.id.right);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment,fr1).commit();

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                switch (page){
                    case 0 : break;
                    case 1 : transaction.replace(R.id.fragment, fr1); page--; break;
                    case 2 : transaction.replace(R.id.fragment, fr2); page--; break;
                    case 3 : transaction.replace(R.id.fragment, fr3); page--; break;
                }
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                switch (page){
                    case 0 : transaction.replace(R.id.fragment, fr2); page++; break;
                    case 1 : transaction.replace(R.id.fragment, fr3); page++; break;
                    case 2 : transaction.replace(R.id.fragment, fr4); page++; break;
                    case 3 : break;


                }
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        Button but = findViewById(R.id.but);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        반투명 처리하는법
//        Paint paint = new Paint();
//
//        paint.setColor(Color.BLACK);
//
//        paint.setAlpha(70);
//
//        ((RelativeLayout)findViewById(R.id.layout)).setBackgroundColor(paint.getColor());
    }



}
