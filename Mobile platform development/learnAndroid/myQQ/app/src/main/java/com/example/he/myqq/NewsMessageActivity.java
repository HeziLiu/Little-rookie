package com.example.he.myqq;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发送消息的界面
 * Created by he on 2017/2/5.
 */

public class NewsMessageActivity extends AppCompatActivity implements EMMessageListener {
    @Bind(R.id.message)
    TextView message;
    @Bind(R.id.edit_message)
    EditText editMessage;
    @Bind(R.id.send)
    Button send;

    private EMMessageListener mMessageListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsmessage);
        ButterKnife.bind(this);
        mMessageListener = this;
        //设置消息监听
        EMClient.getInstance().chatManager().addMessageListener(mMessageListener);
    }

    @OnClick(R.id.send)
    public void onClick() {
        String msg = editMessage.getText().toString();
        if (!TextUtils.isEmpty(msg)) {
            // 创建一条新消息，第一个参数为消息内容，第二个为接受者username
            EMMessage m = EMMessage.createTxtSendMessage(msg, "888888123456");
            // 调用发送消息的方法
            EMClient.getInstance().chatManager().sendMessage(m);
            // 为消息设置回调
            m.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    // 消息发送成功，打印下日志，正常操作应该去刷新ui
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NewsMessageActivity.this, "消息发送成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(final int i, String s) {
                    // 消息发送失败，打印下失败的信息，正常操作应该去刷新ui
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NewsMessageActivity.this, "消息发送失败" + "errCode:" + i, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onProgress(int i, String s) {
                    // 消息发送进度，一般只有在发送图片和文件等消息才会有回调，txt不回调
                }
            });


            //显示消息内容
            message.append("me ---->" + msg + "\n");
            editMessage.setText("");
        }

    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //设置消息监听
//        EMClient.getInstance().chatManager().addMessageListener(mMessageListener);
//    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除消息监听
        EMClient.getInstance().chatManager().removeMessageListener(mMessageListener);
    }

    /**
     * 循环消息监听
     *
     * @param list
     */


    //收到新消息，刷新消息列表
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        final StringBuilder sb = new StringBuilder();
        for (EMMessage emMessage : list) {
            EMTextMessageBody body = (EMTextMessageBody) emMessage.getBody();
            sb.append(body.getMessage()+"\n");
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                message.append("888888123456 ----->" + sb.toString());
            }
        });

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }
}
