package com.example.cheers;

import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;
import android.os.Handler;

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
        Log.i("[THREAD_CT]", "Running thread");
        while (true)
            try{
                String resp= br.readLine();
                Message message=new Message();
                message.what= RESPONSE_MESSAGE;
                message.obj=resp;
                handler.sendMessage(message);
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
    }


    public void write(byte[]bytes){
        try{
            Log.i("[THREAD_CT]", "Writting bytes");
            outs.write(bytes);;
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