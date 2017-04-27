package com.chm.myapplication;

import android.app.Activity;
import android.os.Bundle;

import com.chm.myapplication.entity.DataObject;
import com.chm.myapplication.view.BarChart;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by ason on 2017/4/20.
 */
public class ChartBarActivity extends Activity {

    private ArrayList<DataObject> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chartbar);

        datas = new ArrayList<>();
        Date now = new Date();
        Random rd = new Random();
        datas.add(new DataObject(new Date(now.getTime()-8640000),1500));
        for (int i = 0; i < 100; i++){
            datas.add(new DataObject(new Date(now.getTime() - 8640000*i),rd.nextInt(1500)%1500));
        }
        BarChart barChart = (BarChart) findViewById(R.id.barchart);
        barChart.setDatas(datas);
    }
}