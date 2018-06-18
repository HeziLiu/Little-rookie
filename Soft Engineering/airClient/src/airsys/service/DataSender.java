package airsys.service;


import airsys.Configure;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static airsys.Configure.REMOTE_IP;

public class DataSender {
    private int port;
    private ServerListener listener;
    private Socket serverSocket;
    private PrintWriter pw;
    private String msg;

    public DataSender(int port) throws IOException {
        this.port=port;
        init();
    }

    public DataSender() throws IOException {
        this(Configure.DEFAULT_PORT);
    }

    private void init() throws IOException {
        serverSocket=new Socket(REMOTE_IP,port);
        pw=new PrintWriter(serverSocket.getOutputStream(),true);
    }

    public Socket getServerSocket() {
        return this.serverSocket;
    }

    public boolean connect(InetAddress addr, int port, String id)throws IOException{
        msg="connect remote: "+addr+" port: "+port+" room id: "+id;
        System.out.println(msg);
        //pw.println(msg);
        return true;
    }

    public void disconnect(InetAddress addr,int port,String id)throws IOException{
        msg="disconnect remote: "+addr+" port: "+port+" room id: "+id;
        System.out.println(msg);
        //pw.println(msg);
    }

    public void sendStatus(InetAddress addr,int port,String id,float temp)throws IOException{
        System.out.println("send status: "+addr+" port: "+port+" temperature: "+temp);
        msg="{\"type\":1,\"room\":\""+id+"\",\"temperature\":"+temp+"}";
        //todo 报文格式改变 id+引号
        pw.println(msg);
    }

    public void request(InetAddress addr,int port,String id,int _switch,float temp,int wind){
        System.out.println("request send: "+addr+" port: "+port+" room id: "+id+" switch: "+_switch+" temperature: "+temp+" wind: "+wind);
        msg="{\"type\":0,\"room\":"+id+",\"switch\":"+_switch+",\"temperature\":"+temp+",\"wind\":"+wind+"}";
        pw.println(msg);
    }
}
