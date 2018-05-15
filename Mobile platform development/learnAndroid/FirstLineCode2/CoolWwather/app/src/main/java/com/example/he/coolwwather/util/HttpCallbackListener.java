package com.example.he.coolwwather.util;

/**
 * 回调接口
 * Created by he on 2016/5/17.
 */
public interface HttpCallbackListener {

    void onFinish(String response);
    void onError(Exception e);
}
