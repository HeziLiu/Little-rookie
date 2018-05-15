package com.example.he.databasetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**自定义内容提供器
 * Created by he on 2016/4/19.
 */
public class DatabaseProvider extends ContentProvider {
    private static final int BOOK_DIR = 0;
    private static final int BOOK_ITEM = 1;
    private static final int CATEGORY_DIR = 2;
    private static final int CATEGORY_ITEM = 3;

    private static final String AUTHORRTY = "com.example.he.databasetest.provider";//Uri权限

    private static UriMatcher matcher;
    private MyDatabaseHelper dbhelper;

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORRTY, "Book", BOOK_DIR);         //表中的所有数据
        matcher.addURI(AUTHORRTY, "Book/#", BOOK_ITEM);      //表中任意一行数据
        matcher.addURI(AUTHORRTY, "Category", CATEGORY_DIR);//表中的所有数据
        matcher.addURI(AUTHORRTY, "Category/#", CATEGORY_ITEM);//表中任意一行数据
    }


    @Override
    public boolean onCreate() {
        dbhelper = new MyDatabaseHelper(getContext(), "Book.db", null, 2);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = null;
        switch (matcher.match(uri)) {
            case BOOK_DIR:
                cursor = db.query("Book", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);//获取id
                cursor = db.query("Book", projection, "id=?", new String[]{bookId}, null, null, sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = db.query("Category", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);//获取id
                cursor = db.query("Book", projection, "id=?", new String[]{categoryId}, null, null, sortOrder);
                break;
            default:
                break;

        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.he.databasetest.provider.book";

            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.he.databasetest.provider.book";

            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.he.databasetest.provider.category";

            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.he.databasetest.provider.category";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (matcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITEM:
                long book_id = db.insert("Book", null, values);
                uriReturn = Uri.parse("content://" + AUTHORRTY + "/Book/" + book_id);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long category_id = db.insert("Category", null, values);
                uriReturn = Uri.parse("content://" + AUTHORRTY + "/Category/" + category_id);
                break;
        }

        return uriReturn;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int deleteRows = 0;
        switch (matcher.match(uri)) {
            case BOOK_DIR:
                deleteRows = db.delete("Book", selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String deleteId = uri.getPathSegments().get(1);
                deleteRows = db.delete("Book", "id=?", new String[]{deleteId});
                break;
            case CATEGORY_DIR:
                deleteRows = db.delete("Category", selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String delId = uri.getPathSegments().get(1);
                deleteRows = db.delete("Category", "id=?", new String[]{delId});
                break;
        }

        return deleteRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int updateRows = 0;
        switch (matcher.match(uri)) {
            case BOOK_DIR:
                updateRows = db.update("Book", values, selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String upId = uri.getPathSegments().get(1);
                updateRows = db.update("Book", values, "id=?", new String[]{upId});
                break;
            case CATEGORY_DIR:
                updateRows = db.update("Category", values, selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String upcId = uri.getPathSegments().get(1);
                updateRows = db.update("Category", values, "id=?", new String[]{upcId});
                break;

        }
        return updateRows;
    }
}
