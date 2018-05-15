package com.example.he.aidltest;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * 客户端，与服务端绑定，将服务端返回的Binder对象转换为AIDL接口
 */
public class BookManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Book> list;
    private IBookManager manager;
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;
    private IBinder mBinder;//用于重连服务

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED: {
                    //收到新书到的时候给用户发送一条通知
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    String book = msg.obj.toString();
                    Notification notification = new NotificationCompat.Builder(BookManagerActivity.this).setTicker("您有新的消息").setDefaults(NotificationCompat.DEFAULT_ALL).setContentTitle("服务端添加了一本新书").setContentText(book).setSmallIcon(R.mipmap.ic_launcher).build();
                    manager.notify(1, notification);
                }
                break;
            }
            super.handleMessage(msg);
        }
    };


    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (manager == null)
                return;
            manager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            manager = null;
            // TODO:这里重新绑定远程Service
           manager=IBookManager.Stub.asInterface(mBinder);

        }
    };


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //服务端返回的Binder对象转换为AIDL接口
            manager = IBookManager.Stub.asInterface(service);


            try {
                //给binder设置死亡代理
                manager.asBinder().linkToDeath(mDeathRecipient, 0);
                manager.registerListener(listener);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            manager = null;
            // TODO:这里重新绑定远程Service
            manager=IBookManager.Stub.asInterface(mBinder);
        }
    };


    //运行在客户端的Binder线程池中，因此无法更新UI
    private IOnNewBookArrivedListener listener = new IOnNewBookArrivedListener.Stub() {

        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            Message message = Message.obtain(handler, MESSAGE_NEW_BOOK_ARRIVED);
            message.obj = newBook;
            handler.sendMessage(message);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Button select = (Button) findViewById(R.id.select);
        Button add = (Button) findViewById(R.id.add_);
        select.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        if (manager != null && manager.asBinder().isBinderAlive()) {
            try {
                //取消监听
                manager.unregisterListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(connection);
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select: {
                //如果getBookList很耗时，应该开启线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (manager != null) {
                            try {
                                list = manager.getBookList();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                Toast.makeText(BookManagerActivity.this, "从服务端获取了图书列表\n" +
                        list.toString(), Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.add_: {
                Book book = new Book(3, "Java");
                try {
                    manager.addBook(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }
}
