package com.thetonrifles.stackoverflow.controls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.thetonrifles.stackoverflow.R;

public class PagerIndicatorView extends FrameLayout {

    private LinearLayout mLayout;

    public PagerIndicatorView(Context context) {
        super(context);
        init();
    }

    public PagerIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerIndicatorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @SuppressLint("InflateParams")
    private void init() {
        View layout = inflate(getContext(), R.layout.view_pager_indicator, null);
        mLayout = (LinearLayout) layout.findViewById(R.id.pager_layout);
        addView(layout);
    }

    public void setPagesNumber(int count, int selected) {
        mLayout.removeAllViews();
        for (int i = 0; i < count; i++) {
            View dot = buildDotIndicator(i == selected);
            mLayout.addView(dot);
        }
    }

    /**
     * Support method for building dot indicator for pages
     */
    private View buildDotIndicator(boolean selected) {
        ColorFilteredImageView dot = new ColorFilteredImageView(getContext());
        if (selected) {
            dot.setImageResource(R.drawable.drawable_circle_white);
            dot.setFilterColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        } else {
            dot.setImageResource(R.drawable.drawable_circle_white);
            dot.setFilterColor(ContextCompat.getColor(getContext(), R.color.colorAccentLight));
        }
        int size = getContext().getResources().getDimensionPixelSize(R.dimen.dot_size);
        int space = dp2px(getContext(), 4);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.setMargins(space, 0, space, 0);
        dot.setLayoutParams(params);
        return dot;
    }

    public int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }

}