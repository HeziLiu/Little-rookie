package cqupt.ui.watercoming;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import tool.MyOpenHelper;

/**
 * Created by 78914 on 2017/6/12.
 */

public class rider_login_activity extends Activity {
    private EditText name;//用户名
    private EditText password;//用户密码
    private Button login;//登陆按钮
    private TextView register;//注册
    private TextView forgetNum;//忘记密码
    private MyOpenHelper dbHelper;
    Dialog dialog1;//用于生成注册对话框以及返回activity
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置Activity的无标题显示
        setContentView(R.layout.rider_login_layout);
        dbHelper=MyOpenHelper.getInstance(this);//获取数据库当前状态
        //获取控件
        name= (EditText) findViewById(R.id.rider_login_activity_name_input);
        password= (EditText) findViewById(R.id.rider_login_activity_password_input);
        login= (Button) findViewById(R.id.rider_login_activity_login);
        register= (TextView) findViewById(R.id.admin_login_activity_register);
        forgetNum= (TextView) findViewById(R.id.admin_login_activity_forgetNum);
        //跳转到登陆过的送水工界面
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameInfo=name.getText().toString();
                String passwordInfo=password.getText().toString();
                //从数据库中获取密码并判断是否相同
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                Cursor cursor=db.rawQuery("select password from rider where name=?",new String[]{nameInfo});
                String pi=null;
                if (cursor.moveToNext()){
                    pi=cursor.getString(cursor.getColumnIndex("password"));//获取密码
                }
                //密码正确跳转到登陆后的界面
                if(passwordInfo.equals(pi)){
                    Intent intent=new Intent(rider_login_activity.this,rider_activity.class);
                    startActivity(intent);
                    cursor.close();
                }else{
                    //弹出错误提示
                    Toast.makeText(rider_login_activity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //自定义AlertDialog用于注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //构建对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(rider_login_activity.this);
                //解析对话框布局
                LayoutInflater factory = LayoutInflater.from(rider_login_activity.this);
                final View textEntryView = factory.inflate(R.layout.register, null);
                //设置对话框布局
                builder.setView(textEntryView);
                //获取对话框布局控件
                final Button rider_postive= (Button) textEntryView.findViewById(R.id.rider_postive);
                final Button rider_negative= (Button) textEntryView.findViewById(R.id.rider_negative);
                final EditText code = (EditText) textEntryView.findViewById(R.id.admin_register_info);
                final EditText name = (EditText) textEntryView.findViewById(R.id.admin_register_name);
                final EditText firstPassword = (EditText) textEntryView.findViewById(R.id.admin_register_first_password);
                final EditText secondPassword = (EditText) textEntryView.findViewById(R.id.admin_register_second_password);
                //取消按钮点击事件
                rider_negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();//对话框消失
                    }
                });
                //确定按钮点击事件
                rider_postive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String codeInfo = code.getText().toString();
                        //注册码要为10086
                        if (codeInfo.equals("10086")) {
                            String nameInfo = name.getText().toString();
                            String firstPasswordInfo = firstPassword.getText().toString();
                            String secondPasswordInfo = secondPassword.getText().toString();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //检测密码是否为6个数字
                            if (firstPasswordInfo.matches("[0-9]{6}")) {
                                // 两次密码是否相同
                                if (firstPasswordInfo.equals(secondPasswordInfo)) {
                                    Cursor cursor = db.rawQuery("select name from rider where name=? ", new String[]{nameInfo});
                                    //用户是否存在
                                    if (cursor.moveToNext()) {
                                        Toast.makeText(rider_login_activity.this, "该用户已存在", Toast.LENGTH_SHORT).show();
                                    } else {
                                        db.execSQL("insert into rider(name,password)values(?,?)", new String[]{nameInfo, firstPasswordInfo});
                                        //弹出注册成功提示，并将数据插入员工表
                                        Toast.makeText(rider_login_activity.this,"注册成功",Toast.LENGTH_LONG).show();
                                        dialog1.dismiss();
                                    }
                                } else {
                                    //弹出错误提示
                                    Toast.makeText(rider_login_activity.this, "两次密码不相同", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                //弹出错误提示
                                Toast.makeText(rider_login_activity.this, "密码为6位纯数字", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //弹出错误提示
                            Toast.makeText(rider_login_activity.this, "注册码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog1=builder.create();//创建对话框
                dialog1.show();//显示对话框
            }
        });
        forgetNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rider_login_activity.this,"此功能暂不支持",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
