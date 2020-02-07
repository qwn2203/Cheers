package com.example.cheers;

import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;
import android.os.Handler;

import com.example.cheers.Activity.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ConnectionThread extends Thread{
    private final BluetoothSocket socket;
    private InputStream ins;
    private  OutputStream outs;
    public static final int RESPONSE_MESSAGE=10;
    Handler handler;
    final int RECIEVE_MESSAGE = 1;

    public ConnectionThread(BluetoothSocket socket, Handler handler){
        this.socket=socket;
        this.handler=handler;
        this.ins=null;
        this.outs=null;
        Log.i("[THREAD_CT]", "Creating thread");
        try {
            ins = socket.getInputStream();
            outs=socket.getOutputStream();
            outs.flush();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void run (){
        BufferedReader br= new BufferedReader(new InputStreamReader(ins));
        byte[] buffer = new byte[256];
        int bytes;
        Log.i("[THREAD_CT]", "Running thread");
        while (true)
            try{
                String resp= br.readLine();
                if(resp.contains("ok!")){
                    MainActivity.connectionSecured = true;
                }
                System.out.println("RESPONSE:" + resp);
                Message message=new Message();
                message.what= RESPONSE_MESSAGE;
                message.obj=resp;
                bytes = ins.read(buffer);
                handler.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();
                handler.sendMessage(message);
            }catch (IOException ioe){
                ioe.printStackTrace();
                break;
            }
    }


    public void write(byte[]bytes){
        try{
            Log.i("[THREAD_CT]", "Writting bytes");
            outs.write(bytes);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
    public void cancel(){
        try{
            socket.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}