package com.thetonrifles.stackoverflow;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.Callback {

    private static final int BT_ENABLE_REQUEST_CODE = 1000;

    private RecyclerAdapter mAdapter;
    private BluetoothAdapter mBtAdapter;
    private List<BluetoothDevice> mDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        mDevices = new ArrayList<>();
        mAdapter = new RecyclerAdapter(this, mDevices, this);

        RecyclerView view = (RecyclerView) findViewById(R.id.lst_devices);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(mAdapter);

        // bluetooth available?
        if (mBtAdapter == null) {
            Toast.makeText(this, R.string.toast_bt_unavailable, Toast.LENGTH_SHORT).show();
            return;
        }

        // bluetooth enabled?
        if (!mBtAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, BT_ENABLE_REQUEST_CODE);
            return;
        }

        updateDevices();
    }

    private void updateDevices() {
        mDevices.clear();
        mDevices.addAll(mBtAdapter.getBondedDevices());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeviceClick(BluetoothDevice device) {
        (new ConnectThread(device)).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BT_ENABLE_REQUEST_CODE  && resultCode == RESULT_OK) {
            updateDevices();
        }
    }

}