package com.example.he.asynctasktest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by he on 2016/11/20.
 */

public class SecondActivity extends AppCompatActivity {
    private ImageView image;
    private ProgressBar progressBar;
    private TextView textView;
    private static final String url = "http://img.my.csdn.net/uploads/201504/12/1428806103_9476.png";
    private myAsyncTask task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        image = (ImageView) findViewById(R.id.image_);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        textView = (TextView) findViewById(R.id.tv);
        task = new myAsyncTask();
        task.execute(url);
    }

    class myAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            String url = params[0];
            Bitmap bitmap = null;

            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                inputStream = connection.getInputStream();
                //模拟加载图片时消耗的时间
                for (int i = 0; i < 100; i++) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    publishProgress(i);//发送进度
                }
                BufferedInputStream i = new BufferedInputStream(inputStream);
                bitmap = BitmapFactory.decodeStream(i);
                inputStream.close();
                i.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            super.onPostExecute(bitmap);
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            textView.setText("已加载 " + values[0]+"%");
            super.onProgressUpdate(values);
        }
    }

    /**
     * 用于取消AsyncTask，因为AsyncTask的实现时基于线程池的，如果在加载的时候返回上一个Activity再次打
     * 开当前Activity时必须要等待上一次加载完才能再次加载
     */
    @Override
    protected void onPause() {
        super.onPause();
        if(task!=null && task.getStatus()== AsyncTask.Status.RUNNING)
            task.cancel(true);//如果正在运行则取消
    }
}
