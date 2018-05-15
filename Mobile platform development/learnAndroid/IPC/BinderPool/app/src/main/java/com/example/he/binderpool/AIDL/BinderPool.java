package com.example.he.binderpool.AIDL;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.he.binderpool.IBinderPool;
import com.example.he.binderpool.ServerService;

import java.util.concurrent.CountDownLatch;

/**
 * Created by he on 2016/12/29.
 */

public class BinderPool {

    public static final int BINDER_COMPUTE_CODE = 1;
    public static final int BINDER_SECURITY_CENTER_CODE = 2;

    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPool aInstance;
    private CountDownLatch downLatch;


    private BinderPool(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPool getaInstance(Context context) {
        synchronized (BinderPool.class) {
            if (aInstance == null) {
                aInstance = new BinderPool(context);
            }
        }
        return aInstance;
    }

    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        if (mBinderPool != null) {
            try {
                binder = mBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }


    //连接服务
    private synchronized void connectBinderPoolService() {
        downLatch = new CountDownLatch(1);
        Intent intent = new Intent(mContext, ServerService.class);
        mContext.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            downLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    public static class IBinderPoolImpl extends IBinderPool.Stub {

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            Binder binder = null;
            switch (binderCode) {
                case BINDER_COMPUTE_CODE:
                    binder = new IComputeImpl();
                    break;
                case BINDER_SECURITY_CENTER_CODE:
                    binder = new ISecurityCenterImpl();
                    break;
            }
            return binder;
        }
    }

}
