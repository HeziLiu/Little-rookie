package com.example.he.seekbartest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView t1;
    private TextView t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.sb);
        t1 = (TextView) findViewById(R.id.tv1);
        t2 = (TextView) findViewById(R.id.tv2);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                t1.setText("正在滑动");
                t2.setText("当前值："+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                t1.setText("开始滑动");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                t1.setText("滑动停止");
            }
        });

    }
}
