package cqupt.ui.mygrades;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import tools.MyOpenHelper;

import static cqupt.ui.mygrades.R.id.etFindStudentId;
import static cqupt.ui.mygrades.R.id.name_fuzzy;
import static cqupt.ui.mygrades.R.id.postive;

/**
 * Created by 78914 on 2017/6/3.
 */
//特定查找、删除等模块，通过加载各个对话框界面实现逻辑功能
public class select_activity extends Activity {
    private ListView lstStudent;
    private MyOpenHelper helper;
    private Dialog dlg;
    private String id="-1";//赋初值-1，id不可能为-1
    private Cursor cursor;
    private String name=null;
    private String grade=null;
    private FloatingActionButton btn;
    private MyApplication application;//用于按两次退出

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list);
        application=MyApplication.getInstance();
        application.addActivity(select_activity.this);
        //关联
        lstStudent= (ListView) findViewById(R.id.list_student);
        //当点击FloatActionButton时调用查找对话框的创建方法
        btn= (FloatingActionButton) findViewById(R.id.flb);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
        helper=MyOpenHelper.getInstance(this);
        displayAllStudent();//显示list view的信息
        itemClick();        //list view的项点击事件
    }
    Dialog dialog;//用于精确查找的提示对话框
    Dialog dialog1;//用于删除警告
    Dialog dialog2;//用于关闭精确查找对话框
    Dialog dialog3;//用于关闭模糊查找对话框
    Dialog dialog4;//用于关闭分数段查找对话框
    private void itemClick() {
        lstStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击后加载opertor对话框提示要进行的操作
                final AlertDialog.Builder builder=new AlertDialog.Builder(select_activity.this);
                LayoutInflater factory=LayoutInflater.from(select_activity.this);
                final View textEntryView=factory.inflate(R.layout.opertor,null);
                builder.setView(textEntryView);
                final Button delete= (Button) textEntryView.findViewById(R.id.delete);
                //删除时弹出warning对话框提示谨慎操作
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder1=new AlertDialog.Builder(select_activity.this);
                        LayoutInflater layoutInflater=LayoutInflater.from(select_activity.this);
                        final View warning=layoutInflater.inflate(R.layout.warning,null);
                        builder1.setView(warning);
                        final  Button sure= (Button) warning.findViewById(R.id.sure);
                        final  Button not= (Button) warning.findViewById(R.id.not);
                        //确定之后再调用删除方法并消除两层对话框
                        sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteStudent();
                                dialog1.dismiss();
                                dialog.dismiss();
                            }
                        });
                        //取消返回上级对话框
                        not.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });
                        dialog1=builder1.create();
                        dialog1.show();

                    }
                });
                final Button update= (Button) textEntryView.findViewById(R.id.update);
                //修改操作调用updateStudent()并使对话框消失
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateStudent();
                        dialog.dismiss();
                    }
                });
                dialog=builder.create();
                dialog.show();
            }
        });
    }
    //利用Intent传值修改
    private void updateStudent() {
        Intent intent=new Intent(select_activity.this,add_activity.class);
        intent.putExtra("haveData","true");
        intent.putExtra("_id",cursor.getString(cursor.getColumnIndex("_id")));
        intent.putExtra("name",cursor.getString(cursor.getColumnIndex("name")));
        intent.putExtra("grade",cursor.getString(cursor.getColumnIndex("grade")));
        intent.putExtra("jz",cursor.getInt(cursor.getColumnIndex("jz")));
        intent.putExtra("ls",cursor.getInt(cursor.getColumnIndex("ls")));
        intent.putExtra("hb",cursor.getInt(cursor.getColumnIndex("hb")));
        intent.putExtra("xd",cursor.getInt(cursor.getColumnIndex("xd")));
        intent.putExtra("mg",cursor.getInt(cursor.getColumnIndex("mg")));
        intent.putExtra("ty",cursor.getInt(cursor.getColumnIndex("ty")));
        intent.putExtra("dw",cursor.getInt(cursor.getColumnIndex("dw")));
        intent.putExtra("dy",cursor.getInt(cursor.getColumnIndex("dy")));
        startActivity(intent);
    }
    //删除学生信息
    private void deleteStudent() {
        String id=cursor.getString(cursor.getColumnIndex("_id"));
        SQLiteDatabase db=helper.getWritableDatabase();
        //delete方法返回影响的行数
        int line=db.delete("Student","_id=?",new String[]{id});
        if(line>0){
            Toast.makeText(select_activity.this,"数据删除成功",Toast.LENGTH_LONG).show();
        }

        displayAllStudent();
        db.close();
    }
    //创建对话框
    private void createDialog() {
        //解析
        View view = LayoutInflater.from(select_activity.this).inflate(R.layout.options, null);
        final Button exact_locate = (Button) view.findViewById(R.id.exact_locate);
        final Button fuzzy_locate = (Button) view.findViewById(R.id.fuzzy_locate);
        final Button score_locate = (Button) view.findViewById(R.id.score_locate);
        dlg = new AlertDialog.Builder(select_activity.this)
                .setView(view)
                .create();
        dlg.show();
        //精确查找
        exact_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示自定义对话框
                View view1=LayoutInflater.from(select_activity.this).inflate(R.layout.dialog_view,null);
                final EditText etFindStudentId= (EditText) view1.findViewById(R.id.etFindStudentId);
                final EditText etFindStudentName= (EditText) view1.findViewById(R.id.etFindStudentName);
                final EditText etFindStudentGrade= (EditText) view1.findViewById(R.id.etFindStudentGrade);
                final Button postive= (Button) view1.findViewById(R.id.postive);
                final Button negative= (Button) view1.findViewById(R.id.negative);
                dialog2=new AlertDialog.Builder(select_activity.this)
                        .setView(view1)
                        .create();
                dialog2.show();
                //确定按钮点击事件
                postive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //得到输入的id，name,grade
                        id=etFindStudentId.getText().toString().trim();
                        name=etFindStudentName.getText().toString().trim();
                        grade=etFindStudentGrade.getText().toString().trim();
                        Log.d("idValue---",id);
                        Log.d("nameValue---",name);
                        Log.d("gradeValue---",grade);//测试传数据的日志显示
                        findStudentbyId(id,name,grade);
                        dialog2.dismiss();
                        dlg.dismiss();
                    }
                });
                //取消按钮点击事件
                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id="-1";//无操作就恢复id的初值
                        dialog2.dismiss();
                    }
                });
            }
        });
        //模糊查找
        fuzzy_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view2=LayoutInflater.from(select_activity.this).inflate(R.layout.name_fuzzy,null);
                final EditText name_fuzzy= (EditText) view2.findViewById(R.id.name_fuzzy);
                final Button name_sure= (Button) view2.findViewById(R.id.name_sure);
                final Button name_not= (Button) view2.findViewById(R.id.name_not);
                dialog3=new AlertDialog.Builder(select_activity.this)
                        .setView(view2)
                        .create();
                dialog3.show();
                //确定按钮点击事件
                name_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String namef=name_fuzzy.getText().toString().trim();
                        cursor=helper.getReadableDatabase().query("Student",null,"name like ?",
                                new String[]{"%"+namef+"%"},
                                null,
                                null,
                                "sum desc",
                                null);
                        if (cursor.moveToNext()){
                            //找到
                            //显示在对应的布局文件list_student当中
                            SimpleCursorAdapter adapter=new SimpleCursorAdapter(select_activity.this,R.layout.student_item,cursor,
                                    new String[]{"_id","name","grade","jz","ls","hb","xd","mg","ty","dw","dy","sum","avg"},
                                    new int[]{R.id.num,R.id.name,R.id.grade,R.id.jz,R.id.ls,R.id.hb,R.id.xd,R.id.mg,R.id.ty,
                                            R.id.dw,R.id.dy,R.id.sum,R.id.avg}
                            );
                            lstStudent.setAdapter(adapter);

                        }else{
                            Toast.makeText(select_activity.this,"数据没有找到...",Toast.LENGTH_LONG).show();
                        }
                        helper.close();
                        dialog3.dismiss();
                        dlg.dismiss();
                    }
                });
                //取消按钮点击事件
                name_not.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id="-1";
                        dialog3.dismiss();
                    }
                });
            }
        });
        //分数段查找
        score_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view3=LayoutInflater.from(select_activity.this).inflate(R.layout.score_limit,null);
                final EditText low= (EditText) view3.findViewById(R.id.low);
                final EditText high= (EditText) view3.findViewById(R.id.high);
                final Button score_sure= (Button) view3.findViewById(R.id.slsure);
                final Button score_not= (Button) view3.findViewById(R.id.slnot);
                dialog4=new AlertDialog.Builder(select_activity.this)
                        .setView(view3)
                        .create();
                dialog4.show();
                //确定按钮点击事件
                score_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String llow=low.getText().toString().trim();
                        String hhigh=high.getText().toString().trim();
                        //直接在后面给new String数组里的变量时出现传不进sql字段通配符 ？
                        //所以直接把变量注入到语句中
                        cursor=helper.getReadableDatabase().query("Student",null,"avg>"+llow+" and avg<"+hhigh,
                                null,
                                null,
                                null,
                                "sum desc",
                                null);
                        if (cursor.moveToNext()){
                            //找到
                            //显示在对应的布局文件list_student当中
                            SimpleCursorAdapter adapter=new SimpleCursorAdapter(select_activity.this,R.layout.student_item,cursor,
                                    new String[]{"_id","name","grade","jz","ls","hb","xd","mg","ty","dw","dy","sum","avg"},
                                    new int[]{R.id.num,R.id.name,R.id.grade,R.id.jz,R.id.ls,R.id.hb,R.id.xd,R.id.mg,R.id.ty,
                                            R.id.dw,R.id.dy,R.id.sum,R.id.avg}
                            );
                            lstStudent.setAdapter(adapter);

                        }else{
                            Toast.makeText(select_activity.this,"数据没有找到...",Toast.LENGTH_LONG).show();
                        }
                        helper.close();
                        dialog4.dismiss();
                        dlg.dismiss();

                    }
                });
                //取消按钮点击事件
                score_not.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id="-1";
                        dialog4.dismiss();
                    }
                });

            }
        });
    }
    //精确查询学生
    private void findStudentbyId(String id, String name, String grade) {
        cursor=helper.getReadableDatabase().query("Student",null,"(_id=?) or (name=?) or (grade=?)",
                new String[]{id,name,grade},//若要实现name的模糊匹配
                null,                       //使用"%"+name+"%"时
                null,                       //会把id和grade的查询屏蔽了。。。
                "sum desc",                 //查找分数段的时候两个数据传不进sql语句中
                null);                      //调试log日志的时候值都是传正确的就是运行不到sql语句中/(ㄒoㄒ)/~~
        if (cursor.moveToNext()){
            //找到
            //显示在对应的布局文件list_student当中
            SimpleCursorAdapter adapter=new SimpleCursorAdapter(select_activity.this,R.layout.student_item,cursor,
                    new String[]{"_id","name","grade","jz","ls","hb","xd","mg","ty","dw","dy","sum","avg"},
                    new int[]{R.id.num,R.id.name,R.id.grade,R.id.jz,R.id.ls,R.id.hb,R.id.xd,R.id.mg,R.id.ty,
                            R.id.dw,R.id.dy,R.id.sum,R.id.avg}
            );
            lstStudent.setAdapter(adapter);

        }else{
            Toast.makeText(select_activity.this,"数据没有找到...",Toast.LENGTH_LONG).show();
        }
        helper.close();
    }
    //加载所有学生排名信息
    private void displayAllStudent() {
        cursor=helper.getReadableDatabase().query("Student",null,null,null,null,null,"sum desc",null);
        if (cursor.moveToNext()){
            SimpleCursorAdapter adapter=new SimpleCursorAdapter(select_activity.this,R.layout.student_item,cursor,
                    new String[]{"_id","name","grade","jz","ls","hb","xd","mg","ty","dw","dy","sum","avg"},
                    new int[]{R.id.num,R.id.name,R.id.grade,R.id.jz,R.id.ls,R.id.hb,R.id.xd,R.id.mg,R.id.ty,
                            R.id.dw,R.id.dy,R.id.sum,R.id.avg});
            lstStudent.setAdapter(adapter);

        }
        helper.close();
    }

}
