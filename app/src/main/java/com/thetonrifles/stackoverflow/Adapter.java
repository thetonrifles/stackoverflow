package com.thetonrifles.stackoverflow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ListViewHolder> {

    protected static class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView img_photo;
        TextView txt_label;

        public ListViewHolder(View itemView) {
            super(itemView);
            txt_label = (TextView) itemView.findViewById(R.id.txt_label);
            img_photo = (ImageView) itemView.findViewById(R.id.img_photo);
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
