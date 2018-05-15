package com.example.he.aidltest.com.example.he.mybinder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.example.he.aidltest.Book;

import java.util.List;

/**
 * 自己实现一个Binder,
 * AIDL并不是实现Binder的必须品，
 * AIDL文件只是为我们提供了一种快速实现Binder的工具而已
 * Created by he on 2017/5/8.
 */

public interface IBookManager extends IInterface {
    static final String DESCRIPTOR = "com.example.he.aidltest.com.example.he.mybinder.IBookManager";//Binder的唯一标识符，一般为当前类名
    static final int TRASACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;//用于在onTransact中进行方法判断，aidi中声明了几个方法就要写几个

    public List<Book> getBookList() throws RemoteException;


    /**
     * 实现Stub类和Stub类中的Proxy代理
     */

    public class BookManagerImpl extends Binder implements IBookManager {


        public BookManagerImpl() {
            //连接接口
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBookManager asInterface(IBinder obj) {
            if (obj == null)
                return null;
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);//查询带有该标识符的Binder对象
            if (((iin != null) && (iin instanceof IBookManager))) {
                return (IBookManager) iin;
            }

            return new BookManagerImpl.Proxy(obj);

        }


        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: {
                 reply.writeString(DESCRIPTOR);
                    return  true;
                }
                case TRASACTION_getBookList:{
                    data.enforceInterface(DESCRIPTOR);
                    List<Book> result=this.getBookList();
                    reply.writeNoException();;
                    reply.writeTypedList(result);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            //TODO 待实现
            return null;
        }

        @Override
        public IBinder asBinder() {
            //TODO 待实现
            return this;
        }


        /**
         * 内部代理类
         */
        public static class Proxy implements IBookManager {

            private IBinder mRemote;

            Proxy(IBinder remote) {
                mRemote = remote;
            }

            @Override
            public List<Book> getBookList() throws RemoteException {
               Parcel data=Parcel.obtain();
                Parcel reply=Parcel.obtain();
                List<Book> result;
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRASACTION_getBookList,data,reply,0);
                    reply.readException();
                    result=reply.createTypedArrayList(Book.CREATOR);
                }finally {
                    reply.recycle();
                    data.recycle();
                }

                return  result;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }

           public  String getInterfaceDerscriptor(){
               return  DESCRIPTOR;
           }



        }

    }


}
