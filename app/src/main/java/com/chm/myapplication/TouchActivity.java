package com.chm.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chm.myapplication.entity.AsyncMessage;
import com.chm.myapplication.entity.BackgroundMessage;
import com.chm.myapplication.entity.MainMessage;
import com.chm.myapplication.entity.Message;
import com.chm.myapplication.entity.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ason on 2017/1/11.
 */
public class TouchActivity extends Activity implements View.OnClickListener{
    Button btn1, btn2, btn3,btn4;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toggle);

        EventBus.getDefault().register(this);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        textView = (TextView) findViewById(R.id.info);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMain(MainMessage message)
    {
        textView.setText(message.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackground(BackgroundMessage msg)
    {
        textView.setText(msg.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onAsync(AsyncMessage msg)
    {
        textView.setText(msg.getMessage());
    }

    /**
     * 和发送事件在同一线程，默认方式
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPosting(Message msg)
    {
        textView.setText(msg.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn1:
                EventBus.getDefault().post(new MainMessage("hello main"));
                break;
            case R.id.btn2:
                EventBus.getDefault().post(new BackgroundMessage("hello background"));
                break;
            case R.id.btn3:
                EventBus.getDefault().post(new AsyncMessage("hello aysnc"));
                break;
            case R.id.btn4:
                EventBus.getDefault().postSticky(new MessageEvent("hello sticky"));
                Intent _intent = new Intent();
                _intent.setClass(TouchActivity.this, StickyActivity.class);
                startActivity(_intent);
                break;
        }
    }

    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        super.onTouchEvent(event);
//        switch (event.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//                System.out.println("action down");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                System.out.println("action move");
//                break;
//            case MotionEvent.ACTION_UP:
//                System.out.println("action up");
//                break;
//        }
//
//        return true;
//    }
}
