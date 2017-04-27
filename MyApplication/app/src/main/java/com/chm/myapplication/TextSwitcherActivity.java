package com.chm.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.chm.myapplication.R;

import java.util.AbstractCollection;

/**
 * Created by ason on 2016/12/29.
 */
public class TextSwitcherActivity extends Activity {
    TextSwitcher textSwitcher;
    private BitHandler bitHandler;
    private String[] strings={"text00001","text00002"};
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_switcher);
        textSwitcher = (TextSwitcher) findViewById(R.id.profileSwitcher);
        test();
    }

    private void test(){
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(TextSwitcherActivity.this);
                textView.setSingleLine();
                textView.setTextSize(15);
                textView.setTextColor(Color.parseColor("#ff0000"));
                textView.setEllipsize(TextUtils.TruncateAt.END);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                );
                lp.gravity = Gravity.CENTER;
                textView.setLayoutParams(lp);
                return textView;
            }
        });
        bitHandler = new BitHandler();
        new myThread().start();
    }

    class BitHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textSwitcher.setText(strings[index]);
            index++;
            if (index == strings.length) {
                index = 0;
            }
        }
    }

    private class myThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (index < strings.length) {
                try {
                    synchronized (this) {
                        bitHandler.sendEmptyMessage(0);
                        this.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
