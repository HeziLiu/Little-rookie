package com.example.he.fragmenttest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements Listener {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.left_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                another_right_fragment fragment = new another_right_fragment();
                Bundle bundle = new Bundle();
                bundle.putString("info", "info is ok");
                fragment.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.right_, fragment);
                // Fragment f = manager.findFragmentById(R.id.another_fragment_text);

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }


    @Override
    public void thanks(String code) {
        Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
    }
}
