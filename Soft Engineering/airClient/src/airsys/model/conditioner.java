package airsys.model;

import airsys.Configure;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static airsys.Configure.CURRENT_TEMP;

public class conditioner {

    public static final int pending=0;//无风状态
    public static final int low=1;  //小风
    public static final int mid=2;  //微风
    public static final int high=3; //中风

    public static final int COLD_MODE=0;
    public static final int HOT_MODE=1;

    public static final int OFF=0;//switch on or off
    public static final int ON=1;

    private static final float TEMP_GRID=0.05f;//回温单位

    private String room;            //房号
    private String ipAddr;          //ip地址
    private int _switch;            //开关
    private float currentTemp;      //当前温度
    private float targetTemp;        //目标温度
    private int wind;               //风速
    private float currentPay;     //金额
    private int workMode;

    private DataChangedListener listener;

    public conditioner(String room,String ipAddr) {
        this.room = room;
        this.ipAddr=ipAddr;
        this.wind=mid;//默认风速为中风
        this.currentTemp= CURRENT_TEMP;
        this.workMode=COLD_MODE;
        this.currentPay=0;
    }

    public void addDataChangedListener(DataChangedListener listener){
        this.listener=listener;
    }

    public float getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(float currentTemp) {
        this.currentTemp = currentTemp;
    }

    public float getTargetTemp() {
        return targetTemp;
    }

    public boolean setTargetTemp(float targetTemp) {
        System.out.println("work mode: "+workMode+"target: "+targetTemp);
        if ((workMode==COLD_MODE&&targetTemp<=25.0f&&targetTemp>=16.0f)||
                (workMode==HOT_MODE&&targetTemp<=30.0f&&targetTemp>=27.0f)){
            this.targetTemp = targetTemp;
            return true;
        }else{
            this.targetTemp= Configure.DEFAULT_TARGET_TEMP;
        }
        return false;
    }

    public float getCurrentPay() {
        return currentPay;
    }

    public void setCurrentPay(float currentPay) {
        this.currentPay = currentPay;
        listener.paymentChanged(currentPay);
    }

    public int getWorkMode() {
        return workMode;
    }

    public void setWorkMode(int workMode) {
        if (workMode!=this.workMode&&(workMode==COLD_MODE||workMode==HOT_MODE)){
            this.workMode = workMode;
            listener.workModeChanged(workMode);
        }
    }

    public DataChangedListener getListener() {
        return listener;
    }

    public void setListener(DataChangedListener listener) {
        this.listener = listener;
    }

    public String getIpAddr() {

        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        if (wind!=this.wind&&wind>=pending&&wind<=high){
            this.wind = wind;
            System.out.println("wind: "+wind);
            listener.windChanged(wind);
        }

    }
    //每一秒变化温度，小风0.05 中风0.1 大风0.2
    public void addTempChangeDaemon(){
       new Timer(1000, new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (workMode==HOT_MODE&&currentTemp<=30.0f){
                   switch (wind){
                       case 1:currentTemp+=0.05;
                       break;
                       case 2:currentTemp+=0.1;
                       break;
                       case 3:currentTemp+=0.2;
                       break;
                       default:break;
                   }
               }else if(workMode==COLD_MODE&&currentTemp>=16.0f){
                   switch (wind){
                       case 1:currentTemp-=0.05;
                           break;
                       case 2:currentTemp-=0.1;
                           break;
                       case 3:currentTemp-=0.2;
                           break;
                       default:break;
                   }
               }
               listener.temperatureChanged(currentTemp);
           }
       }).start();
    }

    public void addReturnEnv(){
        //向室温26℃靠近
        new Timer(1000,e->{
            if (workMode==HOT_MODE&&currentTemp>CURRENT_TEMP){
                currentTemp-=TEMP_GRID;
            }else if (workMode==COLD_MODE&&currentTemp<CURRENT_TEMP){
                currentTemp+=TEMP_GRID;
            }
            listener.temperatureChanged(currentTemp);
        }).start();
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int get_switch() {
        return this._switch;
    }

    public void set_switch(int _switch) {
        if (_switch==ON||_switch==OFF){
            this._switch = _switch;
        }

    }

    public String toString(){
        return "room #"+room+"ip: "+ipAddr+" current temperature: "+currentTemp+
                " target temperature: "+targetTemp+" wind: "+wind;
    }
}
