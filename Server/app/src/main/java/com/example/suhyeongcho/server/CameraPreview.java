package com.example.suhyeongcho.server;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.List;

import android.hardware.camera2.*;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{

    private android.hardware.Camera mCamera;
    public List<android.hardware.Camera.Size> listPreviewSizes;
    private android.hardware.Camera.Size previewSize;
    private Context context;
    private Paint paint;

    // SurfaceView 생성자
    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mCamera = MainActivity.getCamera();
        if(mCamera == null){
            mCamera = android.hardware.Camera.open();
        }
        listPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();

    }

    //  SurfaceView 생성시 호출
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        try {
            // 카메라 객체를 사용할 수 있게 연결한다.
            if(mCamera  == null){
                mCamera  = android.hardware.Camera.open();
            }

            // 카메라 설정
            android.hardware.Camera.Parameters parameters = mCamera .getParameters();

            parameters.setPreviewSize(640,480);

            /*
            DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
            int screenWidth = metrics.widthPixels;
            int screenHeight = metrics.heightPixels;
            parameters.setPreviewSize(screenWidth,screenWidth);
            */

            parameters.setPictureSize(640,480);

            parameters.setFocusMode(parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

            // 카메라의 회전이 가로/세로일때 화면을 설정한다.
            if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                parameters.set("orientation", "portrait");
                mCamera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else {
                parameters.set("orientation", "landscape");
                mCamera.setDisplayOrientation(0);
                parameters.setRotation(0);
            }
            mCamera.setParameters(parameters);

            setWillNotDraw(false);
            mCamera.setPreviewDisplay(surfaceHolder);

            // 카메라 미리보기를 시작한다.
            mCamera.startPreview();

            // 자동포커스 설정
//            mCamera.autoFocus(new android.hardware.Camera.AutoFocusCallback() {
//                @Override
//                public void onAutoFocus(boolean success, android.hardware.Camera camera) {
//                    if(success){}
//                }
//            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint();
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        //  Set paint options
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.argb(255, 255, 255, 255));

        canvas.drawLine((screenWidth/3)*2,0,(screenWidth/3)*2,screenHeight,paint);
        canvas.drawLine((screenWidth/3),0,(screenWidth/3),screenHeight,paint);
        canvas.drawLine(0,(screenHeight/3)*2,screenWidth,(screenHeight/3)*2,paint);
        canvas.drawLine(0,(screenHeight/3),screenWidth,(screenHeight/3),paint);

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(screenWidth/2,screenHeight/2,10,paint);
    }

    // SurfaceView 의 크기가 바뀌면 호출
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {

        // 카메라 화면을 회전 할 때의 처리
        if (surfaceHolder.getSurface() == null){
            // 프리뷰가 존재하지 않을때
            return;
        }
        // 프리뷰를 다시 설정한다.
        try {
            mCamera .stopPreview();

            android.hardware.Camera.Parameters parameters = mCamera .getParameters();

            // 화면 회전시 사진 회전 속성을 맞추기 위해 설정한다.
            int rotation = MainActivity.getInstance.getWindowManager().getDefaultDisplay().getRotation();
            if (rotation == Surface.ROTATION_0) {
                mCamera .setDisplayOrientation(90);
                parameters.setRotation(90);
            }else if(rotation == Surface.ROTATION_90){
                mCamera .setDisplayOrientation(0);
                parameters.setRotation(0);
            }else if(rotation == Surface.ROTATION_180){
                mCamera .setDisplayOrientation(270);
                parameters.setRotation(270);
            }else{
                mCamera .setDisplayOrientation(180);
                parameters.setRotation(180);
            }

            // 변경된 화면 넓이를 설정한다.
            parameters.setPreviewSize(previewSize.width, previewSize.height);
            mCamera .setParameters(parameters);

            // 새로 변경된 설정으로 프리뷰를 시작한다
            mCamera .setPreviewDisplay(surfaceHolder);
            mCamera .startPreview();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // SurfaceView가 종료시 호출
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if(mCamera != null){
            // 카메라 미리보기를 종료한다.
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    // 화면이 회전할 때 화면 사이즈를 구한다.
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);

        if (listPreviewSizes != null) {
            previewSize = getPreviewSize(listPreviewSizes, width, height);
        }
    }
    public android.hardware.Camera.Size getPreviewSize(List<android.hardware.Camera.Size> sizes, int w, int h) {

        //w = 720;
        //h = 1080;
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;

        if (sizes == null)
            return null;

        android.hardware.Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (android.hardware.Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;

            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (android.hardware.Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }

        return optimalSize;
    }
}