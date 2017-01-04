package com.thetonrifles.stackoverflow.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thetonrifles.stackoverflow.R;
import com.thetonrifles.stackoverflow.util.DateUtils;

import java.util.Collections;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static class HeaderViewHolder extends RecyclerView.ViewHolder {

		TextView txt_header;

		HeaderViewHolder(View itemView) {
			super(itemView);
			txt_header = (TextView) itemView.findViewById(R.id.txt_header);
		}

	}

	private static class EventViewHolder extends RecyclerView.ViewHolder {

		TextView txt_title;

		EventViewHolder(View itemView) {
			super(itemView);
			txt_title = (TextView) itemView.findViewById(R.id.txt_title);
		}

	}

	@NonNull
	private List<ListItem> items = Collections.emptyList();

	public EventsAdapter(@NonNull List<ListItem> items) {
		this.items = items;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		switch (viewType) {
			case ListItem.TYPE_HEADER: {
				View itemView = inflater.inflate(R.layout.view_list_item_header, parent, false);
				return new HeaderViewHolder(itemView);
			}
			case ListItem.TYPE_EVENT: {
				View itemView = inflater.inflate(R.layout.view_list_item_event, parent, false);
				return new EventViewHolder(itemView);
			}
			default:
				throw new IllegalStateException("unsupported item type");
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		int viewType = getItemViewType(position);
		switch (viewType) {
			case ListItem.TYPE_HEADER: {
				HeaderItem header = (HeaderItem) items.get(position);
				HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
				// your logic here
				holder.txt_header.setText(DateUtils.formatDate(header.getDate()));
				break;
			}
			case ListItem.TYPE_EVENT: {
				EventItem event = (EventItem) items.get(position);
				EventViewHolder holder = (EventViewHolder) viewHolder;
				// your logic here
				holder.txt_title.setText(event.getEvent().getTitle());
				break;
			}
			default:
				throw new IllegalStateException("unsupported item type");
		}
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	@Override
	public int getItemViewType(int position) {
		return items.get(position).getType();
	}

}
