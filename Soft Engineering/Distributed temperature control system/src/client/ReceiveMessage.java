package client;

import client.model.conditioner;
import org.json.JSONObject;

public class ReceiveMessage {

    private int _swicth;
    private float objTemperature;
    private int wind;
    private float cost;

    public void deal(JSONObject msg) {
        conditioner conditioner=new conditioner();
        if(msg.getString("_switch")==null||msg.getString("temperature")==null||msg.getString("wind")==null||msg.getString("cost")==null)
        {
            System.out.println("���������������ֽ�����message:"+msg.toString());
        }
        else
        {
            _swicth=Integer.parseInt(msg.getString("_switch"));
            objTemperature=Float.parseFloat(msg.getString("temperature"));
            wind=Integer.parseInt(msg.getString("wind"));
            cost=Float.parseFloat(msg.getString("cost"));
            //�Դ��ͻ������ֶν��з�Χ���ж�
            if(_swicth!=0&&_swicth!=1)
                System.out.println("switch wrong! switch:"+_swicth);
            else if(objTemperature>=32||objTemperature<16)
                System.out.println("too hot/cool budy! +objTemperature:"+objTemperature);
            else if(wind!=0&&wind!=1&&wind!=2)
                System.out.println("wind wrong! wind:"+wind);
            else
            {
                conditioner.set_switch(_swicth);
                conditioner.setCurrentPay(cost);
                conditioner.setObjTemperature(objTemperature);
                conditioner.setWind(wind);
            }

        }

    }
}
