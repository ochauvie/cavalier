package com.cavalier.service;

import android.graphics.Bitmap;
import android.util.LruCache;


public class BitmapService {

    // Get max available VM memory, exceeding this amount will throw an
    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
    // int in its constructor.
    final static int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    // Use 1/8th of the available memory for this memory cache.
    final static int cacheSize = maxMemory / 3;


    private static LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(cacheSize) {

        @Override
        protected int sizeOf(String key, Bitmap draw) {
            // The cache size will be measured in kilobytes rather than
            // number of items.
            return draw.getByteCount() / 1024;
        }

    };


    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mImageCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromMemCache(String key) {
        return mImageCache.get(key);
    }


}