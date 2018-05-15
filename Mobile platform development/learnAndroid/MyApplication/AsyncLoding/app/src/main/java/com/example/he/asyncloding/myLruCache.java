package com.example.he.asyncloding;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by he on 2016/11/22.
 */

public class myLruCache extends LruCache<String, Bitmap> {
    private static myLruCache instance;

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    private myLruCache(int maxSize) {
        super(maxSize);
    }

    public static myLruCache getInstance(int maxSize) {
        if (instance == null)
            instance = new myLruCache(maxSize);
        return instance;
    }


    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount();
    }
}
