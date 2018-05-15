package com.example.he.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.he.binderpool.AIDL.BinderPool;

/**
 * Created by he on 2016/12/29.
 */

public class ServerService extends Service {
    private Binder mBinder=new BinderPool.IBinderPoolImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
