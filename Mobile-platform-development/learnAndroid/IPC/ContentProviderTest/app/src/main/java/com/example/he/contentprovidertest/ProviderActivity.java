package com.example.he.contentprovidertest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProviderActivity extends AppCompatActivity {
    private ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver= getContentResolver();
        Button book= (Button) findViewById(R.id.select_book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri bookUri=Uri.parse("content://com.example.he.contentprovidertest.provider/book");
                ContentValues values=new ContentValues();
                values.put("_id",6);
                values.put("name","Java");
                resolver.insert(bookUri,values);
                Cursor cursor=resolver.query(bookUri,null,null,null,null);
                while (cursor.moveToNext()){
                    Book b=new Book();
                    b.bookId=cursor.getInt(0);
                    b.bookName=cursor.getString(1);
                    Toast.makeText(ProviderActivity.this,b.toString(),Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        });

        Button user= (Button) findViewById(R.id.select_user);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri userUri = Uri.parse("content://com.example.he.contentprovidertest.provider/user");
                Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
                while (userCursor.moveToNext()) {
                    User user = new User();
                    user.userId = userCursor.getInt(0);
                    user.userName = userCursor.getString(1);
                    user.isMale = userCursor.getInt(2) == 1;
                    Toast.makeText(ProviderActivity.this,user.toString(),Toast.LENGTH_SHORT).show();
                }
                userCursor.close();
            }
        });

    }
}
