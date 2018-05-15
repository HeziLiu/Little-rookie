package com.example.he.volleydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    RequestQueue mRequestQueue;
    private String stringRequestTag="A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text);

        //仅仅是想做一个单次的请求并且不想要线程池一直保留,使用该方法
       // mRequestQueue= Volley.newRequestQueue(this);  //方法内部调用了start方法

        Cache cache=new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network=new BasicNetwork(new HurlStack());
        mRequestQueue=new RequestQueue(cache,network);
        mRequestQueue.start();


        String url="http://www.baidu.com";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Display the first 500 characters of the response string.
                mTextView.setText("Response is: "+ response.substring(0,500));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("  ");
            }
        });
        request.setTag(stringRequestTag);

        mRequestQueue.add(request);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue!=null){
            mRequestQueue.cancelAll(stringRequestTag);
        }
    }
}
