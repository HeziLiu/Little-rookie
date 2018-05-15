package com.example.he.download;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by he on 2017/1/13.
 */

public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    private static final int TYPE_SUCCESS = 0;
    private static final int TYPE_FAILED = 1;
    private static final int TYPE_PAUSED = 2;
    private static final int TYPE_CANCELED = 3;


    private DownloadListener listener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer) {
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            lastProgress = progress;
            listener.onProgress(progress);
        }

    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream input = null;
        File file = null;
        RandomAccessFile savedFile = null;
        try {
            long downloadLength = 0;
            String downloadUrl = params[0];
            long contentLength = getContentLength(downloadUrl);
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            //获取SD卡的down目录
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory + fileName);
            if (file.exists()) {
                downloadLength = file.length();
            }
            if (contentLength == 0)
                return TYPE_FAILED;
            else if (contentLength == downloadLength)
                return TYPE_SUCCESS;

            OkHttpClient client = new OkHttpClient();
            //断点下载，指定从哪个字节开始下载
            Request request = new Request.Builder().addHeader("RANGE", "bytes=" + downloadLength + "-").url(downloadUrl).build();//返回的不是完整的，而是已经跳过下载过的字节，即服务端返回的就是未下载的字节
            Response response = client.newCall(request).execute();
            if (response != null) {
                input = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downloadLength);//跳过已下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = input.read(b)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused)
                        return TYPE_PAUSED;
                    else {
                        total += len;
                    }
                    savedFile.write(b, 0, len);
                    int progress = (int) ((total + downloadLength) * 100 / contentLength);
                    publishProgress(progress);

                }
            }

            response.body().close();
            return TYPE_SUCCESS;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (input != null)
                    input.close();
                if (savedFile != null)
                    savedFile.close();
                if (isCanceled && file != null)
                    file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }


    public void  pauseDownload(){
        isPaused=true;
    }

    public void cancelDownload(){
        isCanceled=true;
    }


    private long getContentLength(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response result = client.newCall(request).execute();
        if (result != null && result.isSuccessful()) {
            return result.body().contentLength();
        }
        return 0;
    }


}
