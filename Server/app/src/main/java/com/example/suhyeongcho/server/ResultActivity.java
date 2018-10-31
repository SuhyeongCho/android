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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.InputStream;

public class ResultActivity extends AppCompatActivity {
    Fragment1 fr1;
    Fragment2 fr2;
    Fragment3 fr3;
    Fragment4 fr4;
    Fragment5 fr5;

    int page = 0;
    ImageButton left,right,but;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_result);


        //받을 때는
        Intent intent = getIntent();
        Result result = (Result) intent.getSerializableExtra("RESULT");
        //형식으로 받음

        Log.d("Haqqq2",result.getTotalResult());


        if(result!=null) {
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

            fr5 = new Fragment5();
            Bundle bundle5 = new Bundle();
            bundle5.putString("D", result.getDResult());
            fr5.setArguments(bundle5);

            left = findViewById(R.id.left);
            right = findViewById(R.id.right);


            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment, fr1).commit();
            left.setVisibility(View.INVISIBLE);
            right.setVisibility(View.VISIBLE);
        }

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                switch (page){
                    case 0 : break;
                    case 1 : left.setVisibility(View.INVISIBLE);right.setVisibility(View.VISIBLE);transaction.replace(R.id.fragment, fr1); page--; break;
                    case 2 : left.setVisibility(View.VISIBLE);right.setVisibility(View.VISIBLE);transaction.replace(R.id.fragment, fr2); page--; break;
                    case 3 : left.setVisibility(View.VISIBLE);right.setVisibility(View.VISIBLE);transaction.replace(R.id.fragment, fr3); page--; break;
                    case 4 : left.setVisibility(View.VISIBLE);right.setVisibility(View.VISIBLE);transaction.replace(R.id.fragment, fr4); page--; break;

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
                    case 0 : left.setVisibility(View.VISIBLE);right.setVisibility(View.VISIBLE);transaction.replace(R.id.fragment, fr2); page++; break;
                    case 1 : left.setVisibility(View.VISIBLE);right.setVisibility(View.VISIBLE);transaction.replace(R.id.fragment, fr3); page++; break;
                    case 2 : left.setVisibility(View.VISIBLE);right.setVisibility(View.VISIBLE);transaction.replace(R.id.fragment, fr4); page++; break;
                    case 3 : left.setVisibility(View.VISIBLE);right.setVisibility(View.INVISIBLE);transaction.replace(R.id.fragment, fr5); page++; break;
                    case 4 : break;


                }
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        but = findViewById(R.id.but);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exit_intent = new Intent(ResultActivity.this,MainActivity.class);
                exit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                exit_intent.putExtra("KILL_APP",true);
                startActivity(exit_intent);
                finish();
            }
        });

    }



}
