package com.example.he.syncadapterdemo.SyncAdpaterModule;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * 第四步：
 *  创建SyncAdapter
 *  该类用于封装数据传输任务
 *
 * Created by he on 2017/5/4.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private ContentResolver mContentResolver;;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver=context.getContentResolver();
    }

    /**
     * 在该方法中添加数据传输代码
     * @param account
     *              如果服务端不需要账户信息则忽略
     * @param extras
     *              它包含了一些标识，这些标识由触发 Sync Adapter 的事件所发送。
     * @param authority
     *              系统中某个 Content Provider 的 Authority。我们的应用必须要有访问它的权限。
     *              通常，该 Authority 对应于应用的 Content Provider。
     * @param provider
     *              ContentProviderClient 针对于由 Authority 参数所指向的Content Provider
     *              ContentProviderClient 是一个 Content Provider 的轻量级共有接口
     *              如果我们正在使用 Content Provider 来存储应用数据，那么我们可以利用它连接 Content Provider。
     *              反之，则将其忽略。
     * @param syncResult
     *              我们可以使用它将信息发送给 Sync Adapter 框架。
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
            //TODO
            //1、连接到一个服务器
            //2、下载和上传数据，如果我们想要从服务器下载数据并将它存储到 Content Provider 中，我们必须提供请求数据，
        //                     下载数据和将数据插入到 Provider 中的代码。
            //3、处理数据冲突或者确定当前数据的状态
            //4、清理，关闭网络连接，清除临时文件和缓存。
    }
}
