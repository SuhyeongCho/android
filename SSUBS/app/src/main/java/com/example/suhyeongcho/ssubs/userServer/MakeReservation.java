package com.example.suhyeongcho.ssubs.userServer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.suhyeongcho.ssubs.userActivity.ReservationActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MakeReservation extends AsyncTask<String, Void, String> {
    private ReservationActivity reservationActivity;
    private String RESERVATIon_URL = "http://";
    private final String TAG = "MAKERESERVATION";

    private String restaruant;
    private String user_name;
    public MakeReservation(ReservationActivity reservationActivity, String R_name, String user_name){
        this.reservationActivity = reservationActivity;
        restaruant = R_name;
        this.user_name = user_name;
    }


    @Override
    protected String doInBackground(String... reservation) {
        String result = "";
        try {
        URL url = new URL(RESERVATIon_URL);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(12000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type","application/json");

        byte[] outputInBytes = reservation[0].getBytes("UTF-8");
        OutputStream os = conn.getOutputStream();
        os.write(outputInBytes);
        os.close();

          int resCode = conn.getResponseCode();
          Log.d(TAG, "doInBackground: "+resCode);

          InputStream is = conn.getInputStream();
          BufferedReader br = new BufferedReader(new InputStreamReader(is));
          String line;
          StringBuffer response = new StringBuffer();
          while((line = br.readLine()) != null) {
              response.append(line);
              response.append('\r');
          }
          br.close();
          result = response.toString().trim();
          }catch (Exception e){
              e.printStackTrace();
          }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("success")){//shaerd에 가게이름 저장 및 현 reservation 종료 및 예약 성공 토스트
            SharedPreferences pref = reservationActivity.getApplicationContext().getSharedPreferences("reservation",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("restaurant",restaruant);//
            editor.putString("userName",user_name);
            editor.commit();
            Toast.makeText(reservationActivity.getApplicationContext(),"예약에 성공하였습니다!",Toast.LENGTH_SHORT);
            reservationActivity.finish();
        }
        super.onPostExecute(s);
    }
}
