package client.controller;

import client.Configure;
import client.SendMessage;
import client.ServerListener;
import client.TcpServer;
import client.model.conditioner;
import client.view.OverViewPanel;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ViewController {
    private SendMessage sender;
    private OverViewPanel view;
    private conditioner model;
    private InetAddress remoteAddr;
    private int port=Configure.REMOTE_PORT;
    private boolean isOn=false;
    private Timer dataTimer;//for send status to the server
    private Thread serv;
    private int tick=Configure.DEFAULT_TICK;

    public ViewController(OverViewPanel panel, conditioner model)throws UnknownHostException {
        this.view=view;
        this.model=model;
        try {
            sender=new DataSender();
        }catch (SocketException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        remoteAddr=InetAddress.getByName(Configure.REMOTE_IP);
        dataTimer = new Timer(tick, e -> {
            try {//每隔1秒传送房间状态
                sender.sendStatus(remoteAddr, port, model.getCurrentTemp());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        serv=new Thread(new TcpServer(Configure.DEFAULT_RECV_PORT, new ServerListener() {
            @Override// switch  temperature(target) wind cost
            public void onReceive(JSONObject jsonObject) {
                int _switch=jsonObject.getInt("switch");
                if (_switch==0){
                    model.set_switch(0);//关机
                }else{
                    float targetTemp=jsonObject.getFloat("temperature");
                    int wind=jsonObject.getInt("wind");
                    float cost=jsonObject.getFloat("cost");
                    model.setCurrentPay(cost);
                    if (wind>model.low){
                        model.set_switch(1);
                    }else{
                        model.set_switch(0);
                    }
                    model.setTargetTemp(targetTemp);
                    model.setWind(wind);
                }
            }

            @Override
            public void onException(Exception e) {
                //TODO
            }
        }));
        setupEvent();
    }

    private void setupEvent() {
        view.bootButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isOn){//boot and connect the remote server
                    //sender.connect(remoteAddr,port,model.getRoom());
                    //sender.request(remoteAddr,port,model.getRoom(),0,model.getTargetTemp(),model.low);
                    dataTimer.start();
                    view.initStatus();
                    view.setCurrTemp((int) model.getCurrentTemp());
                    view.setTargetTemp((int) model.getTargetTemp());
                    view.setPayment(0.0f);
                    view.bootButton.setText("关机");
                    model.addTempChangeDaemon();
                    serv.start();
                }else{

                }
            }
        });
        view.windButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        view.upButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        view.downButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
