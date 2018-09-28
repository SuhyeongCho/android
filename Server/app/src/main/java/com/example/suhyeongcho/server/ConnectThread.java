package com.example.suhyeongcho.server;

import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnectThread extends Thread {

    private BufferedReader networkReader;
    private BufferedWriter networkWriter;
    private String ip = "10.27.12.4";
    private int port = 3000;
    private Socket socket;
    File file;

    ConnectThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/storage/project.jpg");
            socket = new Socket(ip,port);
            FileInputStream fis = new FileInputStream(file);

//            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
//            out.println("aa");
            DataInputStream dis = new DataInputStream(fis);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            byte[] buf = new byte[640*480];
            while(dis.read(buf)>0){
                dos.write(buf);
                dos.flush();
            }
            dos.close();
            //out.println(file);

//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String a = in.readLine();
//            Log.d("a",a);
            socket.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
