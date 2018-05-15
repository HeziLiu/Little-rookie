// IMyAidlInterface.aidl
package com.example.he.aidltest;

import com.example.he.aidltest.Book;//必须要导入book类
/**
* 自己定义的接口，用于和服务端通信
* */

interface IBookManager {
List<Book> getBookList();//从服务端获取服务列表
void addBook(in Book book);//往图书列表中添加一本书

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
