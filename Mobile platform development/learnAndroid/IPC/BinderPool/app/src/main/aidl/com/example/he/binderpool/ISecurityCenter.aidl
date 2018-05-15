// IMyAidlInterface.aidl
package com.example.he.binderpool;

interface ISecurityCenter {
String encrypt(String content);
String decrypt(String password);

}
