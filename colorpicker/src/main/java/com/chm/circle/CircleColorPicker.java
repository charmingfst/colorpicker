package com.chm.circle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by ason on 2017/4/14.
 */

public class CircleColorPicker extends View {

    private int mThumbHeight;
    private int mThumbWidth;
    private String[] colors ;

    private int sections;
    //每个小块的度数
    private int sectionAngle;

    private Paint mPaint;

    private int ringWidth;

    private RectF mRectF;

    private Drawable mThumbDrawable = null;
    private float mThumbLeft;
    private float mThumbTop;
    private double mViewCenterX, mViewCenterY;
    private double mViewRadisu;
    //起始角度
    private int mStartDegree = -90;

    //当前view的尺寸
    private int mViewSize;

    private int textColor;
    private String text;

    private Paint textPaint;

    private Rect mBounds;

    private float textSize;

    private int colorType;

    private int default_size = 100;

    private boolean isInit;

    public CircleColorPicker(Context context) {
        this(context, null);
    }

    public CircleColorPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleColorPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray localTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleColorPicker);
        mThumbDrawable = localTypedArray.getDrawable(R.styleable.CircleColorPicker_thumb);
        ringWidth = (int) localTypedArray.getDimension(R.styleable.CircleColorPicker_ring_span, 30);
        colorType = localTypedArray.getInt(R.styleable.CircleColorPicker_color_type, 0);
        textColor = localTypedArray.getColor(R.styleable.CircleColorPicker_text_color, Color.BLACK);
        text = localTypedArray.getString(R.styleable.CircleColorPicker_text);
        textSize = localTypedArray.getDimension(R.styleable.CircleColorPicker_text_size, 20);
        localTypedArray.recycle();
        default_size = dip2px(context, 260);
        init();
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void init() {

        colors = getResources().getStringArray(R.array.colorall);
       
        sections = colors.length;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(ringWidth);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        mThumbWidth = this.mThumbDrawable.getIntrinsicWidth();
        mThumbHeight = this.mThumbDrawable.getIntrinsicHeight();

        sectionAngle = 360/sections;

        mBounds = new Rect();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredLength(widthMeasureSpec, true), getMeasuredLength(heightMeasureSpec, false));

        int circleX = getMeasuredWidth();
        int circleY = getMeasuredHeight();
        if (circleY < circleX)
        {
            circleX = circleY;
        }
        mViewSize = circleX;
        mViewCenterX = circleX/2;
        mViewCenterY = circleY/2;
        mViewRadisu = circleX/2 - mThumbWidth / 2;

        setThumbPosition(Math.toRadians(mStartDegree));
        isInit = true;
    }

    private int getMeasuredLength(int length, boolean isWidth) {
        int specMode = MeasureSpec.getMode(length);
        int specSize = MeasureSpec.getSize(length);
        int size;
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (specMode == MeasureSpec.EXACTLY) {
            size = specSize;
        } else {
            size = default_size + padding;
            if (specMode == MeasureSpec.AT_MOST) {
                size = Math.min(size, specSize);
            }
        }
        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInit) {
            mRectF = new RectF(0 + mThumbWidth / 2, 0 + mThumbWidth / 2, mViewSize - mThumbWidth / 2, mViewSize - mThumbWidth / 2);
           
            for (int i = 0; i < colors.length; i++) {
                mPaint.setColor(Color.parseColor(colors[i]));
                canvas.drawArc(mRectF, i * sectionAngle - 90, sectionAngle + 1, false, mPaint);
            }

            mThumbDrawable.setBounds((int) mThumbLeft, (int) mThumbTop,
                    (int) (mThumbLeft + mThumbWidth), (int) (mThumbTop + mThumbHeight));
            mThumbDrawable.draw(canvas);

            if (text != null)
            {
                textPaint.getTextBounds(text, 0, text.length(), mBounds);
                float textWidth = mBounds.width();
                float textHeight = mBounds.height();

                float textLeft = (float) (mViewCenterX - textWidth / 2);
                float textTop = (float) (mViewCenterY + textHeight / 2);
                canvas.drawText(text, 0, text.length(), textLeft, textTop, textPaint);
            }

        }
    }

    private void setThumbPosition(double radian) {
        double x = mViewCenterX + mViewRadisu * Math.cos(radian);
        double y = mViewCenterY + mViewRadisu * Math.sin(radian);
        mThumbLeft = (float) (x - mThumbWidth / 2);
        mThumbTop = (float) (y - mThumbHeight / 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                seekTo(eventX, eventY, false);
                break ;

            case MotionEvent.ACTION_MOVE:
                seekTo(eventX, eventY, false);
                break ;

            case MotionEvent.ACTION_UP:
//                seekTo(eventX, eventY, true);
                float part = sectionAngle / 4.0f;
                for (int i = 0; i < sections; i++) {
                    if ( mSweepDegree > (i-1)*sectionAngle+part*3 && mSweepDegree < i *sectionAngle + part)
                    {
                        if (mSweepDegree < i*sectionAngle)
                        {
                            setThumbPosition(Math.toRadians((i-1)*sectionAngle+part*2));
                        }else {
                            setThumbPosition(Math.toRadians(i*sectionAngle+part*2));
                        }
                    }
                }
                if (mSweepDegree > ((sections-1)*sectionAngle)+part*3)
                {
                    setThumbPosition(Math.toRadians((sections-1)*sectionAngle+part*2));
                }
                invalidate();
                break ;
        }
        return true;
    }

    private int preColor;

    private float mSweepDegree;
    private void seekTo(float eventX, float eventY, boolean isUp) {
        if (true == isPointOnThumb(eventX, eventY) && false == isUp) {
//            mThumbDrawable.setState(mThumbPressed);

            double radian = Math.atan2(eventY - mViewCenterY, eventX - mViewCenterX);
            /*
             * 由于atan2返回的值为[-pi,pi]
             * 因此需要将弧度值转换一下，使得区间为[0,2*pi]
             */
            if (radian < 0){
                radian = radian + 2*Math.PI;
            }
            setThumbPosition(radian);

            mSweepDegree = (float) Math.round(Math.toDegrees(radian));

            int currentColor = getColor(mSweepDegree);
            if (currentColor != preColor)
            {
                preColor = currentColor;
                if (onColorChangeListener != null)
                {
                    onColorChangeListener.colorChange(preColor);
                }
            }

            invalidate();
        }else{
//            mThumbDrawable.setState(mThumbNormal);
            invalidate();
        }
    }

    private int getColor(float mSweepDegree) {

        int tempIndex = (int) (mSweepDegree/sectionAngle);

        int num = 90 / sectionAngle;

        if (tempIndex ==sections)
        {
            tempIndex = 0;
        }

        int index = tempIndex;
        if (tempIndex >= 0) {
            index = tempIndex+num;
        }
        if (tempIndex >= (sections-num))
        {
            index = tempIndex-(sections-num);
        }

        return Color.parseColor(colors[index]);
    }


    private boolean isPointOnThumb(float eventX, float eventY) {
        boolean result = false;
        double distance = Math.sqrt(Math.pow(eventX - mViewCenterX, 2)
                + Math.pow(eventY - mViewCenterY, 2));
        if (distance < mViewSize && distance > (mViewSize / 2 - mThumbWidth)){
            result = true;
        }
        return result;
    }

    //设置可选颜色种类
    public void setColors(String... colors)
    {
        this.colors = colors;
        sections = colors.length;
        sectionAngle = 360/sections;
        invalidate();
    }

    public int getCurrentColor()
    {
        return preColor;
    }

    public void setStartColor(String color)
    {
        for (int i = 0; i < colors.length; i++)
        {
            if (colors[i].equals(color))
            {
                preColor = Color.parseColor(colors[i]);
                int sweepAngle = (i- 90 /sectionAngle)*sectionAngle+sectionAngle/2;
//                postDelayed(()->{
//                    setThumbPosition(Math.toRadians(sweepAngle));
//                    invalidate();
//                },200);
                mStartDegree = sweepAngle;
                //最好加上
                invalidate();
                break;
            }
        }
    }

    public void setColor(String color) {
        for (int i = 0; i < colors.length; i++)
        {
            if (colors[i].equals(color))
            {
                preColor = Color.parseColor(colors[i]);
                int sweepAngle = (i- 90 /sectionAngle)*sectionAngle+sectionAngle/2;
                setThumbPosition(Math.toRadians(sweepAngle));
                invalidate();
                break;
            }
        }
    }


    public interface OnColorChangeListener
    {
        void colorChange(int color);
    }

    public void setOnColorChangeListener(OnColorChangeListener onColorChangeListener) {
        this.onColorChangeListener = onColorChangeListener;
    }

    private OnColorChangeListener onColorChangeListener;
}
