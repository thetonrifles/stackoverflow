package com.thetonrifles.stackoverflow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ListViewHolder> {

    protected class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img_photo;
        TextView txt_label;
        Button btn_pick;

        public ListViewHolder(View itemView) {
            super(itemView);
            txt_label = (TextView) itemView.findViewById(R.id.txt_label);
            img_photo = (ImageView) itemView.findViewById(R.id.img_photo);
            btn_pick = (Button) itemView.findViewById(R.id.btn_pick);
            btn_pick.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ListItem item = mItems.get(getAdapterPosition());
            // do work here
            Toast.makeText(mContext, item.getLabel(), Toast.LENGTH_SHORT).show();
        }

    }

    private Context mContext;

    private List<ListItem> mItems;

    public Adapter(Context context, List<ListItem> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.view_item, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, int position) {
        ListItem item = mItems.get(position);
        holder.txt_label.setText(item.getLabel());
        ImageUtils.setPic(holder.img_photo, item.getImgUrl());
    }

}
