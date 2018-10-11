package com.example.suhyeongcho.server;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.Buffer;

public class ConnectThread extends Thread {

    private String ip = "10.27.12.157";
    private int port = 3000;
    final int IMAGE_SIZE = 1280*720;

    private Socket socket;
    private File file;
    private Result result;
    String res;

    ConnectThread(Socket socket,Result result){
        this.socket = socket;
        this.result = result;
    }

    @Override
    public void run() {
        try {

            socket = new Socket(ip, port);
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/storage/project.jpg");
            FileInputStream fis = new FileInputStream(file);

            DataInputStream dis = new DataInputStream(fis);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            byte[] buf = new byte[IMAGE_SIZE];
            while (dis.read(buf) > 0) {
                dos.write(buf);
                dos.flush();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            res = bufferedReader.readLine();

            result.setResult(res);

            bufferedReader.close();

            socket.close();

            }catch(Exception e){
            Log.d("Haqqq",e.getMessage());
        }
    }
}
