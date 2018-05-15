package com.example.he.contentprovidertest;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by he on 2016/12/28.
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String  DB_NAME="book_provider.db";
    public static final String BOOK_TABLE_NAME="book";
    public static final String USER_TABLE_NAME="user";

    private static final int DB_VERSION=1;

    private String CREATE_BOOK_TABLE="CREATE TABLE IF NOT EXISTS "+BOOK_TABLE_NAME+"(_id INTEGER PRIMARY KEY,"+"name TEXT)";
    private String CREATE_USER_TABLE="CREATE TABLE IF NOT EXISTS "+ USER_TABLE_NAME+"(_id INTEGER PRIMARY KEY,"+"name TEXT,"+"sex TEXT)";


    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }


    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO:在这里进行数据库的升级操作
    }
}
