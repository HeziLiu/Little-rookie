package com.example.he.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by he on 2016/4/9.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "receiver myBroadcastReceiver", Toast.LENGTH_SHORT).show();
        abortBroadcast();//中断广播
    }
}
