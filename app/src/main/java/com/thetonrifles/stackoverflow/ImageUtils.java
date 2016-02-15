package com.thetonrifles.stackoverflow;

import android.text.TextUtils;

import com.android.volley.toolbox.NetworkImageView;

public class ImageUtils {

    public static void setPic(NetworkImageView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            view.setImageUrl(url, VolleyHandler.getInstance(view.getContext()).getImageLoader());
        }
    }

}
