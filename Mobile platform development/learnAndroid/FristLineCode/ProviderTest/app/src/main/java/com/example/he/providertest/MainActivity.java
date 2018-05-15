package com.example.he.providertest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    private String newID;
    private static final String UriPath = "content://com.example.he.databasetest.provider/Book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);

        findViewById(R.id.add_data).setOnClickListener(this);
        findViewById(R.id.query_data).setOnClickListener(this);
        findViewById(R.id.up_data).setOnClickListener(this);
        findViewById(R.id.delete_data).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_data:
                ContentValues values = new ContentValues();
                values.put("name", "A Clash of Kings");
                values.put("Author", "ZCC");
                values.put("pages", 1024);
                values.put("price", 20.5);
                Uri add_uri = Uri.parse(UriPath);
                Uri newUri = getContentResolver().insert(add_uri, values);
                newID = newUri.getPathSegments().get(1);
                Toast.makeText(v.getContext(), "add data success", Toast.LENGTH_SHORT).show();
                break;

            case R.id.query_data:
                Uri query_uri = Uri.parse(UriPath);
                //查询新添加的数据
                if (newID!=null){
                    Cursor cursor = getContentResolver().query(query_uri, null, "id=?", new String[]{newID}, null);
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            String author = cursor.getString(cursor.getColumnIndex("author"));
                            String price = cursor.getString(cursor.getColumnIndex("price"));

                            Toast.makeText(v.getContext(), "name:" + name + "\n" + "author:" + author + "\n" + "price:" + price, Toast.LENGTH_SHORT).show();
                        }
                        cursor.close();
                    }
                    else {
                        Toast.makeText(v.getContext(),"no find data",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(v.getContext(),"no find data",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.up_data:
                if (newID!=null){
                    Uri up_uri = Uri.parse(UriPath + "/" + newID);
                    ContentValues up_values = new ContentValues();
                    up_values.put("name", "A Storm of Swords");
                    up_values.put("price", 40.4);
                    getContentResolver().update(up_uri, up_values, null, null);
                    Toast.makeText(v.getContext(), "up data success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(v.getContext(),"you should first add a piece of data",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.delete_data:
                Uri del_uri=Uri.parse(UriPath+"/"+newID);
                getContentResolver().delete(del_uri,null,null);
                Toast.makeText(v.getContext(), "delete data success", Toast.LENGTH_SHORT).show();
                break;

        }

    }
}
