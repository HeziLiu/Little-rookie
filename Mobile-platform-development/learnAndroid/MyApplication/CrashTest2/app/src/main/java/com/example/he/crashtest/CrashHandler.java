package com.example.he.crashtest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by he on 2017/1/10.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private Context mContext;
    //异常信息在本地磁盘中保存的路径
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/CrashTest/log";
    private static final String FILE_NAME = "crash";//文件名
    //    private static final String FILE_NAME_SUFFIX=".trace";//保存文件的后缀
    private static final String FILE_NAME_SUFFIX = ".txt";//保存文件的后缀
    private static final boolean DEBUG=true;

    private static CrashHandler crashHandler = new CrashHandler();

    private Thread.UncaughtExceptionHandler mDefaultCrashHadler;

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return crashHandler;
    }

    /**
     * 为将线程的一次捕获器指定为当前捕获器
     */
    public void init(Context context){
        mDefaultCrashHadler=Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext=context;
    }





    /**
     * 该方法捕获逃逸的异常
     *
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            //将异常写入SD卡
            dumpExceptionToSDCard(e);
            //将异常日志上传到服务器
            uploadExceptionToServer();
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }

        e.printStackTrace();

        //如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就自己结束自己
        if(mDefaultCrashHadler!=null){
            mDefaultCrashHadler.uncaughtException(t,e);
        }else{
            Process.killProcess(Process.myPid());
        }



    }


    /**
     * 将异常信息保存到SD卡中
     * @param ex
     */
    private void dumpExceptionToSDCard(Throwable ex) throws PackageManager.NameNotFoundException {
        //异常无法写入本地磁盘
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            if(DEBUG){
                Log.d(TAG, "dumpExceptionToSDCard: 异常信息无法写入磁盘");
                return;
            }
        }

        //创建被写入的文件
        File dir=new File(PATH);
        if(!dir.exists())
            dir.mkdirs();
        String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        File file=new File(PATH+FILE_NAME+time+FILE_NAME_SUFFIX);

        //将信息写入到文件
        try {
            PrintWriter writer=new PrintWriter(new BufferedWriter(new FileWriter(file)));
            writer.println(time);
            dumpPhoneInfo(writer);
            writer.println();
            ex.printStackTrace(writer);
            writer.close();
        } catch (IOException e) {
            Log.d(TAG, "dumpExceptionToSDCard: 异常写入失败");
            e.printStackTrace();
        }


    }

    /**
     * 获取手机的详细信息
     * @param writer
     */
    private void dumpPhoneInfo(PrintWriter writer) throws PackageManager.NameNotFoundException {
        PackageManager manager=mContext.getPackageManager();
        PackageInfo phoneInfo=manager.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);
        writer.print("App Version:");
        writer.print(phoneInfo.versionName);
        writer.print("--");
        writer.print(phoneInfo.versionCode);

        //Android版本号
        writer.print("OS Version: ");
        writer.print(Build.VERSION.RELEASE);
        writer.print("--");
        writer.println(Build.VERSION.SDK_INT);

        //手机制造商
        writer.print("Vendor: ");
        writer.println(Build.MANUFACTURER);

        //手机型号
        writer.print("Model: ");
        writer.println(Build.MODEL);

        //CPU架构
        writer.print("CPU ABI:");
        writer.println(Build.CPU_ABI);

    }


    /**
     * 将异常日志传到服务器
     */

    private void uploadExceptionToServer(){
        //TODO:
    }


}
