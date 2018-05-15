package com.example.he.kotlindemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

/**
 * 直接创建KotlinActivity并编写Kotlin代码
 */
class SecondActivity : AppCompatActivity() {
    val ACTION = "com.he.example.TTT"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val  go_:Button = findViewById(R.id.go) as Button
        go_.setOnClickListener {
            val intent:Intent
            intent = Intent(ACTION)
            startActivity(intent)
        }
    }
}
