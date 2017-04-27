package com.chm.myapplication.view;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by ason on 2017/3/31.
 */

public class ViewAnimation extends Animation {
    int mCenterX;

    int mCenterY;

    Camera mCamera;

    public ViewAnimation()
    {

    }

    /**
     * @param width 目标对象的宽度
     * @param height 目标对象的高度
     * @param parentWidth
     * @param parentHeight
     */
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight)
    {

        super.initialize(width, height, parentWidth, parentHeight);

        mCamera = new Camera();

        mCenterX = width/2;

        mCenterY = height/2;

        setDuration(2500);

        setFillAfter(true);

        // setRepeatCount(100);

        setInterpolator(new LinearInterpolator());

    }



    /**
     * camera.translate(0.0f, 0.0f, (1300 - 1300.0f * interpolatedTime))在第一次调用的时候interpolatedTime值为0，
     * 相当于把ImageView在Z轴后移1300像素， 然后逐步的往前移动到0，
     * 同时camera.rotateY(360 * interpolatedTime)函数又把ImageView沿Y轴翻转360度
     * @param interpolatedTime 表示的是当前动画的间隔时间 范围是0-1
     * @param t
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t)
    {
        final Matrix matrix = t.getMatrix();

        mCamera.save();

        mCamera.translate(0.0f, 0.0f, (1300 - 1300.0f * interpolatedTime));

        mCamera.rotateY(360 * interpolatedTime);

        mCamera.getMatrix(matrix);

        matrix.preTranslate(-mCenterX, -mCenterY);

        matrix.postTranslate(mCenterX, mCenterY);

        mCamera.restore();
//        //透明度0-1
//        t.setAlpha(interpolatedTime);
//        //周期性左右晃动
//        t.getMatrix().setTranslate((float) (Math.sin(interpolatedTime*20)*50), 0);
    }
}
