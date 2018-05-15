package com.example.he.androidthreadtest;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by he on 2016/4/29.
 */
public class DownloadTask extends AsyncTask<Void, Integer, Boolean> {
    public int download() {
        return 0;
    }

    private ProgressDialog progressDialog = new ProgressDialog(null);

    @Override
    protected void onPreExecute() {
        progressDialog.show();//显示进度对话框
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            while (true) {
                int downloadPercent = download(); //虚构的方法
                publishProgress(downloadPercent);
                if (downloadPercent >= 100) {
                    break;
                }
            }
        }catch (Exception e){
            return  false;
        }
        return  true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        //更新下载进度   UI操作
        progressDialog.setMessage("Download"+values[0]+"%");
    }

    @Override
    protected void onPostExecute(Boolean result) {
        progressDialog.dismiss();//关闭对话框
        if (result){
            // download success
        }else {
            //download failed
        }
    }

}
