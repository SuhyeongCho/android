package com.example.suhyeongcho.server;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment5 extends Fragment {
    TextView textView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment5,null);


        Bundle bundle = getArguments();
        String message = bundle.getString("D");

        textView = view.findViewById(R.id.result);

        if(message.equals("0")){
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            textView.setText("기준치보다 지름이 작습니다.");
        }
        else if(message.equals("100")) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            textView.setText("기준치보다 지름이 큽니다.");
        }
        return view;
    }
}
