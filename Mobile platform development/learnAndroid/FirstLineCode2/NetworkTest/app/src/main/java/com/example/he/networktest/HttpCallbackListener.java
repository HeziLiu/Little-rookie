package com.example.he.networktest;

/**
 * Created by he on 2016/5/4.
 */
public interface HttpCallbackListener  {
    void onFinish(String response);
    void onError(Exception e);
}
