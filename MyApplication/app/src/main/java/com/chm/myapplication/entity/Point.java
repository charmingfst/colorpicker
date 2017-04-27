package com.chm.myapplication.entity;

/**
 * Created by ason on 2017/1/24.
 */
public class Point {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_PRESS = 1;
    public static final int STATE_ERROR = 2;
    public float x;
    public float y;

    public int state = STATE_NORMAL;

    public float distance(Point a)
    {
        float distance = (float)Math.sqrt((x-a.x)*(x-a.x)+(y-a.y)*(y-a.y));
        return distance;
    }

    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

}
