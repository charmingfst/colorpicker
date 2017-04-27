package com.chm.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.chm.myapplication.view.GestureView;

import java.util.List;

/**
 * Created by ason on 2017/1/24.
 */
public class GestureActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture);

        GestureView myView = (GestureView) findViewById(R.id.view);
        myView.setOnDrawFinishedListener(new GestureView.OnDrawFinishedListener() {
            @Override
            public boolean onDrawFinished(List<Integer> passList) {
                boolean flag = false;
                if (passList.size() <= 3) {
                    Toast.makeText(GestureActivity.this, "图案绘制有误!", Toast.LENGTH_SHORT).show();
                    flag = false;
                }else {
                    Toast.makeText(GestureActivity.this, "图案绘制完成!", Toast.LENGTH_SHORT).show();
                    flag = true;
                }

                return flag;
            }
        });
    }
}
