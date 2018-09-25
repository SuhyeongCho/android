package com.example.suhyeongcho.server;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static CameraPreview surfaceView;
    private SurfaceHolder holder;
    private static Button camera_preview_button;
    private static android.hardware.Camera mCamera;
    private int RESULT_PERMISSIONS = 100;
    public static MainActivity getInstance;

    Button button;
    private static final int CAMERA_REQUEST = 1000;
    private android.hardware.Camera.ShutterCallback shutterCallback;
    private android.hardware.Camera.PictureCallback rawCallback,jpegCallback;
    Socket socket;

    String mRootPath;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=2000;

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

                    mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/storage/project.jpg";
                    Log.d("position",mRootPath);


                    File myExternalFile = new File(mRootPath);
                    myExternalFile.delete();
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            Bitmap capturedImg = (Bitmap) data.getExtras().get( "data" ) ;
            saveBitmaptoJpeg(capturedImg,"storage","project");
        }
    }
    public static void saveBitmaptoJpeg(Bitmap bitmap,String folder, String name){
        String ex_storage =Environment.getExternalStorageDirectory().getAbsolutePath();
        // Get Absolute Path in External Sdcard
        String folder_name = "/"+folder+"/";
        String file_name = name+".jpg";
        String string_path = ex_storage+folder_name;

        File file_path;
        try{
            file_path = new File(string_path);
            if(!file_path.isDirectory()){
                file_path.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(string_path+file_name);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

        }catch(FileNotFoundException exception){
            Log.e("FileNotFoundException", exception.getMessage());
        }catch(IOException exception){
            Log.e("IOException", exception.getMessage());
        }
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


        // SurfaceView 정의 - holder와 Callback을 정의한다.
        holder = surfaceView.getHolder();
        holder.addCallback(surfaceView);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RESULT_PERMISSIONS);

        }
        button = (Button) findViewById(R.id.camera);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(shutterCallback,rawCallback,jpegCallback);

                ConnectThread th = new ConnectThread(socket);
                th.start();

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

//public class MainActivity extends AppCompatActivity {
//
//    Socket socket;
//    private String html = "";
//    private Handler mHandler;
//    Camera camera;
//
//    SurfaceView surfaceView;
//    SurfaceHolder surfaceHolder;
//
//    private Button btn;
//    private ImageView ivPicture;
//    private String imagePath;
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        try{
//            //socket.close();
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//  //      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        setContentView(R.layout.activity_main);
//
//        //surfaceView = findViewById(R.id.surfaceView);
// //       surfaceHolder = surfaceView.getHolder();
//
//
//
//        btn = findViewById(R.id.btn);
//        ivPicture = findViewById(R.id.imageView);
////        btn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                ConnectThread th = new ConnectThread(socket);
////                th.start();
////            }
////        });
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isExistCameraApplication()){
//                    Intent cameraApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                    File picture = savePictureFile();
//
//                    if(picture != null){
//                        cameraApp.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(picture));
//                        startActivityForResult(cameraApp,10000);
//                    }
//                }else{
//                    Toast.makeText(MainActivity.this,"no camera",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//
//    protected void onActivityResult(int requestCode,int resultCode,Intent data){
//        if(requestCode == 10000 && resultCode == RESULT_OK){
//            BitmapFactory.Options factory = new BitmapFactory.Options();
//
//            factory.inJustDecodeBounds = false;
//            factory.inPurgeable = true;
//
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath,factory);
//            ivPicture.setImageBitmap(bitmap);
//
//        }
//    }
//
//    private boolean isExistCameraApplication(){
//        PackageManager packageManager = getPackageManager();
//
//        Intent cameraApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        List cameraApps = packageManager.queryIntentActivities(cameraApp,PackageManager.MATCH_DEFAULT_ONLY);
//
//        return cameraApps.size() > 0;
//
//    }
//
//    private File savePictureFile(){
//        PermissionRequester.Builder requester = new PermissionRequester.Builder(this);
//
//        int result = requester.create().request(
//                Manifest.permission.WRITE_EXTERNAL_STORAGE, 20000, new PermissionRequester.OnClickDenyButtonListener() {
//                    @Override
//                    public void onClick(Activity activity) {
//
//                    }
//                });
//
//        if(result == PermissionRequester.ALREADY_GRANTED||result == PermissionRequester.REQUEST_PERMISSION){
//
//            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String fileName = "IMG_"+timestamp;
//
//            File pictureStorage = new File(
//                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"MYAPP/"
//            );
//
//            if(!pictureStorage.exists()){
//                pictureStorage.mkdirs();
//            }
//
//            try{
//                File file = File.createTempFile(fileName,".jpg",pictureStorage);
//
//                imagePath = file.getAbsolutePath();
//
//                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                File f = new File(imagePath);
//                Uri contentUri = FileProvider.getUriForFile(MainActivity.this, "com.example.suhyeongcho.server.provider", f);
//                mediaScanIntent.setData(contentUri);
//                this.sendBroadcast(mediaScanIntent);
//                return file;
//            }catch(IOException e){
//                e.printStackTrace();
//            }
//        }else{}
//        return null;
//    }
//}
