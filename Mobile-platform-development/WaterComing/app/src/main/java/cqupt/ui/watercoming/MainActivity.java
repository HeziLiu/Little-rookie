package cqupt.ui.watercoming;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tool.MyOpenHelper;

public class MainActivity extends AppCompatActivity {
    private Button rider;
    private Button student;
    private MyOpenHelper dbHelper;
    //补充按两次退出界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定控件
        rider= (Button) findViewById(R.id.main_activity_rider);
        student= (Button) findViewById(R.id.main_activity_student);
        dbHelper=MyOpenHelper.getInstance(this);
        //获取数据库写操作
        dbHelper.getWritableDatabase();
        //跳转到送水工登陆界面
        rider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,rider_login_activity.class);
                startActivity(intent);
            }
        });
        //跳转到学生登陆界面
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,student_login_activity.class);
                startActivity(intent);
            }
        });
    }
}
