package com.example.he.broadcastbestpractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private EditText ed_account;
    private EditText ed_password;
    private Button login;
    private CheckBox rememberPress;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_layout);

        ed_account = (EditText) findViewById(R.id.account);
        ed_password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        rememberPress = (CheckBox) findViewById(R.id.remember_pass);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor=preferences.edit();


        boolean isRemember = preferences.getBoolean("remember_password", false);
        if (isRemember) {
            String account = preferences.getString("account", "");
            String password = preferences.getString("password", "");

            ed_account.setText(account);
            ed_password.setText(password);
            rememberPress.setChecked(true);
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String account = ed_account.getText().toString();
                String password = ed_password.getText().toString();

                if (account.equals("admin") && password.equals("123456")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //如果选中复选框 将信息保存
                    if (rememberPress.isChecked()) {
                        editor.putString("account", account);
                        editor.putString("password", password);
                        editor.putBoolean("remember_password", true);
                    } else {
                        editor.clear();//清除SharedPreferences中的数据
                    }
                    editor.commit();
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "帐户或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
