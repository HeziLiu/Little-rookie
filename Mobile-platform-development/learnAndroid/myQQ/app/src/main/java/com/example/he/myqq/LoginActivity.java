package com.example.he.myqq;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.he.myqq.utils.HideStatusBar;
import com.example.he.myqq.utils.MyApplication;
import com.example.he.myqq.utils.SharedPreferrenceHelper;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private boolean l = false;//用于判断是否已经登陆到了环信

    private EditText userID;
    private EditText password;
    private Button login;
    private TextView forgetPassword;
    private TextView register;
    private CheckBox checkBox;
    private TextView clause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        HideStatusBar.hide(this);

        userID = (EditText) findViewById(R.id.login_edit);
        password = (EditText) findViewById(R.id.password_edit);
        login = (Button) findViewById(R.id.login_button);
        forgetPassword = (TextView) findViewById(R.id.textView);
        register = (TextView) findViewById(R.id.textView2);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        clause = (TextView) findViewById(R.id.textView5);

        login.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        register.setOnClickListener(this);
        checkBox.setOnClickListener(this);
        clause.setOnClickListener(this);

        //登录环信
        loginHX();
    }


    private void loginHX() {
        //已经直接注册过的账户，直接登录即可
        EMClient.getInstance().login("123456888888", "123456", new EMCallBack() {
            @Override
            public void onSuccess() {
                l = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "环信登录成功，即时通信功能可以使用", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(final int i, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "环信登录失败，即时通信功能无法使用" + i, Toast.LENGTH_SHORT).show();
                        switch (i) {
                            // 网络异常 2
                            case EMError.NETWORK_ERROR:
                                Toast.makeText(LoginActivity.this, "网络错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无效的用户名 101
                            case EMError.INVALID_USER_NAME:
                                Toast.makeText(LoginActivity.this, "无效的用户名 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无效的密码 102
                            case EMError.INVALID_PASSWORD:
                                Toast.makeText(LoginActivity.this, "无效的密码 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 用户认证失败，用户名或密码错误 202
                            case EMError.USER_AUTHENTICATION_FAILED:
                                Toast.makeText(LoginActivity.this, "用户认证失败，用户名或密码错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 用户不存在 204
                            case EMError.USER_NOT_FOUND:
                                Toast.makeText(LoginActivity.this, "用户不存在 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无法访问到服务器 300
                            case EMError.SERVER_NOT_REACHABLE:
                                Toast.makeText(LoginActivity.this, "无法访问到服务器 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 等待服务器响应超时 301
                            case EMError.SERVER_TIMEOUT:
                                Toast.makeText(LoginActivity.this, "等待服务器响应超时 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 服务器繁忙 302
                            case EMError.SERVER_BUSY:
                                Toast.makeText(LoginActivity.this, "服务器繁忙 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 未知 Server 异常 303 一般断网会出现这个错误
                            case EMError.SERVER_UNKNOWN_ERROR:
                                Toast.makeText(LoginActivity.this, "未知的服务器异常 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                Log.d(TAG, "userName" + userID.getText().toString() + "  password" + password.toString());
                if (userID.getText().toString().equals("123456") && password.getText().toString().equals("123456")) {
                    //保存密码
                    SharedPreferrenceHelper.savePassword(MyApplication.getContext(), true);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    //切换动画
                    overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
                    this.finish();
                } else {
                    Toast.makeText(this, "账户或密码错误", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.checkBox:
                if (!checkBox.isChecked()) {
                    login.setTextColor(Color.GRAY);
                    login.setClickable(false);
                } else {
                    login.setTextColor(Color.WHITE);
                    login.setClickable(true);
                }
                break;
            case R.id.textView:
                Toast.makeText(this, "暂不支持", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textView2:
                Toast.makeText(this, "暂不支持", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textView5:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
                break;
            default:
                break;

        }
    }
}
