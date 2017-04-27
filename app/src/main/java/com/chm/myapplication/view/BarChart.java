package com.chm.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import com.chm.myapplication.R;
import com.chm.myapplication.entity.DataObject;
import com.chm.myapplication.utils.SystemUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.centerX;
import static android.R.attr.elegantTextHeight;
import static android.R.attr.x;

/**
 * Created by ason on 2017/4/20.
 */

public class BarChart extends View {
    private Paint gridPaint,linePaint,timePaint,hitP,unitP;
    private Context mContext;
    private List<DataObject> datas;
    private float xAxisRate;
    private float yAxisRate;
    private float yRate = 0;
    //最大值
    private int maxValue = 1500;
    //x轴偏移量, 滑动时需要, 负值往右
    private int xViewPosition;
    //显示8个柱条
    private static final int DISPLAY_ITEM_COUNT = 8;
    private int timeBarHeight = 20;
    private int valueBarWidth = 0;
    private Rect gridViewFrame,timeRect;
    private Drawable barImage;
    private Drawable pointImage;
    private Drawable hitImage;
    private float maxAxisWidth;
    private float minAxisWidth;
    private SimpleDateFormat format;
    //    默认第一个点击
    private int hitIndex = 0;

    private int centerIndex = Integer.MAX_VALUE;
    private int tempIndex = Integer.MAX_VALUE;

    private VelocityTracker mVelocityTracker;
    private Scroller mScroller;

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initPaint(context);
    }

    private void initPaint(Context context) {


        gridPaint = new Paint();
        gridPaint.setStrokeWidth(SystemUtils.dip2px(mContext,2));

        linePaint = new Paint();
        linePaint.setColor(mContext.getResources().getColor(R.color.linePaintColor));
        linePaint.setStrokeWidth(SystemUtils.dip2px(context,2));
        linePaint.setAntiAlias(true);
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        timePaint = new Paint();
        timePaint.setTextSize(SystemUtils.dip2px(context,12));
        timePaint.setColor(Color.WHITE);
        timePaint.setTextAlign(Paint.Align.CENTER);
        timeRect = new Rect();
        timePaint.getTextBounds("99-99",0,5,timeRect);

        format = new SimpleDateFormat("MM-dd");

    }

    private void init(Context context) {
        mContext = context;
        barImage = context.getResources().getDrawable(R.drawable.xbar);
        pointImage = context.getResources().getDrawable(R.mipmap.iconpoint);
        hitImage = context.getResources().getDrawable(R.mipmap.iconnumbers);
        setBackgroundColor(Color.TRANSPARENT);
        datas = new ArrayList<>();
        calc();
        xViewPosition = SystemUtils.dip2px(context,-15);
        mScroller = new Scroller(context);
    }

    private void calc() {
        //柱条宽
        valueBarWidth = (int) (this.getWidth() * 0.04f);
        //日期高度
        timeBarHeight = SystemUtils.dip2px(mContext,30);
        //柱图部分画布大小
        gridViewFrame = new Rect(0,0,this.getWidth(),
                this.getHeight() - timeBarHeight);
        //每列的宽，不是柱条的宽
        yAxisRate = (float)gridViewFrame.width() / DISPLAY_ITEM_COUNT;
        xAxisRate = (float)gridViewFrame.height() / DISPLAY_ITEM_COUNT;
        //值和柱图高的比率
        yRate = (float)(gridViewFrame.height() - hitImage.getIntrinsicHeight()-pointImage.getIntrinsicHeight()/2
                -SystemUtils.dip2px(mContext,2))/maxValue;

        maxAxisWidth = (datas.size() + 2) * yAxisRate;
        minAxisWidth = 0 - yAxisRate;
    }

    float lastTouchX=0;
    private boolean isIntersect = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int centerX = gridViewFrame.centerX();
        int centerDownY = gridViewFrame.height();
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                lastTouchX=event.getX();
                addVelocityTracker(event);
                break;
            case MotionEvent.ACTION_MOVE:
                float t=event.getX()-lastTouchX;
                t= xViewPosition-t;
                if (t<minAxisWidth) t=minAxisWidth;
                if (t+gridViewFrame.width()>maxAxisWidth)
                    t=maxAxisWidth-gridViewFrame.width();
                xViewPosition=(int)t;
                lastTouchX=event.getX();
