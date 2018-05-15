package com.example.he.sockettest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TCPClientActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MESSAGE_RECRIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private static final String TAG = "Client:";

    private Button mButton;
    private TextView text;
    private EditText edit;
    private ProgressBar bar;

    private PrintWriter writer;
    private Socket mSocket;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MESSAGE_RECRIVE_NEW_MSG:
                    text.setText(text.getText() + (String) msg.obj + "\n");
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    Log.d(TAG, "handleMessage: ");
                    bar.setVisibility(View.GONE);
                    mButton.setEnabled(true);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.send);
        text = (TextView) findViewById(R.id.tv);
        edit = (EditText) findViewById(R.id.edit_text);
        bar= (ProgressBar) findViewById(R.id.bar);
        mButton.setOnClickListener(this);
        Intent service = new Intent(this, TCPServerService.class);
        startService(service);

        Log.d(TAG, "onCreate: ");
        
        new Thread() {
            @Override
            public void run() {
                try {
                    connectTCPServer();
                } catch (IOException e) {
                    SystemClock.sleep(1000);
                    System.out.println("正在连接....");
                }
            }
        }.start();

    }


    private void connectTCPServer() throws IOException {
        Socket socket = null;
        while (socket == null) {
            Log.d(TAG, "connectTCPServer: ");
            socket = new Socket("localhost", 8688);
            mSocket = socket;
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            handler.sendEmptyMessageDelayed(MESSAGE_SOCKET_CONNECTED,1000);
        }

        //从服务端接受信息
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //如果未停止就不断的遍历
        while (!TCPClientActivity.this.isFinishing()) {
            String msg = reader.readLine();
            if (msg != null) {
                String time = formatDateTime(System.currentTimeMillis());
                String newMessage = "server " + time + ": " + msg + "\n";
                handler.obtainMessage(MESSAGE_RECRIVE_NEW_MSG, newMessage).sendToTarget();
            }
        }

        writer.close();
        reader.close();
        socket.close();
    }

    private String formatDateTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));

    }

    @Override
    protected void onDestroy() {
        if (mSocket != null) {
            try {
                mSocket.shutdownInput();
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == mButton) {
            final String mMessage = edit.getText().toString();
            if(!TextUtils.isEmpty(mMessage) && writer != null) {
                new Thread() {
                    @Override
                    public void run() {
                        writer.println(mMessage);
                    }
                }.start();
                edit.setText("");
                String time = formatDateTime(System.currentTimeMillis());
                String newMessage = "client " + time + ": " + mMessage + "\n";
                text.setText(text.getText() + newMessage);
            }
        }
    }
}
