package com.example.suhyeongcho.ssubs.userServer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.suhyeongcho.ssubs.userActivity.Info;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetInfo extends AsyncTask<String,Void,String> {
    private Context context;
    private final  String TAG = "GETINFO";

    private String URL = "http://10.27.24.11:3000/info/";

    public GetInfo(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... name) {
        URL += name[0];

        String result = "fail";
        try{
            java.net.URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(12000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(false);

            int resCode = conn.getResponseCode();
            Log.d(TAG, "doInBackground: "+resCode);
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
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
        super.onPostExecute(s);
        Intent intent = new Intent(context, Info.class);
        intent.putExtra("information",s);
        context.startActivity(intent);
    }
}
