
package com.example.he.aidltest;
import com.example.he.aidltest.Book;
//该接口用于当添加新书时向实现了该接口的用户推送信息
interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
