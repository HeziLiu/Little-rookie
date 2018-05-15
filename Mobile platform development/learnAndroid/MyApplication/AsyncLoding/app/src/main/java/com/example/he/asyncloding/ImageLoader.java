package com.example.he.asyncloding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * 根据URL加载网络图片
 * Created by he on 2016/11/22.
 */

public class ImageLoader {

    //创建缓存用于缓存ListView中的图片
    private static LruCache<String, Bitmap> imageCache;
    private ListView mListView;
    private Set<imageAsyncTask> mTask;

    public ImageLoader(ListView listView) {
        // 获取运行时的最大内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cache = maxMemory / 4;//设置缓冲大小
        mListView = listView;
        mTask = new HashSet<imageAsyncTask>();
        imageCache = myLruCache.getInstance(cache);
    }


    /**
     * 通过AsyncTask的方式加载网络图片
     *
     * @param image
     * @param url
     */
    public void loadingImageByAsyncTask(ImageView image, final String url) {
        Bitmap bitmap = imageCache.get(url);
        //如果内存中没有加载图片则使用默认图片
        if (bitmap == null) {
            image.setImageResource(R.mipmap.ic_launcher);
        } else {
            image.setImageBitmap(bitmap);
        }

    }


    /**
     * 加载指定位置的图片
     *
     * @param start
     * @param end
     */
    public void loadImage(int start, int end) {

        for (int i = start; i < end; i++) {
            ItemBean bean= (ItemBean) mListView.getItemAtPosition(i);
            String url=bean.getImageUrl();//通过itemBean获取URL,为下拉刷新服务
//            String url = myAdapter.imagePath[i];
            Bitmap bitmap = imageCache.get(url);
            if (bitmap != null) {
                //找到ImageView
                ImageView image = (ImageView) mListView.findViewWithTag(url);
                image.setImageBitmap(imageCache.get(url));
            } else {
                //如果缓存中没有则重新下载
                imageAsyncTask task = new imageAsyncTask(url);
                task.execute(url);
                mTask.add(task);
            }

        }
    }

    /**
     * 取消所有正在运行的任务
     */
    public void cancelAllTask() {
        if (mTask != null)
            for (imageAsyncTask task : mTask)
                task.cancel(false);
    }


    class imageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private String urlTag;
        private Bitmap bitmap;

        public imageAsyncTask(String urlTag) {
            this.urlTag = urlTag;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            try {
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null)
                    imageCache.put(url, bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bm) {
            ImageView imageView = (ImageView) mListView.findViewWithTag(urlTag);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
//
//            if (imageView.getTag().equals(urlTag)) {
//                if (imageCache.get(urlTag) != null) {
//                    imageView.setImageBitmap(imageCache.get(urlTag));
//                } else{
//                    imageView.setImageBitmap(bitmap);
//                }
//            }
            super.onPostExecute(bm);
        }
    }


}
