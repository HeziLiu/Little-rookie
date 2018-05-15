// IMyAidlInterface.aidl
package com.example.he.aidltest;
//显示的引用自定义的Parcelable对象和AIDL对象
import com.example.he.aidltest.Book;
import com.example.he.aidltest.IOnNewBookArrivedListener;

//声明暴露给客户端的接口
interface IBookManager {
List<Book> getBookList();
void addBook(in Book book);
void registerListener(IOnNewBookArrivedListener listener);//注册接口
void unregisterListener(IOnNewBookArrivedListener listener);//取消接口
}
