package com.example.he.syncadapterdemo.SyncAdpaterModule;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * 第三步：
 *  添加一个 Stub Content Provider
 *  SyncAdapter 框架是设计成用来和设备数据一起工作的，
 * 而这些设备数据应该被灵活且安全的 Content Provider 框架管理。
 * 因此，SyncAdapter 框架会期望应用已经为它的本地数据定义了Content Provider
 *
 * Created by he on 2017/5/4.
 */

public class StubProvider extends ContentProvider {
    /*
     * Always return true, indicating that the
     * provider loaded correctly.
     */
    @Override
    public boolean onCreate() {
        return true;
    }


    /*
     * query() always returns no results
     *
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

     /*
     * Return an empty String for MIME type
     */

    @Nullable
    @Override
    public String getType(Uri uri) {
        return new String();
    }


    /*
    * insert() always returns null (no URI)
    */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    /*
    * delete() always returns "no rows affected" (0)
    */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    /*
     * update() always returns "no rows affected" (0)
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
