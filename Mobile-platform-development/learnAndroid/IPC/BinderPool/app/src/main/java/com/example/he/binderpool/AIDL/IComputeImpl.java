package com.example.he.binderpool.AIDL;

import android.os.RemoteException;

import com.example.he.binderpool.ICompute;

/**
 * Created by he on 2016/12/29.
 */

public class IComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
