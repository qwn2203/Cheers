package com.example.cheers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class BluetoothConnectionManager {

    public String mac;
    public final static int REQUEST_ENABLE_BT=1;
    private static final UUID MY_UIID=
            UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    BluetoothAdapter adapter;
    BluetoothSocket socket;
    BluetoothDevice device;
    ConnectionThread thread;
    public Handler handler;
    private static BluetoothConnectionManager INSTANCE = null;

    private BluetoothConnectionManager(String mac) {
        this.mac = mac;
        adapter= BluetoothAdapter.getDefaultAdapter();
        if (adapter.isEnabled()){
            device=adapter.getRemoteDevice(mac);
            try {
                socket= device.createRfcommSocketToServiceRecord(MY_UIID);
                socket.connect();
            }
            catch (IOException ioe){
                ioe.printStackTrace();
                try {
                    if (socket!=null)
                        socket.close();
                }
                catch(IOException ioe2){
                    ioe2.printStackTrace();
                }
            }
            handler=new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message mssg){
                    if (mssg.what==ConnectionThread.RESPONSE_MESSAGE){
                        String txt=(String)mssg.obj;
                    }
                }
            };
            thread= new ConnectionThread(socket,handler);
            thread.start();
        }
    };

    public static BluetoothConnectionManager getInstance(String mac) {
        if(INSTANCE == null)
            INSTANCE = new BluetoothConnectionManager(mac);
        return INSTANCE;
    }

    public void sendMessage(String message){
        if (socket.isConnected()&& thread!=null){
            thread.write(message.getBytes());
        }
    }

    public void send(byte[] b){
        if (socket.isConnected()&& thread!=null){
            try{
                socket.getOutputStream().write(b);
            }
            catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
    }
}
