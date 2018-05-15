package com.example.he.uibsetpractice;

/**
 * Created by he on 2016/4/2.
 */
public class Msg {
    public  static  final  int TYPE_RECEIVED =0;//表示收到的信息
    public  static  final int TYPE_SENT =1;//表示发出去的信息

    private  String content;
    private  int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
