package com.example.suhyeongcho.ssubs.userServer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.suhyeongcho.ssubs.userActivity.RestaurantList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetList extends AsyncTask<String, Void, String> {
    private Context context;
    private final String TAG = "GETLIST";
    private String SERVER_URL = "http://10.27.24.11:3000/list/";

    public GetList(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... category_num) {
        SERVER_URL += category_num[0];
        Log.d(TAG, "URL : "+ SERVER_URL);
        String result = "fail";
        try{
            URL url = new URL(SERVER_URL);
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
        Intent intent = new Intent(context, RestaurantList.class);
        intent.putExtra("categoryList",s);
        Log.d(TAG, "onPostExecute: "+s);
        context.startActivity(intent);
    }
}

/*
public class ServerCall extends AsyncTask<String, Void, String> {
    Context context;
    TextView keyWord;
    String SERVER_URL = "http://52.79.233.68:3000/insert";
    final String TAG = "ServerCall";

    public ServerCall(Context context, TextView keyWord){
        this.context = context.getApplicationContext();
        this.keyWord = keyWord;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = "fail";
        try{
            URL url = new URL(SERVER_URL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(12000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/json");

            byte[] outputInBytes = strings[0].getBytes("UTF-8");
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
            result = response.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG, "onPostExecute: "+s);
        keyWord.setText(s);
        context.startActivity(new Intent(context, ShowMap.class));
    }
}

 */