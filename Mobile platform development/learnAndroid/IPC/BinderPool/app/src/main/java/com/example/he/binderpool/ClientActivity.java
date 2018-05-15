package com.example.he.binderpool;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.he.binderpool.AIDL.BinderPool;
import com.example.he.binderpool.AIDL.ISecurityCenterImpl;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener {
    private ICompute compute;
    private ISecurityCenter center;
    private static final String TAG="Client";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1: {
                new Thread() {
                    @Override
                    public void run() {
                        BinderPool pool = BinderPool.getaInstance(ClientActivity.this);
                        IBinder binder = pool.queryBinder(BinderPool.BINDER_SECURITY_CENTER_CODE);
                        center = ISecurityCenterImpl.asInterface(binder);
                        String msg = "hello world";
                        Looper.prepare();
                        Toast.makeText(ClientActivity.this, "加密前 " + msg, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "加密前 "+msg);
                        try {
                            String password = center.encrypt(msg);
//                            Looper.prepare();
                            Toast.makeText(ClientActivity.this, "加密后 " + password, Toast.LENGTH_SHORT).show();
                            Looper.loop();
                            Log.d(TAG, "加密后 "+password);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
            break;
            case R.id.button2: {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BinderPool pool = BinderPool.getaInstance(ClientActivity.this);
                        IBinder binder=pool.queryBinder(BinderPool.BINDER_COMPUTE_CODE);
                        compute=ICompute.Stub.asInterface(binder);
                        try {
                            Looper.prepare();
                            Toast.makeText(ClientActivity.this, "3+5= " +compute.add(3,5), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                            Log.d(TAG, "3+5="+compute.add(3,5));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            break;
        }
    }
}
