package com.chm.myapplication.utils;

import android.content.Context;

/**
 * Created by ason on 2017/4/20.
 */

public class SystemUtils {
    /**
     * 根据手机的分辨率从dp的单位转成为px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从px的单位转成为dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
