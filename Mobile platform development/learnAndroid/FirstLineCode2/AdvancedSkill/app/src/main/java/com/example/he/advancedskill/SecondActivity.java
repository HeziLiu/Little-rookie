package com.example.he.advancedskill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by he on 2016/5/14.
 */
public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        Intent intent=getIntent();
        Person person= (Person) intent.getSerializableExtra("person_data");
        Person2 person2=intent.getParcelableExtra("person2_data");
        String name=person.getName();
        int age=person.getAge();
        String name2=person2.getName();
        int age2=person2.getAge();
        Toast.makeText(MyApplication.getContext(),"name:"+name+"age:"+age+"\n"+"name:"+name2+"age:"+age2+"\n",Toast.LENGTH_SHORT).show();
    }
}
