package com.thetonrifles.stackoverflow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ListViewHolder> {

    protected class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MyImageView img_photo;
        TextView txt_label;
        Button btn_pick;

        public ListViewHolder(View itemView) {
            super(itemView);
            txt_label = (TextView) itemView.findViewById(R.id.txt_label);
            img_photo = (MyImageView) itemView.findViewById(R.id.img_photo);
            btn_pick = (Button) itemView.findViewById(R.id.btn_pick);
            btn_pick.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ListItem item = mItems.get(getAdapterPosition());
            if (mCallback != null) {
                mCallback.onButtonClick(getAdapterPosition(), item);
            }
        }

    }

    private Context mContext;
    private List<ListItem> mItems;
    private Callback mCallback;

    public Adapter(Context context, List<ListItem> items, Callback callback) {
        mContext = context;
        mItems = items;
        mCallback = callback;
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
        ImageUtils.getInstance().setPic(holder.img_photo, item.getImgUrl());
    }

    public interface Callback {

        void onButtonClick(int position, ListItem item);

    }

}
