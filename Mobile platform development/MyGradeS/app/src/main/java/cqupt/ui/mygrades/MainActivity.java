package cqupt.ui.mygrades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//主界面UI模块
public class MainActivity extends Activity {
    private Button select;//查询学生信息按钮
    private Button add;//添加学生信息按钮
    private long exit_time;//用于实现按两次返回退出
    private MyApplication application;//用于按两次退出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        application=MyApplication.getInstance();
        application.addActivity(MainActivity.this);
        //关联控件
        select= (Button) findViewById(R.id.select);
        add= (Button) findViewById(R.id.add_main);
        //按钮监听选择查找则跳入select_activity
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,select_activity.class);
                startActivity(intent);
            }
        });
        //按钮监听选择查找则跳入add_activity
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,add_activity.class);
                startActivity(intent);
            }
        });
    }
    //获取按键并比较两次按back的时间大于2s不退出，否则退出
    public void onBackPressed(){

        if ((System.currentTimeMillis()-exit_time)>2000){
                Toast.makeText(MainActivity.this,"再按一次退出",Toast.LENGTH_LONG).show();
                exit_time=System.currentTimeMillis();
            }else{
                application.exit();//利用application类退出加载过的所有Activity
            }

    }
}
