package com.chm.myapplication.event;

/**
 * 作者：杨光福 on 2016/4/17 14:07
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：xxxx
 */
public class Log {
    public static void d(String tag, String msg) {
        android.util.Log.e(tag,msg);
    }

    public static void w(String tag, String msg) {
        android.util.Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        android.util.Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        android.util.Log.i(tag, msg);
    }

}
