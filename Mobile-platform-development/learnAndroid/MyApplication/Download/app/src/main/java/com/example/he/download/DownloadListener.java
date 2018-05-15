package com.example.he.download;

/**
 * Created by he on 2017/1/13.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
