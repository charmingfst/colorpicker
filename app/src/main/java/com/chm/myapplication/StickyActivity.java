package com.chm.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.chm.myapplication.entity.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

/**
 * Created by ason on 2017/1/19.
 */
public class StickyActivity extends Activity{
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticky_message);
        mTextView = (TextView) findViewById(R.id.msg);
        EventBus.getDefault().register(this);
    }


    // UI updates must run on MainThread
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        mTextView.setText(event.getMessage());
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
