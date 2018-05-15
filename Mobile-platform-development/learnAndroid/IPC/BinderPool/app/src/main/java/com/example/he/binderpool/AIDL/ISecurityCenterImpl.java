package com.example.he.binderpool.AIDL;

import android.os.RemoteException;

import com.example.he.binderpool.ISecurityCenter;

/**
 * Created by he on 2016/12/29.
 */

public class ISecurityCenterImpl extends ISecurityCenter.Stub {
    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
