package com.example.suhyeongcho.server;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static CameraPreview surfaceView;
    private SurfaceHolder holder;
    private static android.hardware.Camera mCamera;
    private int RESULT_PERMISSIONS = 100;
    public static MainActivity getInstance;

    Button button;
    private android.hardware.Camera.ShutterCallback shutterCallback;
    private android.hardware.Camera.PictureCallback rawCallback,jpegCallback;
    Socket socket;
    ConnectThread th;

    String mRootPath;


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 카메라 프리뷰를  전체화면으로 보여주기 위해 셋팅한다.
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // 안드로이드 6.0 이상 버전에서는 CAMERA 권한 허가를 요청한다.
        requestPermissionCamera();


        shutterCallback = new android.hardware.Camera.ShutterCallback() {
            @Override
            public void onShutter() {

            }
        };
        rawCallback = new android.hardware.Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, android.hardware.Camera camera) {

            }
        };
        jpegCallback = new android.hardware.Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
                try{

                    mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/storage";
                    Log.d("position",mRootPath);

                    File folder = new File(mRootPath);
                    if(!folder.isDirectory()){
                        folder.mkdirs();
                    }
                    File myExternalFile = new File(mRootPath+"/project.jpg");
                    if(!folder.isFile()) {
                        myExternalFile.delete();
                    }
                    myExternalFile.createNewFile();
                    FileOutputStream output = new FileOutputStream(myExternalFile);
                    output.write(data);
                    output.flush();
                    output.close();
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        };


    }

    public static android.hardware.Camera getCamera(){
        return mCamera;
    }
    private void setInit(){
        getInstance = this;

        // 카메라 객체를 R.layout.activity_main의 레이아웃에 선언한 SurfaceView에서 먼저 정의해야 함으로 setContentView 보다 먼저 정의한다.

        mCamera = android.hardware.Camera.open();

        setContentView(R.layout.activity_main);

        // SurfaceView를 상속받은 레이아웃을 정의한다.
        surfaceView = (CameraPreview) findViewById(R.id.preview);
        //surfaceView.layout(300,300,1000,1000);


        // SurfaceView 정의 - holder와 Callback을 정의한다.
        holder = surfaceView.getHolder();
        holder.addCallback(surfaceView);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RESULT_PERMISSIONS);

        }
        button = findViewById(R.id.camera);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mCamera.takePicture(shutterCallback,rawCallback,jpegCallback);

//                th = new ConnectThread(socket);
//                th.start();
//                try {
//                    조인하면 쓰레드 다 할 때까지 멈춘다
//                    th.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                //put Extra로 Serializable을 implements한 객체를 보냄
                //intent.putExtra("OBJECT",data);
                //받을 때는
                //Intent intent = getIntent();
                //Data data = (Data) intent.getSerializableExtra("OBJECT");
                //형식으로 받음
                //intent.putExtra()
                startActivity(intent);

            }
        });
    }

    public boolean requestPermissionCamera(){
        int sdkVersion = Build.VERSION.SDK_INT;
        if(sdkVersion >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        RESULT_PERMISSIONS);

            }else {
                setInit();
            }
        }else{  // version 6 이하일때
            setInit();
            return true;
        }

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (RESULT_PERMISSIONS == requestCode) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 허가시
                setInit();
            } else {
                // 권한 거부시
            }
            return;
        }

    }
}
