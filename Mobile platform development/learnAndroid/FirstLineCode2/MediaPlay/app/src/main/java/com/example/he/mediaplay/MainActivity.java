package com.example.he.mediaplay;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.VideoView;

import java.io.File;

/**
 * 播放本地视频和音乐
 */


public class MainActivity extends Activity implements View.OnClickListener {

    private MediaPlayer player = new MediaPlayer();
    private VideoView view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //播放音乐按钮
        findViewById(R.id.audio_play).setOnClickListener(this);
        findViewById(R.id.audio_pause).setOnClickListener(this);
        findViewById(R.id.audio_stop).setOnClickListener(this);


        //播放视频按钮
        view= (VideoView) findViewById(R.id.video_view);
        findViewById(R.id.video_play).setOnClickListener(this);
        findViewById(R.id.video_pause).setOnClickListener(this);
        findViewById(R.id.video_stop).setOnClickListener(this);


        initMediaPlayer();//初始化MediaPlayer
        initVideoPath();//设置video路径

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.audio_play:
                if (!player.isPlaying()) {
                    player.start();//开始
                }
                break;
            case R.id.audio_pause:
                if (player.isPlaying()) {
                    player.pause();
                }
                break;
            case R.id.audio_stop:
                if (player.isPlaying()) {
                    player.reset();//停止播放
                    initMediaPlayer();
                }
                break;
            case R.id.video_play:
                if (!view.isPlaying()){
                    view.start();
                }
                break;
            case R.id.video_pause:
                if (view.isPlaying()){
                    view.pause();
                }
                break;
            case R.id.video_stop:
                if (view.isPlaying()){
                    view.resume();//重新播放
                }
            break;

        }
    }

    //初始化MediaPlayer
    private void initMediaPlayer() {
        try {
            //根目录
            File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
            player.setDataSource(file.getPath());//指定音频路径
            player.prepare();//进入准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //初始化Video路径
    private  void initVideoPath(){
        File file=new File(Environment.getExternalStorageDirectory(),"video.MP4");
        view.setVideoPath(file.getPath());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player!=null){
            player.stop();
            player.release();
        }
        if (view!=null){
            view.suspend();//释放资源
        }
    }
}
