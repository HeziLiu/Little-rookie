package com.example.he.messengertest;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;


public class MessengerService extends Service {
    public MessengerService() {
    }

    private static class MessengerHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_CLIENT: {
                    String info = msg.getData().getString("msg");
                    Toast.makeText(myApplication.getContext(), "receive msg from Client: " + info, Toast.LENGTH_SHORT).show();
                    Messenger client=msg.replyTo;
                    Message message=Message.obtain(null,MyConstants.MSG_FROM_SERVICE);
                    Bundle bundle=new Bundle();
                    bundle.putString("reply","你好客户端，我已收到你的信息");
                    message.setData(bundle);

                    try {
                        client.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            super.handleMessage(msg);
        }
    }

    private final Messenger messenger = new Messenger(new MessengerHandle());


    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
