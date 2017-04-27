package com.chm.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chm.myapplication.R;
import com.chm.myapplication.entity.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ason on 2017/1/24.
 */
public class GestureView extends View {
    private boolean isInit; //是否初始化
    private Point[][] points = new Point[3][3];
    private Paint pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint pressPaint = new Paint();
    private Paint errorPaint = new Paint();

    private Bitmap normalBitap, pressBitmap, errorBitmap;

    private int bitmapR;
    private boolean isDraw;
    private ArrayList<Point> pressedPoints = new ArrayList<>();
    private ArrayList<Integer> passList = new ArrayList<>(); //记录所滑过的点的位置，第几个点，以便后续验证

    public GestureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            init();
        }

        //画点
        drawPoint(canvas);

        //有点后，开始画线
        if (pressedPoints.size() > 0) {
            Point a = pressedPoints.get(0);
            for (int i = 0; i < pressedPoints.size(); i++) {
                Point b = pressedPoints.get(i);
                drawLine(canvas, a, b);
                a = b;
            }
            if (isDraw) {
                drawLine(canvas, a, new Point(mouseX, mouseY));
            }
        }

    }

    private void drawLine(Canvas canvas, Point a, Point b) {
        if (a.state == Point.STATE_PRESS) {
            canvas.drawLine(a.x, a.y, b.x, b.y, pressPaint);
        } else if (a.state == Point.STATE_ERROR) {
            canvas.drawLine(a.x, a.y, b.x, b.y, errorPaint);
        }
    }

    private void drawPoint(Canvas canvas) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[1].length; j++) {
                Point point = points[i][j];
                if (point.state == Point.STATE_NORMAL) {
                    canvas.drawBitmap(normalBitap, point.x - bitmapR, point.y - bitmapR, pointPaint);
                } else if (point.state == Point.STATE_PRESS) {
                    canvas.drawBitmap(pressBitmap, point.x - bitmapR, point.y - bitmapR, pointPaint);
                } else {
                    canvas.drawBitmap(errorBitmap, point.x - bitmapR, point.y - bitmapR, pointPaint);
                }

            }

        }
    }

    public void resetPoint() {

        pressedPoints.clear();
        passList.clear();
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[0].length; j++) {
                points[i][j].state = Point.STATE_NORMAL;
            }
        }
        invalidate();
    }

    private void init() {

        pressPaint.setColor(Color.YELLOW);
        pressPaint.setStrokeWidth(10);
        errorPaint.setColor(Color.RED);
        errorPaint.setStrokeWidth(10);
        int width = getWidth();
        int height = getHeight();
        int offset = Math.abs(width - height) / 2;
        int offsetX, offsetY;
        int space;
        if (width > height) //横屏
        {
            space = height / 4;
            offsetX = offset;
            offsetY = 0;
        } else {
            space = width / 4;
            offsetX = 0;
            offsetY = offset;
        }

        points[0][0] = new Point(offsetX + space, offsetY + space);
        points[0][1] = new Point(offsetX + space * 2, offsetY + space);
        points[0][2] = new Point(offsetX + space * 3, offsetY + space);

        points[1][0] = new Point(offsetX + space, offsetY + space * 2);
        points[1][1] = new Point(offsetX + space * 2, offsetY + space * 2);
        points[1][2] = new Point(offsetX + space * 3, offsetY + space * 2);

        points[2][0] = new Point(offsetX + space, offsetY + space * 3);
        points[2][1] = new Point(offsetX + space * 2, offsetY + space * 3);
        points[2][2] = new Point(offsetX + space * 3, offsetY + space * 3);

        normalBitap = BitmapFactory.decodeResource(getResources(), R.drawable.normal);
        pressBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.press);
        errorBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.error);
        bitmapR = normalBitap.getWidth() / 2;

        isInit = true;
    }


    float mouseX, mouseY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        super.onTouchEvent(event);
        mouseX = event.getX();
        mouseY = event.getY();
        int[] ij;
        int i, j;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                resetPoint();
                ij = getSelectPoint();
                if (ij != null) {
                    isDraw = true;
                    i = ij[0];
                    j = ij[1];
                    points[i][j].state = Point.STATE_PRESS;
                    pressedPoints.add(points[i][j]);
                    passList.add(i * points.length + j);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDraw) {
                    ij = getSelectPoint();
                    if (ij != null) {
                        i = ij[0];
                        j = ij[1];

                        if (!pressedPoints.contains(points[i][j])) {
                            points[i][j].state = Point.STATE_PRESS;
                            pressedPoints.add(points[i][j]);
                            passList.add(i * points.length + j);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                boolean valid = false;
                if (onDrawFinishedListener != null && isDraw) {
                    valid = onDrawFinishedListener.onDrawFinished(passList);
//                    onDrawFinishedListener.onDrawFinished(passList);
                }
//                if (passList.size() <= 3) {
//                    valid = false;
//                }else {
//                    valid = true;
//                }
                if (!valid) {
                    for (Point p : pressedPoints) {
                        p.state = Point.STATE_ERROR;
                    }
                }
                isDraw = false;
                break;
        }
        invalidate();
        return true;
    }

    private int[] getSelectPoint() {
        Point pMouse = new Point(mouseX, mouseY);
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[0].length; j++) {
                if (points[i][j].distance(pMouse) < bitmapR) {
                    int[] result = new int[2];
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }


    public interface OnDrawFinishedListener {
        boolean onDrawFinished(List<Integer> passList);
    }

    private OnDrawFinishedListener onDrawFinishedListener;

    public void setOnDrawFinishedListener(OnDrawFinishedListener onDrawFinishedListener) {
        this.onDrawFinishedListener = onDrawFinishedListener;
    }
}
