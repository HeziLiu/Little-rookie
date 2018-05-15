package com.example.he.broadcastbestpractice;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

/**
 * Created by he on 2016/4/10.
 */
public class ForceOfflineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("即将被强制下线，请重新登录");
        dialog.setCancelable(false);//不能被取消
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();//销毁所有活动
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//由于在接收器中启动活动一定要加入标志
                context.startActivity(intent);

            }
        });
        AlertDialog dialog1 = dialog.create();
        //设置AlterDialog类型，保证在广播接收器中能正常弹出
        dialog1.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog1.show();
    }
}
