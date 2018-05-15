package com.example.he.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    private MyDatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);
        databaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        //创建数据库
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(this);
        //添加数据
        findViewById(R.id.add_data).setOnClickListener(this);
        //更新数据
        findViewById(R.id.up_data).setOnClickListener(this);
        //删除数据
        findViewById(R.id.delete_data).setOnClickListener(this);
        //查询数据
        findViewById(R.id.select_data).setOnClickListener(this);
        //使用事务替换数据
        findViewById(R.id.replace_data).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_database:
                databaseHelper.getWritableDatabase();
                break;
            case R.id.add_data:
                db = databaseHelper.getWritableDatabase();
                values = new ContentValues();
                values.put("name", "First");
                values.put("author", "A");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values);
                values.clear();//清空values中的数据
                values.put("name", "Second");
                values.put("author", "B");
                db.insert("Book", null, values);
                Toast.makeText(this, "add data success", Toast.LENGTH_SHORT).show();
                break;
            case R.id.up_data:
                db = databaseHelper.getWritableDatabase();
                values = new ContentValues();
                values.put("price", 10.99);
                //将书名为First的价格更新
                db.update("Book", values, "name=?", new String[]{"First"});
                Toast.makeText(this, "up data success", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_data:
                db = databaseHelper.getWritableDatabase();
                //删除id为3的行
                db.delete("Book", "id=?", new String[]{"3"});
                Toast.makeText(this, "delete data success", Toast.LENGTH_SHORT).show();
                break;
            case R.id.select_data:
                db = databaseHelper.getWritableDatabase();
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));

                        Toast.makeText(this, "查询的数据：" + "\n" + "name:" +
                                name + "\n" + "author:" + author + "\n", Toast.LENGTH_SHORT).show();
                    } while (cursor.moveToNext());
                }
                cursor.close();//关闭
                break;

            case R.id.replace_data:
                db = databaseHelper.getWritableDatabase();
                db.beginTransaction();//开启事务
                try {
                    db.delete("Book", null, null);//将表中的数据全部删除
                    ContentValues values = new ContentValues();
                    values.put("name", "Thread");
                    values.put("author", "C");
                    values.put("pages", 454);
                    values.put("price", 80.00);
                    db.insert("Book", null, values);
                    db.setTransactionSuccessful();//事务执行成功
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.endTransaction();//关闭事务
                }
                break;
        }
    }
}
