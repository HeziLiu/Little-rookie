package cqupt.ui.mygrades;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tools.MyOpenHelper;

/**
 * Created by 78914 on 2017/6/3.
 */
//添加信息模块
public class add_activity extends Activity {
    private EditText num;
    private EditText name;
    private EditText grade;
    private EditText jz;
    private EditText ls;
    private EditText hb;
    private EditText xd;
    private EditText mg;
    private EditText ty;
    private EditText dw;
    private EditText dy;
    private MyOpenHelper helper;
    private String oldID;//用于保存旧有数据学生ID
    private Button add;
    private Button cancel;
    Intent oldData;//接收来自select_activity的传值
    private MyApplication application;//用于按两次退出
    //sum和avg总分和平均分利用传进来的各科目成绩计算得出
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.add);

        application=MyApplication.getInstance();
        application.addActivity(add_activity.this);
        //关联控件
        num = (EditText) findViewById(R.id.etnum);
        name = (EditText) findViewById(R.id.etname);
        grade = (EditText) findViewById(R.id.etgrade);
        jz = (EditText) findViewById(R.id.etjz);
        ls = (EditText) findViewById(R.id.etls);
        hb = (EditText) findViewById(R.id.ethb);
        xd = (EditText) findViewById(R.id.etxd);
        mg = (EditText) findViewById(R.id.etmg);
        ty = (EditText) findViewById(R.id.etty);
        dw = (EditText) findViewById(R.id.etdw);
        dy = (EditText) findViewById(R.id.etdy);
        add= (Button) findViewById(R.id.add_Info);
        cancel= (Button) findViewById(R.id.cancel);
        helper=MyOpenHelper.getInstance(this);

        oldData=getIntent();//接收Intent传值
        String it=oldData.getStringExtra("haveData");//防止出现空指针引入新变量
        if (it!=null&&it.equals("true")){
            initInfo();//恢复旧数据
        }
        //按钮监听
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertStudent();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(add_activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void insertStudent() {
        String stunum=num.getText().toString();
        String stuname=name.getText().toString();
        String stugrade=grade.getText().toString();
        int stujz=Integer.parseInt(String.valueOf(jz.getText()));
        int stuls=Integer.parseInt(String.valueOf(ls.getText()));
        int stuhb=Integer.parseInt(String.valueOf(hb.getText()));
        int stuxd=Integer.parseInt(String.valueOf(xd.getText()));
        int stumg=Integer.parseInt(String.valueOf(mg.getText()));
        int stuty=Integer.parseInt(String.valueOf(ty.getText()));
        int studw=Integer.parseInt(String.valueOf(dw.getText()));
        int study=Integer.parseInt(String.valueOf(dy.getText()));
        int stusum=stujz+stuls+stuhb+stuxd+stumg+stuty+studw+study;
        int stuavg=stusum/8;//总分和平均分通过传进来的参数直接计算

        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL("delete from Student where _id=?",new String[]{oldID});//删除旧数据
        //判断学号是否重复
        Cursor cursor=db.rawQuery("select * from Student where _id=?",new String[]{stunum});
        if (cursor.moveToNext()) {
            Toast.makeText(add_activity.this, "已有学生使用该学号,请重新输入", Toast.LENGTH_SHORT).show();
        }else{
            ContentValues values=new ContentValues();
            values.put("_id",stunum);
            values.put("name",stuname);
            values.put("grade",stugrade);
            values.put("jz",stujz);
            values.put("ls",stuls);
            values.put("hb",stuhb);
            values.put("xd",stuxd);
            values.put("mg",stumg);
            values.put("ty",stuty);
            values.put("dw",studw);
            values.put("dy",study);
            values.put("sum",stusum);
            values.put("avg",stuavg);

            long rowID=db.insert("Student",null,values);
            if(rowID!=-1){
                Toast.makeText(add_activity.this,"操作成功！",Toast.LENGTH_LONG).show();
            }
            db.close();
            cursor.close();
            Intent intent = new Intent(add_activity.this,MainActivity.class);
            startActivity(intent);
        }

    }
    //恢复旧数据
    private void initInfo() {
        String oldId=oldData.getStringExtra("_id");
        oldID=oldId;
        num.setText(oldId);
        String oldName=oldData.getStringExtra("name");
        name.setText(oldName);
        String oldGrade=oldData.getStringExtra("grade");
        grade.setText(oldGrade);
        int oldjz=oldData.getIntExtra("jz",0);
        jz.setText(String.valueOf(oldjz));
        int oldls=oldData.getIntExtra("ls",0);
        ls.setText(String.valueOf(oldls));
        int oldhb=oldData.getIntExtra("hb",0);
        hb.setText(String.valueOf(oldhb));
        int oldxd=oldData.getIntExtra("xd",0);
        xd.setText(String.valueOf(oldxd));
        int oldmg=oldData.getIntExtra("mg",0);
        mg.setText(String.valueOf(oldmg));
        int oldty=oldData.getIntExtra("ty",0);
        ty.setText(String.valueOf(oldty));
        int olddw=oldData.getIntExtra("dw",0);
        dw.setText(String.valueOf(olddw));
        int olddy=oldData.getIntExtra("dy",0);
        dy.setText(String.valueOf(olddy));
    }
}
