package com.example.he.syncadapterdemo.SyncAdpaterModule;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 第二步：
 *  创建一个框架绑定授权器的服务
 *  这一服务提供一个 Android Binder 对象，
 *  允许框架调用我们的授权器，并且在授权器和框架间传递数据。
 * Created by he on 2017/5/4.
 */

public class AuthenticatorService extends Service {

    private Authenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator=new Authenticator(this);
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
