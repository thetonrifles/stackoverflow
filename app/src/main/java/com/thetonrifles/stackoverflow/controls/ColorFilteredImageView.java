package com.thetonrifles.stackoverflow.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.thetonrifles.stackoverflow.R;

public class ColorFilteredImageView extends ImageView {

	private int mFilterColor;

	public ColorFilteredImageView(Context context) {
		super(context);
	}

	public ColorFilteredImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttrs(attrs);
	}

	public ColorFilteredImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initAttrs(attrs);
	}

	private void initAttrs(AttributeSet attrs) {
		if (attrs != null) {
			TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
					R.styleable.ColorFilteredImageView);
			mFilterColor = typedArray.getColor(R.styleable.ColorFilteredImageView_filter_color, 0);
			typedArray.recycle();
		}
		buildAndApplyFilter();
	}

	private void buildAndApplyFilter() {
		if (mFilterColor != 0) {
			ColorFilter filter = new PorterDuffColorFilter(mFilterColor, PorterDuff.Mode.MULTIPLY);
			setColorFilter(filter);
		}
	}

	public void setFilterColor(int color) {
		mFilterColor = color;
		buildAndApplyFilter();
	}

	public void setFilterColor(String color) {
		mFilterColor = Color.parseColor(color);
		buildAndApplyFilter();
	}

}