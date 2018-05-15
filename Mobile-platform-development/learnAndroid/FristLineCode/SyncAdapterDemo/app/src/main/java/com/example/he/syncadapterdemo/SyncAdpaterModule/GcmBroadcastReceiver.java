package com.example.he.syncadapterdemo.SyncAdpaterModule;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 接收服务器发送的指令，当接收到指令后启动SyncAdapter
 * Created by he on 2017/5/4.
 */

public class GcmBroadcastReceiver extends BroadcastReceiver {

    // Constants
    // Content provider authority
    public static final String AUTHORITY = "com.example.he.syncadapterdemo.provider";
    // Account type
    public static final String ACCOUNT_TYPE = "example.com";
    // Account
    public static final String ACCOUNT = "default_account";
    // Incoming Intent key for extended data
    public static final String KEY_SYNC_REQUEST = "com.example.android.datasync.KEY_SYNC_REQUEST";

    @Override
    public void onReceive(Context context, Intent intent) {

        Account account=new Account(ACCOUNT,ACCOUNT_TYPE);

        // Get a GCM object instance
//        GoogleCloudMessaging gcm =
//                GoogleCloudMessaging.getInstance(context);
//        // Get the type of GCM message
//        String messageType = gcm.getMessageType(intent);
        /*
         * Test the message type and examine the message contents.
         * Since GCM is a general-purpose messaging system, you
         * may receive normal messages that don't require a sync
         * adapter run.
         * The following code tests for a a boolean flag indicating
         * that the message is requesting a transfer from the device.
         */
//        if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)
//                &&
//                intent.getBooleanExtra(KEY_SYNC_REQUEST,false)) {
//            /*
//             * Signal the framework to run your sync adapter. Assume that
//             * app initialization has already created the account.
//             */
//            ContentResolver.requestSync(account, AUTHORITY, null);
//
//        }

    }

}
