package com.thetonrifles.stackoverflow.adapter;

import android.support.annotation.NonNull;

import com.thetonrifles.stackoverflow.model.Event;

public class EventItem extends ListItem {

	@NonNull
	private Event event;

	public EventItem(@NonNull Event event) {
		this.event = event;
	}

	@NonNull
	public Event getEvent() {
		return event;
	}

	// here getters and setters
	// for title and so on, built
	// using event

	@Override
	public int getType() {
		return TYPE_EVENT;
	}

}