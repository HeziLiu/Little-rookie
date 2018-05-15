package com.example.he.contentprovidertest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by he on 2016/12/28.
 */

public class BookProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.he.contentprovidertest.provider";
    private static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    private static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    private static final int BOOK_URI_CODE = 0;
    private static final int USER_URI_CODE = 1;

    private static final UriMatcher mMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {
        mMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        mMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;


    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
        }
        return tableName;
    }

    private void initProviderData(){
        mDb=new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from "+DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from "+DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3,'Android');");
        mDb.execSQL("insert into book values(4,'Ios');");
        mDb.execSQL("insert into book values(5,'Html5');");
        mDb.execSQL("insert into user values(1,'jake',1);");
        mDb.execSQL("insert into user values(2,'jasmine',0);");

    }


    //只有该方法运行在主线程中
    @Override
    public boolean onCreate() {
        mContext=getContext();
        initProviderData();
        return true;
    }


    //下面的五个方法由客户端回调且都运行在客户端的Binder线程池中
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String table=getTableName(uri);
        if(table==null)
            throw  new IllegalArgumentException();
        return mDb.query(table,projection,selection,selectionArgs,null,null,sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        mDb.insert(table, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int count = mDb.delete(table, selection, selectionArgs);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int row = mDb.update(table, values, selection, selectionArgs);
        if (row > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;
    }
}
