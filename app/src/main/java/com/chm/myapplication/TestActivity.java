package com.chm.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.chm.myapplication.R;

/**
 * Created by ason on 2016/12/26.
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.collapsingtoolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Snackbar.make(view,"hello world",Snackbar.LENGTH_LONG)
//                        .setAction("cancel", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //这里的单击事件代表点击消除Action后的响应事件
//                                Toast.makeText(TestActivity.this, "点击cancel", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .show();
//            }
//        });
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                System.out.println("aaa:"+Thread.currentThread().getName());
            }
        }, 3000);

    }
}
