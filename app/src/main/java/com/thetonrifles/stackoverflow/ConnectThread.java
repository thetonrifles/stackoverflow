package com.thetonrifles.stackoverflow;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.util.Log;

import java.io.IOException;

public class ConnectThread extends Thread {

    private final BluetoothSocket mSocket;
    private final BluetoothDevice mDevice;
 
    public ConnectThread(BluetoothDevice device) {
        BluetoothSocket tmp = null;
        mDevice = device;
 
        try {
            ParcelUuid uuid = mDevice.getUuids()[0];

            if(uuid ==  null) {
                Log.e("BT", "UUID = null");
            } else {
                Log.d("BT", "UUID = " + uuid.toString());
                tmp = device.createRfcommSocketToServiceRecord(uuid.getUuid());
            }
        } catch (IOException ignored) {
        }
        mSocket = tmp;
    }

    @Override
    public void run() {
        // Cancel discovery because it will slow down the connection
        BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
        bt.cancelDiscovery();
 
        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                mSocket.close();
            } catch (IOException closeException) { }
            return;
        }
 
        // Do work to manage the connection (in a separate thread)
        Log.i("BT", "manage connected socket");
    }

    public void cancel() {
        try {
            mSocket.close();
        } catch (IOException ignored) {
        }
    }

}