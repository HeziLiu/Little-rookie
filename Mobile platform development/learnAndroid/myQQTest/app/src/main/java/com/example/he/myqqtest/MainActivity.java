package com.example.he.myqqtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements EMMessageListener {


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
        loginHX();

        //设置消息监听
        EMClient.getInstance().chatManager().addMessageListener(mMessageListener);
    }

    private void loginHX() {
        //已经直接注册过的账户，直接登录即可
        EMClient.getInstance().login("888888123456", "123456", new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "环信登录成功，即时通信功能可以使用", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(final int i, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "环信登录失败，即时通信功能无法使用" + i, Toast.LENGTH_SHORT).show();
                        switch (i) {
                            // 网络异常 2
                            case EMError.NETWORK_ERROR:
                                Toast.makeText(MainActivity.this, "网络错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无效的用户名 101
                            case EMError.INVALID_USER_NAME:
                                Toast.makeText(MainActivity.this, "无效的用户名 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无效的密码 102
                            case EMError.INVALID_PASSWORD:
                                Toast.makeText(MainActivity.this, "无效的密码 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 用户认证失败，用户名或密码错误 202
                            case EMError.USER_AUTHENTICATION_FAILED:
                                Toast.makeText(MainActivity.this, "用户认证失败，用户名或密码错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 用户不存在 204
                            case EMError.USER_NOT_FOUND:
                                Toast.makeText(MainActivity.this, "用户不存在 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无法访问到服务器 300
                            case EMError.SERVER_NOT_REACHABLE:
                                Toast.makeText(MainActivity.this, "无法访问到服务器 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 等待服务器响应超时 301
                            case EMError.SERVER_TIMEOUT:
                                Toast.makeText(MainActivity.this, "等待服务器响应超时 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 服务器繁忙 302
                            case EMError.SERVER_BUSY:
                                Toast.makeText(MainActivity.this, "服务器繁忙 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 未知 Server 异常 303 一般断网会出现这个错误
                            case EMError.SERVER_UNKNOWN_ERROR:
                                Toast.makeText(MainActivity.this, "未知的服务器异常 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;

                        }
                    }
                });

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

    }


    @Override
    public void onMessageReceived(List<EMMessage> list) {
        final StringBuilder sb = new StringBuilder();
        for (EMMessage emMessage : list) {
            EMTextMessageBody body = (EMTextMessageBody) emMessage.getBody();
            sb.append(body.getMessage() + "\n");
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                message.append("123456888888 ----->" + sb.toString());
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

    @OnClick(R.id.send)
    public void onClick() {
        String msg = editMessage.getText().toString();
        if (!TextUtils.isEmpty(msg)) {
            // 创建一条新消息，第一个参数为消息内容，第二个为接受者username
            EMMessage m = EMMessage.createTxtSendMessage(msg, "123456888888");
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
                            Toast.makeText(MainActivity.this, "消息发送成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(final int i, String s) {
                    // 消息发送失败，打印下失败的信息，正常操作应该去刷新ui
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "消息发送失败" + "errCode:" + i, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除消息监听
        EMClient.getInstance().chatManager().removeMessageListener(mMessageListener);
    }
}
