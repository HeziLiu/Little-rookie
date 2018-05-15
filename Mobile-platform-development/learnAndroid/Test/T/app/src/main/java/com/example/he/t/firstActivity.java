package com.example.he.t;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by he on 2016/3/15.
 */
public class firstActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);//隐藏标题栏
        setContentView(R.layout.firstlayout);

        Button botton1 = (Button) findViewById(R.id.button1);
        //按钮监听
        botton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(firstActivity.this, "click button", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
                Intent intent=new Intent(firstActivity.this,secondActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_menu:
                Toast.makeText(this, "you clicked add", Toast.LENGTH_SHORT).show();
            break;
            case R.id.remove_menu:
                Toast.makeText(this, "you clicked remove", Toast.LENGTH_SHORT).show();
            break;
            default:
        }
        return  true;
    }
}
