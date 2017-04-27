package com.chm.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.chm.myapplication.view.CircleSeekBar;

/**
 * Created by ason on 2017/3/27.
 */

public class CircleSeekBarActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.circle_progress);

        CircleSeekBar circleSeekBar = (CircleSeekBar) findViewById(R.id.circle_seekbar);
        circleSeekBar.setProgress(100);
        circleSeekBar.setProgressFrontColor(Color.RED);
        //circleSeekBar.setProgressWidth(20);
    }

}
