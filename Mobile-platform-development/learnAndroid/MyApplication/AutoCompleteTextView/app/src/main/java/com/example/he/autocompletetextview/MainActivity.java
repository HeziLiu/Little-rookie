package com.example.he.autocompletetextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * autoCompleteTextView 控件 demo
 */
public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView textView;
    private MultiAutoCompleteTextView textView2;
    private ArrayAdapter<String> adapter;
    private List<String> list;
    private ToggleButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (AutoCompleteTextView) findViewById(R.id.t1);
        textView2 = (MultiAutoCompleteTextView) findViewById(R.id.t2);
        button = (ToggleButton) findViewById(R.id.tb1);
        list = new ArrayList<String>();
        list.add("shanghai1");
        list.add("shanghai2");
        list.add("beijing");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        textView.setAdapter(adapter);
        textView2.setAdapter(adapter);
        textView2.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //isChecked?
                Toast.makeText(MainActivity.this, isChecked ? "开" : "关", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
