package com.chm.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chm.myapplication.R;

import java.util.ArrayList;

/**
 * Created by ason on 2016/12/26.
 */
public class RecyclerViewActivity extends Activity {
    private ArrayList<String> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("this is "+i);
        }
        RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.myrecyclerview);
        //设置adapter
        myRecyclerView.setAdapter(new MyRecyclerViewAdapter(this, datas));
        //设置layoutManager
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
    }
}
