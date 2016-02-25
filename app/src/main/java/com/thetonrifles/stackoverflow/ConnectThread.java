package com.thetonrifles.stackoverflow;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.util.Log;

import java.io.IOException;

public class ConnectThread extends Thread {

    private static final String LOG_TAG = "BT";

    private BluetoothSocket mSocket;
    private BluetoothDevice mDevice;
    private Callback mCallback;

    public ConnectThread(BluetoothDevice device, Callback callback) {
        mDevice = device;
        mCallback = callback;
    }

    @Override
    public void run() {
        Log.d(LOG_TAG, "got " + mDevice.getUuids().length + " uuids");
        for (ParcelUuid uuid : mDevice.getUuids()) {
            Log.d(LOG_TAG, "got uuid: " + uuid.toString());
            try {
                mSocket = mDevice.createRfcommSocketToServiceRecord(uuid.getUuid());
                mSocket.connect();
                Log.d(LOG_TAG, "connected");
                break;
            } catch (Exception ex1) {
                Log.e(LOG_TAG, ex1.getMessage());
                mSocket = null;
                try {
                    Log.d(LOG_TAG, "trying fallback");
                    mSocket = (BluetoothSocket) mDevice.getClass()
                            .getMethod("createRfcommSocket", new Class[]{int.class}).invoke(mDevice, 1);
                    mSocket.connect();
                    Log.d(LOG_TAG, "connected");
                    break;
                } catch (Exception ex2) {
                    Log.e(LOG_TAG, ex2.getMessage());
                    cancel();
                }
            }
        }
        if (mCallback != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (mSocket != null && mSocket.isConnected()) {
                        mCallback.onConnected();
                    } else {
                        mCallback.onFailure();
                    }
                }
            });
        }
    }

    public void cancel() {
        try {
            mSocket.close();
        } catch (IOException ignored) {
        }
    }

    public interface Callback {

        void onConnected();

        void onFailure();

    }

}