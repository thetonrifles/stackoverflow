package com.thetonrifles.stackoverflow;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleyHandler {

    private static VolleyHandler mInstance;
    private static Context mContext;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleyHandler(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
    }

    public static synchronized VolleyHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyHandler(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    private class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            mCache = new LruCache<>(20);
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            // scaling bitmap for avoiding too much big images
            // to be loaded
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
            mCache.put(url, scaled);
        }
    }

}
