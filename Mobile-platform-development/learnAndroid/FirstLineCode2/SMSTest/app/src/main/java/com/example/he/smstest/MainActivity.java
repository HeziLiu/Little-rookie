package com.example.he.smstest;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private TextView sender;
    private TextView content;
    private IntentFilter filter;
    private MessageReceiver messageReceiver;

    private EditText to;
    private EditText msg_input;
    private Button send;

    private IntentFilter sendfilter;
    private sendStatusReceiver statusReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);

        sender = (TextView) findViewById(R.id.sender);
        content = (TextView) findViewById(R.id.content);
        //动态注册
        filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, filter);

        to= (EditText) findViewById(R.id.to);
        msg_input= (EditText) findViewById(R.id.msg_input);
        send= (Button) findViewById(R.id.send);

        //发送短信是否成功检查
        sendfilter=new IntentFilter();
        sendfilter.addAction("SMS_SEND");
        statusReceiver =new sendStatusReceiver();
        registerReceiver(statusReceiver,sendfilter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送短信
                SmsManager manager=SmsManager.getDefault();
                Intent intent=new Intent("SMS_SEND");
                PendingIntent pi=PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
                manager.sendTextMessage(to.getText().toString(),null,msg_input.getText().toString(),null,pi);

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
    }

    class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");//提取短信消息
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
//                messages[i]=SmsMessage.createFromPdu((byte[]) pdus[i],"SmsConstants.FORMAT_3GPP2");
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            String address=messages[0].getOriginatingAddress();//获取发送号码
            //获取短信内容
            String mess="";
            for (SmsMessage message:messages){
                mess +=message.getMessageBody();
            }
            //TextView 显示短信
            sender.setText(address);
            content.setText(mess);
        }
    }
    //广播接收器用于监听短信是否发送成功
    class  sendStatusReceiver extends  BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (getResultCode()==RESULT_OK){
                Toast.makeText(context,"send success",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,"send failed",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
