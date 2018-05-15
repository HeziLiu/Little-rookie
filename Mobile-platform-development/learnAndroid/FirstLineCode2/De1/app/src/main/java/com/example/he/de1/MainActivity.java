package com.example.he.de1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

                View view = null;
                if (view == null) {
                    AppCompatDelegate d = getDelegate();
                    view = d.createView(parent, name, context, attrs);
                }
                if (name.equals("TextView")) {
                    Button button = new Button(context, attrs);
                    return button;
                }


                return view;
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



}
