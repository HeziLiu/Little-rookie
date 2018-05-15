package com.example.he.myqq.utils;

/**
 * 每条消息的实体类
 * Created by he on 2017/1/30.
 */

public class MessageBean {

    private String uri;
    private String name;
    private String info;
    private String time;

    public MessageBean(String info, String name, String time, String uri) {
        this.info = info;
        this.name = name;
        this.time = time;
        this.uri = uri;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getUri() {
        return uri;
    }
}
