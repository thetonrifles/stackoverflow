package com.thetonrifles.stackoverflow;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageUtils {

    public static void setPic(ImageView view, String path) {

    }

    public static void setPic(ImageView view, Bitmap bmp) {
        view.setImageBitmap(bmp);
    }

}
