package com.chm.myapplication.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.chm.myapplication.minatest.ConnectionConfig;
import com.chm.myapplication.minatest.ConnectionManager;

/**
 * Created by ason on 2017/1/22.
 */
public class MinaService extends Service {

    private ConnectionHandlerThread thread;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        thread = new ConnectionHandlerThread("mina", getApplicationContext());
        System.out.println("service create:");
        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.disConnection();
    }

    /**
     * 负责调用ConnectionManager
     */
    class ConnectionHandlerThread extends HandlerThread {
        private Context context;
        boolean isConnection;
        ConnectionManager mManager;

        public ConnectionHandlerThread(String name, Context context) {
            super(name);
            this.context = context;
            ConnectionConfig config = new ConnectionConfig.Builder(context)
                    .setIp("10.90.24.139").setPort(9123)
                    .setReadBufferSize(10240).setReadBufferSize(10000).builder();
            System.out.println(config.getReadBufferSize());
            mManager = new ConnectionManager(config);
        }

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            while (true) {
                isConnection = mManager.connect(); //

                if (isConnection) {
                    break;
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        public void disConnection() {
            mManager.disConnection();
        }
    }

}
