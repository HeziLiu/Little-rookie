package cqupt.ui.watercoming;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
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
public class student_login_activity extends Activity {
    private EditText name;//账户名输入框
    private EditText password;//密码输入框
    private Button login;//登陆按钮
    private MyOpenHelper dbHelper;//用于数据库操作
    private TextView register;//注册TextView
    private TextView forgetNum;//忘记密码TextView
    Dialog dialog;//用于注册对话框返回Activity
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Activity无标题显示
        setContentView(R.layout.student_login_layout);
        //获取控件
        name= (EditText) findViewById(R.id.student_login_activity_name_input);
        password= (EditText) findViewById(R.id.student_login_activity_password_input);
        login= (Button) findViewById(R.id.student_login_activity_login);
        register= (TextView) findViewById(R.id.student_login_activity_register);
        forgetNum= (TextView) findViewById(R.id.student_login_activity_forgetNum);
        //获取数据库状态
        dbHelper=MyOpenHelper.getInstance(this);
        //自定义AlertDialog用于注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //生成Builder对象用于创建对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(student_login_activity.this);
                //解析对话框布局
                LayoutInflater factory = LayoutInflater.from(student_login_activity.this);
                final View textEntryView = factory.inflate(R.layout.student_register, null);
                //解析对话框布局
                builder.setView(textEntryView);
                //设置对话框布局
                final EditText code = (EditText) textEntryView.findViewById(R.id.student_register_info);
                final EditText name = (EditText) textEntryView.findViewById(R.id.student_register_name);
                final EditText num=(EditText)textEntryView.findViewById(R.id.student_register_num);
                final EditText firstPassword = (EditText) textEntryView.findViewById(R.id.student_register_first_password);
                final EditText secondPassword = (EditText) textEntryView.findViewById(R.id.student_register_second_password);
                final Button student_positive= (Button) textEntryView.findViewById(R.id.student_postive);
                final Button student_negative= (Button) textEntryView.findViewById(R.id.student_negative);
                //取消按钮点击事件
                student_negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();//对话框消失
                    }
                });
                //确定按钮点击事件
                student_positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String codeInfo = code.getText().toString();
                        //注册码要为10086
                        if (codeInfo.equals("10086")) {
                            String nameInfo = name.getText().toString();
                            String firstPasswordInfo = firstPassword.getText().toString();
                            String secondPasswordInfo = secondPassword.getText().toString();
                            String roomNum=num.getText().toString();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //检测密码是否为6个数字
                            if (firstPasswordInfo.matches("[0-9]{6}")) {
                                // 两次密码是否相同
                                if (firstPasswordInfo.equals(secondPasswordInfo)) {
                                    Cursor cursor = db.rawQuery("select name from student where name=? ", new String[]{nameInfo});
                                    //用户是否存在
                                    if (cursor.moveToNext()) {
                                        Toast.makeText(student_login_activity.this, "该用户已存在", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //操作均无误，对相应表插入数据
                                        db.execSQL("insert into student(num,name,password)values(?,?,?)", new String[]{roomNum,nameInfo, firstPasswordInfo});
                                        //弹出操作成功提示
                                        Toast.makeText(student_login_activity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                } else {
                                    //弹出操作有误提示
                                    Toast.makeText(student_login_activity.this, "两次密码不相同", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                //弹出操作有误提示
                                Toast.makeText(student_login_activity.this, "密码为6位纯数字", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            //弹出操作有误提示
                            Toast.makeText(student_login_activity.this, "注册码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog=builder.create();//创建对话框
                dialog.show();//显示对话框
            }
        });
        //忘记密码点击事件
        forgetNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(student_login_activity.this,"此功能暂不支持",Toast.LENGTH_SHORT).show();
            }
        });
        //登陆按钮点击事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取相应学生信息用于Intent传值
                String studentId = name.getText().toString();
                String studentPassword = password.getText().toString();
                //判断输入信息是否为空
                if (!TextUtils.isEmpty(studentId) && !TextUtils.isEmpty(studentPassword)) {
                    //获取数据库写操作权限
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    //从学生表查出密码
                    Cursor cursor = db.rawQuery("select password from student where name=?", new String[]{studentId});
                    if (cursor.moveToNext()) {
                        //获取相应密码
                        String password = cursor.getString(cursor.getColumnIndex("password"));
                        //密码是否匹配
                        if (password.equals(studentPassword)) {
                            //启动学生登录后的界面并将学生的姓名（name）传过去用于个人中心页面的显示
                            Toast.makeText(student_login_activity.this,studentId+"已登陆",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(student_login_activity.this, student_activity.class);
                            intent.putExtra("haveData","true");
                            intent.putExtra("name",studentId);
                            startActivity(intent);
                        } else {
                            //弹出操作有误提示
                            Toast.makeText(student_login_activity.this, "密码错误请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //弹出操作有误提示
                        Toast.makeText(student_login_activity.this, "该学号未注册", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //弹出操作有误提示
                    Toast.makeText(student_login_activity.this, "帐户，密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
