package com.thetonrifles.stackoverflow.adapter;

public abstract class ListItem {

    public static final int TYPE_HEADER = 0;
	public static final int TYPE_EVENT = 1;

    abstract public int getType();

} 