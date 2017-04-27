package com.chm.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ason on 2017/1/19.
 */
public class LetterView extends View {

    private final String[] letters = {"A", "B", "C", "D", "E","F", "G","H","I","G","K","L","M","N","O","P","Q","R", "S","T","U","V","W","X","Y","Z"};
    private Paint mPaint;
    private int itemWidth;
    private int itemHeight;

    private int touchIndex = -1;  // 手指按住的字母索引

    public LetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(40);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        itemWidth = getWidth();
        itemHeight = getHeight()/letters.length;
        mPaint.getTextBounds(letters[0], 0,1,rect);
        for (int i = 0; i < letters.length; i++) {
            if (touchIndex == i)
            {
                mPaint.setColor(Color.GRAY);
            }else {
                mPaint.setColor(Color.WHITE);
            }
            canvas.drawText(letters[i], itemWidth/2-rect.width()/2, (itemHeight/2+rect.height()/2)+(i*itemHeight), mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:


                break;
            case MotionEvent.ACTION_MOVE:
                float Y = event.getY();
                int index = (int) (Y/itemHeight);
                if (index != touchIndex)
                {
                    touchIndex = index;
                    invalidate();
                    if (onIndexChangeListener != null)
                    {
                        onIndexChangeListener.onIndexChange(letters[index]);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                invalidate();
                break;
        }

        return true;

    }

    public interface OnIndexChangeListener
    {
        void onIndexChange(String letter);
    }

    public OnIndexChangeListener onIndexChangeListener;

    public void setOnIndexChangeListener(LetterView.OnIndexChangeListener onIndexChangeListener) {
        this.onIndexChangeListener = onIndexChangeListener;
    }
}
