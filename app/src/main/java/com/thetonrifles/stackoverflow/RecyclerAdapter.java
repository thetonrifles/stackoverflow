package com.thetonrifles.stackoverflow;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BluetoothHolder> {

    private Context mContext;
    private Callback mCallback;
    private List<BluetoothDevice> mDevices;

    public RecyclerAdapter(Context context, List<BluetoothDevice> devices, Callback callback) {
        mContext = context;
        mDevices = devices;
        mCallback = callback;
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    @Override
    public BluetoothHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new BluetoothHolder(layout);
    }

    @Override
    public void onBindViewHolder(BluetoothHolder holder, int position) {
        BluetoothDevice device = mDevices.get(position);
        holder.txt_label.setText(device.getName());
        holder.txt_mac.setText(device.getAddress());
    }

    protected class BluetoothHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_label;
        TextView txt_mac;

        public BluetoothHolder(View itemView) {
            super(itemView);
            txt_label = (TextView) itemView.findViewById(android.R.id.text1);
            txt_mac = (TextView) itemView.findViewById(android.R.id.text2);
        }

        @Override
        public void onClick(View v) {
            if (mCallback != null) {
                BluetoothDevice device = mDevices.get(getAdapterPosition());
                mCallback.onDeviceClick(device);
            }
        }

    }

    public interface Callback {

        void onDeviceClick(BluetoothDevice device);

    }

}