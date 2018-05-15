package com.example.he.advancedskill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Person person=new Person(20,"A");
        Person2 person2=new Person2();
        person2.setName("B");
        person2.setAge(22);
        Intent intent=new Intent(this,SecondActivity.class);
        intent.putExtra("person_data",person);
        intent.putExtra("person2_data",person2);
        startActivity(intent);
    }
}
