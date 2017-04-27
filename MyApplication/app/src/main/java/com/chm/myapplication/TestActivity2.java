package com.chm.myapplication;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.chm.myapplication.view.MyScroller;

/**
 * Created by ason on 2016/12/26.
 */
public class TestActivity2 extends AppCompatActivity {

    private TextView title1,title2,tt1,tt2;
    private MyScroller scroll;
    private CollapsingToolbarLayout aa;
    private AppBarLayout bb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_test);

        title1= (TextView) findViewById(R.id.title1);
        title2=(TextView) findViewById(R.id.title2);
        tt1=(TextView)findViewById(R.id.tt1);
        tt2=(TextView)findViewById(R.id.tt2);
        aa=(CollapsingToolbarLayout)findViewById(R.id.aa);
        bb=(AppBarLayout)findViewById(R.id.bb);

        scroll=(MyScroller)findViewById(R.id.scroll);
        title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bb.setExpanded(false);
                // scroll.mScroller.startScroll(0,0,0,(int) tt1.getY());

                scroll.scro((int)tt1.getY());
            }
        });

        title2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bb.setExpanded(false);

                // scroll.scrollTo(0,(int) tt2.getY());
                scroll.scro((int)tt2.getY());

            }
        });


    }

}
