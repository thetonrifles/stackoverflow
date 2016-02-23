package com.thetonrifles.stackoverflow.volley;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                if (url.startsWith("content://")) {
                    // content uri
                    try {
                        Uri uri = Uri.parse(url);
                        Bitmap bitmap = mMemoryCache.get(url);
                        if (bitmap == null) {
                            InputStream is = context.getContentResolver().openInputStream(uri);
                            bitmap = BitmapFactory.decodeStream(is);
                            Bitmap scaled = ImageUtils.getInstance().scaleBitmap(bitmap);
                            mMemoryCache.put(url, scaled);
                            if (is != null) {
                                is.close();
                            }
                        }
                        view.setLocalImageBitmap(bitmap);
                    } catch (Exception ex) {
                        Log.e("Image", ex.getMessage(), ex);
                    }
                } else {
                    // absolute path
                    Bitmap bitmap = BitmapFactory.decodeFile(url);
                    mMemoryCache.put(url, bitmap);
                    view.setLocalImageBitmap(bitmap);
                }
            }
        } else {
            view.setImageUrl("", VolleyHandler.getInstance(view.getContext()).getImageLoader());
        }
    }

    public Intent buildPicturePickerIntent(PackageManager packageManager) {
        // Camera
        final List<Intent> cameraIntents = new ArrayList<>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            cameraIntents.add(intent);
        }
        // Filesystem
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        // Chooser of filesystem options
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
        // Add the camera options
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        // returning intent
        return chooserIntent;
    }

    public Bitmap scaleBitmap(Bitmap bitmap) {
        int width = WIDTH;
        int height = (WIDTH * bitmap.getHeight()) / bitmap.getWidth();
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    public synchronized String savePhoto(Bitmap image) {
        // getting external storage folder
        String folder = Environment.getExternalStorageDirectory().toString();
        // generating random unique filename
        String filename = UUID.randomUUID().toString() + ".png";
        // path
        String path;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, bytes);
            File f = new File(folder, filename);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            path = f.getAbsolutePath();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException ex) {
            Log.e("Image", ex.getMessage(), ex);
            path = null;
        }
        return path;
    }

}