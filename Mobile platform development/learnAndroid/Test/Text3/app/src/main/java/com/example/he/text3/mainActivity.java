package com.example.he.text3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * 使用Bundle和Intent结合传递数据
 * Created by he on 2016/3/22.
 */
public class mainActivity extends Activity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first_layout);
        Button button = (Button) findViewById(R.id.first_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("bundle_data", "hello");
                intent = new Intent(mainActivity.this, secondActivity.class);
                intent.putExtra("intent_data", bundle);
                startActivity(intent);
            }
        });
    }

}