//                calc();
                addVelocityTracker(event);
                //设置单位，1000 表示每秒多少像素（pix/second),1代表每微秒多少像素（pix/millisecond)。
                mVelocityTracker.computeCurrentVelocity(1000, 10000.0f);
                //从左向右划返回正数，从右向左划返回负数
                System.out.println("the x velocity is "+mVelocityTracker.getXVelocity());
                //从上往下划返回正数，从下往上划返回负数
                System.out.println("the y velocity is "+mVelocityTracker.getYVelocity());

                Rect centerRect = new Rect(centerX-valueBarWidth/2, centerDownY-100, centerX+valueBarWidth/2, centerDownY);
                int count=datas.size();
                int xValue=gridViewFrame.left-xViewPosition;
                for (int i=0;i<count;i++) {
                    DataObject value = datas.get(i);
                    int y = (int) (gridViewFrame.top + gridViewFrame.height() - value.getValue() * yRate);
                    //柱条范围
                    Rect barRect = new Rect(xValue - valueBarWidth / 2, y, xValue + valueBarWidth / 2, (int) (y + value.getValue() * yRate));

                    if (Rect.intersects(barRect, centerRect))
                    {
                        tempIndex = centerIndex = i;
                        isIntersect = true;
                        break;
                    }else {
                        isIntersect = false;
                    }
                    xValue+=yAxisRate;
                }
                if (!isIntersect)
                {
                    centerIndex = Integer.MAX_VALUE;
                }
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:

//                int count=datas.size();
                int x=gridViewFrame.left-xViewPosition;
                for (int i=0;i<datas.size();i++)
                {
                    DataObject value=datas.get(i);
                    int y=(int)(gridViewFrame.top+gridViewFrame.height()-value.getValue()*yRate);

                    //柱条范围
                    Rect barRect=new Rect(x-valueBarWidth/2, y, x+valueBarWidth/2,(int)(y+value.getValue()*yRate));

                    //柱条上圆圈的范围
                    Rect pointRect=new Rect(x-pointImage.getIntrinsicWidth()/2,
                            y-pointImage.getIntrinsicHeight()/2,
                            x+pointImage.getIntrinsicWidth()/2,
                            y+pointImage.getIntrinsicHeight()/2);
                    if (barRect.contains((int)event.getX(), (int)event.getY())
                            || pointRect.contains((int)event.getX(), (int)event.getY()))
                    {
                        //第几个选中
                        hitIndex=i;
                        this.invalidate();
                        break;
                    }
                    x+=yAxisRate;

                }
                int tempX = gridViewFrame.left-xViewPosition;
                for (int i=0;i<datas.size();i++) {
                    int dis = tempX - centerX;
                    if ( Math.abs(dis) < yAxisRate/2 )
                    {
                        if (dis < 0) //右滑
                        {
                            xViewPosition = xViewPosition - Math.abs(dis);
                            if (tempIndex <= i)
                            {
                                centerIndex = tempIndex;
                            }else {
                                centerIndex = tempIndex -1;
                            }
                        }else {
                            xViewPosition = xViewPosition +  Math.abs(dis);
                            if (tempIndex >= i)
                            {
                                centerIndex = tempIndex;
                            }else {
                                centerIndex = tempIndex+1;
                            }
                        }
                        invalidate();
                        break;
                    }

                    tempX+=yAxisRate;
                }

                // 在触点抬起后再继续滑动一定距离
                int xVelocity = getXScrollVelocity();
                int yVelocity = getYScrollVelocity();
//                mScroller.forceFinished(true);
//                mScroller.fling(xViewPosition, 0, (int) (-0.5 * xVelocity), 0, (int) minAxisWidth, (int) maxAxisWidth, 0, 0);
                recycleVelocityTracker();

        }
        return true;
    }

    private int num;
    private void myScrollTo(int partDis) {
        if (num*partDis < maxAxisWidth)
        {
            num++;
            xViewPosition = xViewPosition+partDis;
            postInvalidateDelayed(1000);
        }
    }

    private void addVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收速度追踪器
     */
    private void recycleVelocityTracker()
    {
        if(mVelocityTracker != null)
        {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * 获取X方向的滑动速度,大于0向右滑动，反之向左
     */
    private int getXScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000, 1000.0f);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return velocity;
    }

    /**
     * 获取Y方向的滑动速度,大于0向下滑动，反之向上
     */
    private int getYScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getYVelocity();
        return velocity;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calc();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        drawGrid(canvas);
        drawValues(canvas);

