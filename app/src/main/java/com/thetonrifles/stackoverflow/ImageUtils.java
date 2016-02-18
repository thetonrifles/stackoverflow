package com.thetonrifles.stackoverflow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;

import java.io.InputStream;

public class ImageUtils {

    private static final int CACHE_SIZE = 10 * 1024 * 1024; // 10 MB

    private static final int WIDTH = 100;

    private static ImageUtils instance;

    public static synchronized ImageUtils getInstance() {
        if (instance == null) {
            instance = new ImageUtils();
        }
        return instance;
    }

    private ImageUtils() {
        mMemoryCache = new LruCache<String, Bitmap>(CACHE_SIZE) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int bytes = BitmapCompat.getAllocationByteCount(value);
                return bytes / 1000;
            }
        };
    }

    private LruCache<String, Bitmap> mMemoryCache;

    public void setPic(MyImageView view, String url) {
        Context context = view.getContext();
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("http")) {
                view.setImageUrl(url, VolleyHandler.getInstance(context).getImageLoader());
            } else {
                try {
                    Uri uri = Uri.parse(url);
                    Bitmap bitmap = mMemoryCache.get(url);
                    if (bitmap == null) {
                        InputStream is = context.getContentResolver().openInputStream(uri);
                        bitmap = BitmapFactory.decodeStream(is);
                        Bitmap scaled = ImageUtils.getInstance().scaleBitmap(bitmap);
                        mMemoryCache.put(url, scaled);
                        if (is!=null) {
                            is.close();
                        }
                    }
                    view.setLocalImageBitmap(bitmap);
                } catch (Exception ex) {
                    Log.e("Image", ex.getMessage(), ex);
                }
            }
        } else {
            view.setImageUrl("", VolleyHandler.getInstance(view.getContext()).getImageLoader());
        }
    }

    public void launchPicturePicker(Activity activity, int code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), code);
    }

    public Bitmap scaleBitmap(Bitmap bitmap) {
        int width = WIDTH;
        int height = (WIDTH * bitmap.getHeight()) / bitmap.getWidth();
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

}
