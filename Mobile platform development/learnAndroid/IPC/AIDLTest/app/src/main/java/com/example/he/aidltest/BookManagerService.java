package com.example.he.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 服务端，实现AIDL中定义的接口,AIDL添加权限验证的方式使用自定义权限的方式，如果没有权限在onBind方法中返回null即可
 * Created by he on 2016/12/27.
 */

public class BookManagerService extends Service {

    private static final String TAG = "BSM";
    //图书链表
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    //监听链表,使用该方法无法在退出软件时取消监听，因为经过Binder的转换会产生一个新的对象
//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<IOnNewBookArrivedListener>();

    //系统提供了一个专门用于删除跨进程的listener接口RemoteCallbackList
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<IOnNewBookArrivedListener>();


    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);


    //被客户端调用的方法运行在服务端的Binder线程池中
    private Binder mBinder = new IBookManager.Stub() {
        //实现AIDL中声明的接口中的方法

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (!mListenerList.contains(listener)) {
//                mListenerList.add(listener);
//                Log.d(TAG, "成功添加该监听");
//
//            } else {
//                Log.d(TAG, "该监听已存在");
//            }

            mListenerList.register(listener);


        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (mListenerList.contains(listener)) {
//                mListenerList.remove(listener);
//                Log.d(TAG, "成功移除该监听");
//            } else {
//                Log.d(TAG, "不存在该监听");
//            }
            mListenerList.unregister(listener);
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "IOS"));
        new Thread(new ServiceWorker()).start();//服务端每5s中向图书列表中添加一本新书
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    private void onNewBookArrived(Book newBook) throws RemoteException {
        mBookList.add(newBook);
//        for (int i = 0; i < mListenerList.size(); i++) {
//            IOnNewBookArrivedListener listener = mListenerList.get(i);
//            //有新书通知每个实现了监听的用户
//            listener.onNewBookArrived(newBook);
//        }

        //beginBroadcast()和finishBroadcast()必须配对使用
        final int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener l = mListenerList.getBroadcastItem(i);
            if (l != null) {
                l.onNewBookArrived(newBook);
            }
        }
        mListenerList.finishBroadcast();

    }


    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                try {
                    onNewBookArrived(new Book(bookId, "new book#" + bookId));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
