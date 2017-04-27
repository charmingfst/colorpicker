package com.chm.myapplication.minatest;

import android.content.Context;

/**
 * Created by ason on 2017/1/22.
 */
public class ConnectionConfig {
    private Context context;
    private String ip;
    private int port;
    private int readBufferSize;
    private long connectionTimeout;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }

    public void setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    //构建者模式
    public static class Builder{
        private Context context;
        private String ip="10.90.24.139";
        private int port=9123;
        private int readBufferSize=10240;
        private long connectionTimeout=10000;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setReadBufferSize(int readBufferSize) {
            this.readBufferSize = readBufferSize;
            return this;
        }

        public Builder setConnectionTimeout(long connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        private void applyConfig(ConnectionConfig config)
        {
            config.context = this.context;
            config.ip = this.ip;
            config.port = this.port;
            config.readBufferSize = readBufferSize;
            config.connectionTimeout = this.connectionTimeout;
        }
        public ConnectionConfig builder()
        {
            ConnectionConfig config = new ConnectionConfig();
            applyConfig(config);
            return config;
        }
    }

}
