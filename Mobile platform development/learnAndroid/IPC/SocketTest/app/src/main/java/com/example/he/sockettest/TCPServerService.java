package com.example.he.sockettest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by he on 2016/12/29.
 */

public class TCPServerService extends Service {
    private boolean mIsServiceDestoryed = false;
    private String defineMessages[] = new String[]{"hi", "我选择狗带", "你说什么", "你猜"};

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!mIsServiceDestoryed) {
                //接受客服端请求,为每个请求创建一个套接字
                try {
                    final Socket client = serverSocket.accept();
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private void responseClient(Socket client) throws IOException {
        //接受客户端发来的消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //向客户端发送消息
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        PrintWriter writer = new PrintWriter(out, true);
        writer.println("欢迎来到聊天室");
        while (!mIsServiceDestoryed) {
            String str = in.readLine();
            System.out.println(str);
            if (str == null) {
                //客户端断开了连接
                break;
            }
            int i = new Random().nextInt(defineMessages.length);
            writer.println(defineMessages[i]);
        }

        writer.close();
        in.close();
        client.close();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TcpServer()).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestoryed = true;
    }
}
