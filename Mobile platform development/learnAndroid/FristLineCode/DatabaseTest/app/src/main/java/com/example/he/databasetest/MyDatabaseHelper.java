package com.example.he.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by he on 2016/4/15.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    //创建数据库语句
    private static final String CREATE_BOOK = "create table Book(" +
            "id integer primary key autoincrement, " +
            " author text," +
            " price real, " +
            "pages integer, " +
            "name text)";

    //新创建的数据库语句
    private static final String CREATE_CATEGORY = "create table Category(" +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果存在删除表
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);

    }
}
