package com.thetonrifles.stackoverflow.model;

import android.support.annotation.NonNull;

import java.util.Date;

public class Event {

	private String title;
	private Date date;

	public Event(@NonNull String title, @NonNull Date date) {
		this.title = title;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public Date getDate() {
		return date;
	}

}
