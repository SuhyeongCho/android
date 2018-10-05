package com.example.suhyeongcho.server;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment4 extends Fragment {
    TextView textView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4,null);


        //프래그먼트에서는 값을 받고 하는데
        Bundle bundle = getArguments();
        String message = bundle.getString("C");

        textView = view.findViewById(R.id.result);



      textView.setText(message);


        return view;
    }
}
