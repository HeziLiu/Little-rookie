package client;

import client.model.conditioner

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;


public class SendMessage {

    private int type;           //����������
    private String room;           //����
    private int _switch;        //����
    private float curTemperature;  //��ǰ�¶�
    private float objTemperature; //Ĭ���¶�
    private int wind;           //����
    private float changeunit;
    private conditioner conditioner;
    private Timer timer;
    private TimerTask task;

    public SendMessage() {
        // TODO Auto-generated constructor stub
        conditioner=new conditioner();
        room=conditioner.getRoom();
        curTemperature=conditioner.getCurTemperature();
        objTemperature=conditioner.getObjTemperature();
        type=conditioner.getType();
        wind=conditioner.getWind();
        _switch=conditioner.get_switch();
        changeunit=conditioner.getChangeunit();
        objTemperature=conditioner.getObjTemperature();
        if(_switch==1)
        {
            if(objTemperature>curTemperature)
                risetemp();
            else if (objTemperature<curTemperature)
                downtemp();
        }
    }

    //�¶�����
    private void risetemp()
    {
        timer=new Timer(true);
        task=new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if(curTemperature<objTemperature)
                {
                    curTemperature=(float) (curTemperature+wind*changeunit);
                    conditioner.setCurTemperature(curTemperature);
                }
            }
        };
        timer.schedule(task, 0, 1000);//1s��һ���¶�
    }
    //����
    private void downtemp()
    {
        timer=new Timer(true);
        task=new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if(curTemperature>objTemperature)
                {
                    curTemperature=(float) (curTemperature-wind*changeunit);
                    conditioner.setCurTemperature(curTemperature);
                }
            }
        };
        timer.schedule(task, 0, 1000);//1s��һ���¶�
    }

    public void SendRequest(SocketChannel channel) throws IOException
    {

        int k=0;//������
        while (true) {
            //����������
            if(k==0)
            {
                String mString="{'type':0,'room':"+room+",'switch':"+_switch+",'temperature':"+objTemperature+",'wind':"+wind+"}";
                JSONObject jsonReq=new JSONObject();
                String req=jsonReq.toString();
                channel.write(ByteBuffer.wrap(req.getBytes(Charset.forName("UTF-8"))));
                k=1;
            }
        }
        //���������ģ��ֶΣ�type,room,switch,temperature,wind
        //return new String("{'type':0,'room':"+room+",'switch':"+_switch+",'temperature':"+objTemperature+",'wind':"+wind+"}") ;
    }

    public void SendStatus(SocketChannel channel) {
        //����ͨ�汨�ģ������ֶΣ�type=1����ǰ�¶�temperature
        Timer timer=new Timer(true);
        TimerTask task=new TimerTask() {
            @Override

            public void run() {
                // TODO Auto-generated method stub
                try {
                    String tString="{'type':1,'temperature':"+curTemperature+"}";
                    JSONObject jsonMsg=new JSONObject(tString);
                    String msg=jsonMsg.toString();
                    channel.write(ByteBuffer.wrap(msg.getBytes(Charset.forName("UTF-8"))));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task, 0,1000);//1s��һ���¶�
    }
}
