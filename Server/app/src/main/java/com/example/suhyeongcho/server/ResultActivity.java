package com.example.suhyeongcho.server;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ResultActivity extends AppCompatActivity {
    Fragment1 fr1;
    Fragment2 fr2;
    Fragment3 fr3;

    int page = 0;
    Button left,right;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        //액티비티에서 값을 넣어서 전달하고
        //MyFragment myFragment = new MyFragment();
        //Bundle bundle = new Bundle();
        //bundle.putString("key", "전달할 메세지");
        //myFragment.setArguments(bundle);
        //
        //
        //프래그먼트에서는 값을 받고 하는데
        // Bundle bundle = getArguments();
        //String message = bundle.getString("key");

        fr1 = new Fragment1();
        fr2 = new Fragment2();
        fr3 = new Fragment3();

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
                    case 2 : break;

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
