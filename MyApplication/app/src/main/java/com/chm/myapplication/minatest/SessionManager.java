package com.chm.myapplication.minatest;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;

/**
 * Created by ason on 2017/1/22.
 */
public class SessionManager {
    private static SessionManager mInstance = null;
    //最终与服务器进行通信的对象
    private IoSession mSession;
    public static SessionManager getInstance() {
        if (mInstance == null)
        {
            synchronized (SessionManager.class) {
                if (mInstance == null) {
                    mInstance = new SessionManager();
                }
            }
        }
        return mInstance;
    }

    public void setSeesion(IoSession session){
        this.mSession = session;
    }
    public SessionManager() {
    }

    public SessionManager(IoSession mSession) {
        this.mSession = mSession;
    }

    /**
     * 将对象写到服务器
     * @param msg
     */
    public void writeToServer(Object msg)
    {
        if (mSession != null) {
            WriteFuture future = mSession.write(msg);
            future.awaitUninterruptibly();
            if (future.isWritten()) //
            {
                //数据已成功发送
            }else
            {
                //数据发送失败
            }
        }
    }

    public void closeSession()
    {
        if (mSession != null)
            mSession.closeOnFlush();
    }
    public void removeSession()
    {
        this.mSession = null;
    }
}
