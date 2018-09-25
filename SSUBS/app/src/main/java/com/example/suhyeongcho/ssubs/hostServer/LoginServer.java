package com.example.suhyeongcho.ssubs.hostServer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.suhyeongcho.ssubs.hostActivity.HostActivity;
import com.example.suhyeongcho.ssubs.hostActivity.LoginActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



import static android.widget.Toast.LENGTH_SHORT;

public class LoginServer extends AsyncTask<String, Void, String> {
    private String SIGNUP_URL = "http://172.20.10.6:3000/insert/user";
    private String LOGIN_URL = "http://172.20.10.6:3000/login";
    private final String TAG = "LOGINSERVER";
    private boolean login;
    private Context context;
    private LoginActivity loginActivity;

    private String ID;
        public LoginServer(Context context, String type){

        Log.d(TAG, "LoginServer: construct LoginServer TYPE : "+type);
        this.context = context;
        if(type.equals("LogIn")){
            login = true;
        }else if(type.equals("SignUp")){
            login = false;
        }
    }

    public LoginServer(LoginActivity loginActivity, Context context, String type){
        this(context,type);
        this.loginActivity = loginActivity;
    }

    @Override
    protected String doInBackground(String... data) {
        String result = "fail";
        //ID = data[1];//sharedPreference를 위한 id저장
        try{
            URL url;
            if(login){
                url = new URL(LOGIN_URL);
            }else {
                url = new URL(SIGNUP_URL);
            }
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
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG, "onPostExecute: "+login);
        Log.d(TAG, "onPostExecute: answer : "+ s);
        if(login){//사전에 등록한 정보도 같이 보내서 예약현황을 확인해야할듯
            if(s.equals("success")){
                Toast.makeText(context,"성공적!"+s, LENGTH_SHORT);
                Intent intent = new Intent(context, HostActivity.class);
                SharedPreferences pref = context.getSharedPreferences("login",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("HostID",ID);//
                editor.putString("login","success");
                editor.commit();
                loginActivity.finish();//이부분 에러 우려
                context.startActivity(intent);//로그인 성공하면 로그아웃 할 때 까지 로그인 상태 유지, 즉 로그인 페이지가 나오면 안됨
            }else {
                Toast.makeText(context,"ID 또는 비밀번호가 올바르지 않습니다. 다시 입력해 주십시오.", LENGTH_SHORT);
                //로그인 실패
            }
        }else {//SIGNUP
            if(s.equals("success")){
                Log.d(TAG, "onPostExecute: 회원가입 성공은 함");
                Toast.makeText(context,"회원가입 성공!",LENGTH_SHORT);
                //회원가입 성공
                //쌓은 액티비티 소멸 방법 구상
                // 액티비티 소멸은 finish()쓰면 되지 않냐
            }else {
                //실패 원인 구명!
                if(s.equals("password_fail")){
                    //비밀번호 확인이 달라
                    //이럴 때 토스트 써서 밑에 비밀번호가 다르다고 말하면 되는거 아님?
                    Toast.makeText(context,"비밀번호가 올바르지 않습니다.",LENGTH_SHORT).show();
                }
                else if(s.equals("id_fail")){
                    //아이디가 중복
                    Toast.makeText(context,"아이디가 중복되었습니다.",LENGTH_SHORT).show();

                }
            }
        }
    }
}
