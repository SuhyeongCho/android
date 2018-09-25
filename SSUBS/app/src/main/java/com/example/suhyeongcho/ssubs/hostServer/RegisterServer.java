package com.example.suhyeongcho.ssubs.hostServer;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterServer extends AsyncTask<String, Void, String> {
    private String REGISTER_URL = "http://...";
    private final String TAG = "REGISTERSERVER";

    @Override
    protected String doInBackground(String... data) {
        String result = "fail";
        try{
            URL url = new URL(REGISTER_URL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(12000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/json");
            Log.d(TAG, "doInBackground: "+data[0]);
            byte[] outputInBytes = data[0].getBytes("UTF-8");
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

        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("success")){//등록성공하면...

        }else {//등록 실패하면...

        }
        super.onPostExecute(s);
    }
}
