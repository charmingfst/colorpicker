package com.chm.circle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;


/**
 * Created by ason on 2017/4/17.
 */

public class IntervalColorPicker extends View {
    private int mThumbHeight;
    private int mThumbWidth;

    private int mThumbLowHeight, mThumbLowWidth;
    private String[] colors ;

    private int sections;
    //每个小块的度数
    private int sectionAngle;

    private Paint mPaint;

    private Paint arcPaint;

    private int ringWidth;

    private RectF mRectF;

    private Drawable mThumbHighDrawable = null;
    private Drawable mThumbLowDrawable;
    private float mThumbLeft;
    private float mThumbTop;

    private float mThumbLowLeft, mThumbLowTop;

    private double mViewCenterX, mViewCenterY;
    private double mViewRadisu;
    //起始角度
    private float mStartDegree = 270;

    //当前view的尺寸
    private int mViewSize;

    //区间
    private int interval = 7;

    private boolean reverse;

    private float tempStartAngle = mStartDegree;

    public IntervalColorPicker(Context context) {
        this(context, null);
    }

    public IntervalColorPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IntervalColorPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray localTypedArray = context.obtainStyledAttributes(attrs, R.styleable.IntervalColorPicker);
        mThumbHighDrawable = localTypedArray.getDrawable(R.styleable.IntervalColorPicker_thumbHigh);
        mThumbLowDrawable = localTypedArray.getDrawable(R.styleable.IntervalColorPicker_thumbLow);
        ringWidth = (int) localTypedArray.getDimension(R.styleable.IntervalColorPicker_ring_breadth, 30);
        interval = localTypedArray.getInteger(R.styleable.IntervalColorPicker_interval, 8);
        localTypedArray.recycle();
        init();
    }

    private void init() {
        colors = getResources().getStringArray(R.array.colorall);
        sections = colors.length;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(ringWidth);

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(ringWidth + 1);
        arcPaint.setColor(Color.GRAY);

        mThumbWidth = this.mThumbHighDrawable.getIntrinsicWidth();
        mThumbHeight = this.mThumbHighDrawable.getIntrinsicHeight();
        mThumbLowHeight = mThumbLowDrawable.getIntrinsicHeight();
        mThumbLowWidth = mThumbHighDrawable.getIntrinsicWidth();

        sectionAngle = 360 / sections;


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int circleX = getMeasuredWidth();
        int circleY = getMeasuredHeight();
        if (circleY < circleX) {
            circleX = circleY;
        }
        mViewSize = circleX;
        mViewCenterX = circleX / 2;
        mViewCenterY = circleY / 2;
        mViewRadisu = circleX / 2 - mThumbWidth / 2;
    }

    private float sweepAngle;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mRectF = new RectF(0 + mThumbWidth / 2, 0 + mThumbWidth / 2, mViewSize - mThumbWidth / 2, mViewSize - mThumbWidth / 2);

        for (int i = 0; i < colors.length; i++) {
            mPaint.setColor(Color.parseColor(colors[i]));
            canvas.drawArc(mRectF, i * sectionAngle - 90, sectionAngle + 1, false, mPaint);
        }

        int tempAng = (int) (tempStartAngle + sweepAngle);
        int intervalAngle = interval * sectionAngle;

        if (reverse) {
            setThumbPosition(Math.toRadians(tempAng));
            setThumbLowPosition(Math.toRadians(tempAng - intervalAngle));
            canvas.drawArc(mRectF, tempAng, 360 - intervalAngle, false, arcPaint);
        } else {
            setThumbPosition(Math.toRadians(tempAng));
            setThumbLowPosition(Math.toRadians(tempAng + intervalAngle));
            canvas.drawArc(mRectF, (int) (tempAng + intervalAngle), 360 - intervalAngle, false, arcPaint);
        }

        mThumbHighDrawable.setBounds((int) mThumbLeft, (int) mThumbTop,
                (int) (mThumbLeft + mThumbWidth), (int) (mThumbTop + mThumbHeight));
        mThumbLowDrawable.setBounds((int) mThumbLowLeft, (int) mThumbLowTop, (int) (mThumbLowLeft + mThumbLowWidth), (int) (mThumbLowTop + mThumbLowHeight));


        mThumbHighDrawable.draw(canvas);
        mThumbLowDrawable.draw(canvas);
    }

    private void setThumbPosition(double radian) {
        double x = mViewCenterX + mViewRadisu * Math.cos(radian);
        double y = mViewCenterY + mViewRadisu * Math.sin(radian);
        mThumbLeft = (float) (x - mThumbWidth / 2);
        mThumbTop = (float) (y - mThumbHeight / 2);
    }

    private void setThumbLowPosition(double radian) {
        double x = mViewCenterX + mViewRadisu * Math.cos(radian);
        double y = mViewCenterY + mViewRadisu * Math.sin(radian);
        mThumbLowLeft = (float) (x - mThumbLowWidth / 2);
        mThumbLowTop = (float) (y - mThumbLowHeight / 2);
    }

    private boolean isDown = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getEventDegree(eventX, eventY);
