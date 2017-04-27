package com.chm.myapplication.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.chm.myapplication.R
import kotlinx.android.synthetic.main.activity_kotlin.*
class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        hello.setText("hello kotlin")
        hello.setOnClickListener { Toast.makeText(this@KotlinActivity, "${it.javaClass.simpleName}", Toast.LENGTH_SHORT).show() }

    }

}
