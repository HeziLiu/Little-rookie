package com.example.he.webviewtest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webV);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.baidu.com");
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//使用缓存
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //表示网页加载完成
                if (newProgress == 100) {
                    closeDialog();
                } else {
                    openDialog(newProgress);
                }
            }
        });

    }

    private void closeDialog() {
        if(dialog!=null && dialog.isShowing()){
            dialog.dismiss();
            dialog=null;
        }
    }

    private void openDialog(int newProgress) {
        if(dialog==null){
            dialog=new ProgressDialog(MainActivity.this);
            dialog.setTitle("正在加载中。。。");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setProgress(newProgress);
            dialog.show();
        }else{
            dialog.setProgress(newProgress);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (webView.canGoBack()) {
                webView.goBack();
                return  true;
            } else {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
