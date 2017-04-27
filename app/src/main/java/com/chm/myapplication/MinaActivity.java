package com.chm.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chm.myapplication.minatest.SessionManager;
import com.chm.myapplication.service.MinaService;

/**
 * Created by ason on 2017/1/22.
 */
public class MinaActivity extends Activity implements View.OnClickListener{
    private MessageBroadcastReceiver receiver = new MessageBroadcastReceiver();
    private Button btn1, btn2;
    private TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mina_test);
        message = (TextView) findViewById(R.id.message);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        registerBroadcast();
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter("com.commonlibrary.mina");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    private void unregisterBroadcast()
    {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MinaService.class));
        unregisterBroadcast();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn1:
                SessionManager.getInstance().writeToServer("123");
                break;
            case R.id.btn2:
                Intent intent = new Intent(this, MinaService.class);
                startService(intent);
                break;
        }
    }


    private class MessageBroadcastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            message.setText(intent.getStringExtra("message"));
        }
    }
}
