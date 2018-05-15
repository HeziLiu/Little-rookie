package tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 78914 on 2017/6/12.
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    private static MyOpenHelper instance;
    public static final String CREATE_RIDER="create table rider(id integer primary key autoincrement, name text,password text)";
    public static final String CREATE_STUDENT="create table student(id integer primary key autoincrement,num text,name text,password text)";
    //构建学生表和员工表（主要操作的表项 员工：姓名，密码 学生：姓名，密码，寝室号）
    public static final String CREATE_INFO="create table info(_id integer primary key autoincrement,title text,content text,time text)";
    //构建公告表（标题，内容，时间）
    public static final String CREATE_ORDER="create table orderlist(_id integer primary key autoincrement,RoomNum text,month text,day text)";
    //构建已完成订单表（寝室号，月，日）
    public static final String CREATE_FINISH_ORDER="create table orderlistPaid(_id integer primary key autoincrement,RoomNum text,month text,day text)";
    //构建待付款订单表（寝室号，月，日）
    public static final String CREATE_UNPAID_ORDER="create table orderlistUnPaid(_id integer primary key autoincrement,RoomNum text,month text,day text)";
    private MyOpenHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RIDER);
        db.execSQL(CREATE_STUDENT);
        //执行sql语句
        db.execSQL(CREATE_INFO);
        db.execSQL(CREATE_ORDER);
        db.execSQL(CREATE_FINISH_ORDER);
        db.execSQL(CREATE_UNPAID_ORDER);
        //对相应表各列进行操作
        db.execSQL("alter table student add  column ranking integer");
        db.execSQL("alter table info add  column ranking integer");
        db.execSQL("alter table orderlist add  column ranking integer");
        db.execSQL("alter table orderlistPaid add  column ranking integer");
        db.execSQL("alter table orderlistUnPaid add  column ranking integer");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion==1){
            //对相应表进行更新
            db.execSQL("alter table student add  column ranking integer");
            db.execSQL("alter table info add  column ranking integer");
            db.execSQL("alter table orderlist add  column ranking integer");
            db.execSQL("alter table orderlistPaid add  column ranking integer");
            db.execSQL("alter table orderlistUnPaid add  column ranking integer");
        }
    }
    public static MyOpenHelper getInstance(Context context){
        if (instance==null){
            //静态构造函数获取当前数据库版本号，及时更新表
            instance=new MyOpenHelper(context,"StudentManagement.db",null,2);
            instance=new MyOpenHelper(context,"InfoManagement.db",null,2);
            instance=new MyOpenHelper(context,"OrderlistMangement.db",null,2);
            instance=new MyOpenHelper(context,"OrderlistPaidMangement.db",null,2);
            instance=new MyOpenHelper(context,"OrderlistUnpaidMangement.db",null,2);
        }
        return instance;
    }
}
