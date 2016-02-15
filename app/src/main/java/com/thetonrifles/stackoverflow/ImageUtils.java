package com.thetonrifles.stackoverflow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.InputStream;

public class ImageUtils {

    public static void setPic(MyImageView view, String url) {
        Context context = view.getContext();
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("http")) {
                view.setImageUrl(url, VolleyHandler.getInstance(context).getImageLoader());
            } else {
                try {
                    Uri uri = Uri.parse(url);
                    InputStream is = context.getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    view.setLocalImageBitmap(bitmap);
                    if (is!=null) {
                        is.close();
                    }
                } catch (Exception ex) {
                    Log.e("Image", ex.getMessage(), ex);
                }
            }
        } else {
            view.setImageUrl("", VolleyHandler.getInstance(view.getContext()).getImageLoader());
        }
    }

    public static void launchPicturePicker(Activity activity, int code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), code);
    }

}