//        int centerX = gridViewFrame.centerX();
//        int centerDownY = gridViewFrame.height();
//        canvas.drawRect(centerX-valueBarWidth/2, centerDownY-100, centerX+valueBarWidth/2, centerDownY, new Paint(Paint.ANTI_ALIAS_FLAG));

    }

    protected void drawValues(Canvas context) {
        int count=datas.size();
        Paint lineP=new Paint();
        lineP.setColor(0xFF24DAEF);
        lineP.setStrokeWidth(SystemUtils.dip2px(mContext,2));
        lineP.setAntiAlias(true);
        Paint p=new Paint();

        Rect timeRect=new Rect();
        p.setTextSize(SystemUtils.dip2px(mContext,12));
        p.setColor(Color.WHITE);
        p.setTextAlign(Paint.Align.CENTER);
        p.getTextBounds("99-99",0,5,timeRect);

        SimpleDateFormat format=new SimpleDateFormat("MM-dd");
//        System.out.println("gridLeft:"+gridViewFrame.left);
        Paint mPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
        int x=gridViewFrame.left-xViewPosition;
        for (int i=0;i<count;i++)
        {
            DataObject value=datas.get(i);
            int y=(int)(gridViewFrame.height()-value.getValue()*yRate);

            Rect checkRect=new Rect((int)(x-timeRect.width()/2), y,
                    (int)(x+timeRect.width()/2), (int)(y+value.getValue()*yRate));
//            if (hitIndex==i)
//            {
//                checkRect=new Rect(x-hitImage.getIntrinsicWidth()/2,
//                        y,x+hitImage.getIntrinsicWidth()/2,400);
////                context.drawRect(checkRect, mPaint);
//            }

//            context.drawRect(checkRect, mPaint);
//            gridViewFrame.exactCenterX();

            //判断是否相交
            if (Rect.intersects(gridViewFrame, checkRect))
            {
                Rect barRect=new Rect((int)(x-valueBarWidth/2), y,
                        (int)(x+valueBarWidth/2), (int)(y+value.getValue()*yRate));
                barImage.setBounds(barRect);
                //画柱条
                if (centerIndex == i)
                {
                    context.drawRect(barRect, mPaint);
                }else {
                    barImage.draw(context);
                }
//                pointImage.setBounds(x-pointImage.getIntrinsicWidth()/2,y-pointImage.getIntrinsicHeight()/2,
//                        x+pointImage.getIntrinsicWidth()/2,y+pointImage.getIntrinsicHeight()/2);
//                pointImage.draw(context);

                String time=format.format(value.getDate());
                float x_pos = x-SystemUtils.dip2px(mContext,1);
                float y_pos = gridViewFrame.bottom+(timeBarHeight-timeRect.height()) /2+timeRect.height();
                String text=format.format(value.getDate());
                //画日期
                context.drawText(text, x_pos, y_pos, p);

                //画提示框中的字
                if (i==hitIndex)
                {
                    y_pos=y-pointImage.getIntrinsicHeight()/2-hitImage.getIntrinsicHeight();
                    Rect hitRect=new Rect(x-hitImage.getIntrinsicWidth()/2, (int)y_pos,
                            x+hitImage.getIntrinsicWidth()/2 , y-pointImage.getIntrinsicHeight()/2);
                    hitImage.setBounds(hitRect);
                    //画提示框背景图
//                    hitImage.draw(context);


                    Paint hitP=new Paint();
                    hitP.setColor(Color.WHITE);
                    hitP.setTextAlign(Paint.Align.CENTER);
                    hitP.setAntiAlias(true);
                    hitP.setFlags(Paint.ANTI_ALIAS_FLAG);
                    hitP.setTextSize(SystemUtils.dip2px(mContext,12));
                    String hitValue=String.valueOf(value.getValue());
                    Rect valueSize=new Rect();
                    hitP.getTextBounds(hitValue, 0, hitValue.length(), valueSize);

                    Paint unitP=new Paint();
                    unitP.setColor(Color.WHITE);
                    unitP.setTextAlign(Paint.Align.CENTER);
                    unitP.setAntiAlias(true);
                    unitP.setFlags(Paint.ANTI_ALIAS_FLAG);
                    unitP.setTextSize(SystemUtils.dip2px(mContext,12));
                    String unitValue="ml";
                    Rect unitSize=new Rect();
                    unitP.getTextBounds(unitValue, 0, unitValue.length(), unitSize);

                    context.drawText(hitValue, hitRect.centerX(),
                            hitRect.centerY(),hitP);
                    context.drawText(unitValue, hitRect.centerX(),
                            hitRect.centerY()+unitSize.height(),unitP);
                }
            }

            x+=yAxisRate;
        }
    }

    private void drawGrid(Canvas canvas) {
        int x = gridViewFrame.left - xViewPosition;
        int y = gridViewFrame.top;
        int count = datas.size();

        gridPaint.setColor(Color.WHITE);

        for (int i = 0; i < count; i ++){
            if (gridViewFrame.contains(x,gridViewFrame.top)){
                canvas.drawLine(x,gridViewFrame.top,x,gridViewFrame.bottom,gridPaint);
            }
            x += yAxisRate;
        }
        x = gridViewFrame.left;
        for (int i = 0; i < DISPLAY_ITEM_COUNT;i ++){
            canvas.drawLine(x,y,this.getWidth(),y,gridPaint);
            y += xAxisRate;
        }
        gridPaint.setColor(getResources().getColor(R.color.gridBottomRect));

        int yPos = gridViewFrame.bottom + SystemUtils.dip2px(mContext,4);
        gridPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(new Rect(gridViewFrame.left,yPos,gridViewFrame.right,timeBarHeight + yPos-SystemUtils.dip2px(mContext,8)),gridPaint);
    }

    public void setDatas(ArrayList<DataObject> datas) {
        this.datas = datas;
        invalidate();
    }
}
