package tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 78914 on 2017/6/3.
 */
//继承于OpenHelper,方便对数据库的操作，该模块主要是创建表和更新数据库
public class MyOpenHelper extends SQLiteOpenHelper {
    private static MyOpenHelper instance;
    public static final String CREATE_STUDENT="create table Student(_id integer primary key,"+
    "name,"+
    "grade,"+
    "jz,"+"ls,"+"hb,"+"xd,"+"mg,"+"ty,"+"dw,"+"dy,"+"sum,"+"avg)";
    public MyOpenHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT);
        db.execSQL("alter table Student add  column ranking integer");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion==1){
            db.execSQL("alter table Student add  column ranking integer");
        }

    }
    //利用instance及时更新当前数据库版本
    public static MyOpenHelper getInstance(Context context){
        if (instance==null){
            instance=new MyOpenHelper(context,"Student.db",null,2);
        }
        return instance;
    }
}