//                seekTo(eventX, eventY, false);
                break;

            case MotionEvent.ACTION_MOVE:

                seekTo(eventX, eventY);
                break;

            case MotionEvent.ACTION_UP:
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tempStartAngle = tempStartAngle + sweepAngle;
                        sweepAngle = 0;
                        getSelectedColor();
                        if (onColorChangeListener != null) {
                            onColorChangeListener.colorChange(selectedColors);
                        }
                    }
                }, 100);

                break;

        }
        return true;
    }

    private float downDegree;

    private void getEventDegree(float eventX, float eventY) {
        if (isPointOnThumb(eventX, eventY)) {
            double radian = Math.atan2(eventY - mViewCenterY, eventX - mViewCenterX);
            /*
             * 由于atan2返回的值为[-pi,pi]
             * 因此需要将弧度值转换一下，使得区间为[0,2*pi]
             */
            if (radian < 0) {
                radian = radian + 2 * Math.PI;
            }
            isDown = true;
            downDegree = Math.round(Math.toDegrees(radian));
        } else {
            isDown = false;
        }
    }

    private void seekTo(float eventX, float eventY) {
        if (true == isPointOnThumb(eventX, eventY)) {
//            mThumbHighDrawable.setState(mThumbPressed);

            if (!isDown) {
                getEventDegree(eventX, eventY);
                isDown = true;
            }

            double radian = Math.atan2(eventY - mViewCenterY, eventX - mViewCenterX);
            /*
             * 由于atan2返回的值为[-pi,pi]
             * 因此需要将弧度值转换一下，使得区间为[0,2*pi]
             */
            if (radian < 0) {
                radian = radian + 2 * Math.PI;
            }
            setThumbPosition(radian);

            float mSweepDegree = (float) Math.round(Math.toDegrees(radian));

            sweepAngle = mSweepDegree - downDegree;


            invalidate();
        }
    }

    //选中的颜色
    private ArrayList<Integer> selectedColors = new ArrayList<>(interval);

    public void getSelectedColor() {
        int tempIndex = (int) (tempStartAngle / sectionAngle);
        int num = 90 / sectionAngle;
        if (tempIndex == sections) {
            tempIndex = 0;
        }
        int index = tempIndex;
        if (tempIndex >= 0) {
            index = tempIndex + num;
        }
        if (tempIndex >= (sections - num)) {
            index = tempIndex - (sections - num);
        }


        if (index>colors.length)
            index = index%colors.length;
        while (index<0)
        {
            index = colors.length+index;
        }
        selectedColors.clear();
        int startIndex = 0;
        if (reverse)
        {
            startIndex = index - interval -1;
            while (startIndex < 0)
            {
                startIndex = startIndex+colors.length;
            }
            if (startIndex > index)
            {
                for (int i = startIndex+1; i < colors.length; i++) {
                    selectedColors.add(Color.parseColor(colors[i]));
                }
                for (int i = 0; i <= index; i++) {
                    selectedColors.add(Color.parseColor(colors[i]));
                }
            }else {
                for (int i = startIndex+1; i <= index; i++) {
                    selectedColors.add(Color.parseColor(colors[i]));
                }
            }
        }else {
            startIndex = index+interval+1;
            while (startIndex>colors.length)
            {
                startIndex = startIndex-colors.length;
            }
            if (startIndex < index)
            {
                for (int i = startIndex-1; i >= 0; i--) {
                    selectedColors.add(Color.parseColor(colors[i]));
                }
                for (int i = colors.length-1; i >= index; i--) {
                    selectedColors.add(Color.parseColor(colors[i]));
                }
            }else {
                for (int i = startIndex-1; i >=index; i--) {
                    selectedColors.add(Color.parseColor(colors[i]));
                }
            }

        }

    }


    private boolean isPointOnThumb(float eventX, float eventY) {
        boolean result = false;
        double distance = Math.sqrt(Math.pow(eventX - mViewCenterX, 2)
                + Math.pow(eventY - mViewCenterY, 2));
        if (distance < mViewSize && distance > (mViewSize / 2 - mThumbWidth)) {
            result = true;
        }
        return result;
    }


    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
        invalidate();
    }

    public interface OnColorChangeListener {
        void colorChange(ArrayList<Integer> colors);
    }

    public void setOnColorChangeListener(OnColorChangeListener onColorChangeListener) {
        this.onColorChangeListener = onColorChangeListener;
    }

    private OnColorChangeListener onColorChangeListener;
}
