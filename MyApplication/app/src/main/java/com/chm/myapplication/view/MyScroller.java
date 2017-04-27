package com.chm.myapplication.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

/**
 * Created by 张林 on 2017/2/22.
 */

public class MyScroller extends NestedScrollView {
    private Scroller mScroller;

    public MyScroller(Context context) {
        super(context);
       // mScroller = new Scroller(context);
    }

    public MyScroller(Context context, AttributeSet attrs){
        super(context,attrs);
        mScroller = new Scroller(context);
    }

    public MyScroller(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
    }


    public void scro(int by){
        int a=by-mScroller.getCurrY();
    mScroller.startScroll(0,mScroller.getCurrY(),0,a);
    invalidate();
}
    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
        super.computeScroll();
    }
}
