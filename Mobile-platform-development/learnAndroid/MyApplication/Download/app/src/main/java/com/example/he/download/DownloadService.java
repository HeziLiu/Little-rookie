package com.example.he.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;

/**
 * Created by he on 2017/1/13.
 */

public class DownloadService extends Service {
    private DownloadTask downloadTask;
    private String downloadUrl;


    private DownloadListener listener=new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getManager().notify(1,getNotification("Download...",progress));
        }

        @Override
        public void onSuccess() {
            downloadTask=null;
            stopForeground(true);
            getManager().notify(1,getNotification("Download Success",-1));
            Toast.makeText(DownloadService.this,"Success ",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downloadUrl=null;
            stopForeground(true);
            getManager().notify(1,getNotification("Download Failed",-1));
            Toast.makeText(DownloadService.this,"Failed ",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTask=null;
            Toast.makeText(DownloadService.this,"Paused ",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            stopForeground(true);
            getManager().notify(1,getNotification("Download Canceled",-1));
            Toast.makeText(DownloadService.this,"Canceled ",Toast.LENGTH_SHORT).show();
        }
    };

    private DownBinder mBinder=new DownBinder();

    class DownBinder extends Binder{

        public  void startDownload(String url){
            if(downloadTask==null){
                downloadUrl=url;
                downloadTask=new DownloadTask(listener);
                downloadTask.execute(downloadUrl);
                //让当前服务变为前提服务
                startForeground(1,getNotification("Downloading...",0));
                Toast.makeText(DownloadService.this,"Downloading....",Toast.LENGTH_SHORT).show();
            }
        }


        public void pauseDownload(){
            if(downloadTask==null){
                return;
            }
            downloadTask.pauseDownload();
        }

        public void canceledDownload(){
            if(downloadTask!=null){
                downloadTask.cancelDownload();
            }else {
                if(downloadUrl!=null){
                    String fileName=downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file=new File(directory+fileName);
                    if (file.exists()){
                        file.delete();
                    }
                    getManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this,"canceled..",Toast.LENGTH_SHORT).show();

                }

            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    private NotificationManager getManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title,int progress){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setContentTitle(title);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent  pi=PendingIntent.getActivity(this,0,intent,0);
        builder.setContentIntent(pi);
        if(progress>0){
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }




}
