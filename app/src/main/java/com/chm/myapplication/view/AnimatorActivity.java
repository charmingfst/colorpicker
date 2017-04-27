package com.chm.myapplication.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.chm.myapplication.R;

/**
 * Created by ason on 2017/1/9.
 */
public class AnimatorActivity extends Activity {
    private Button btn;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animator_test);

        btn = (Button) findViewById(R.id.btn);
        imageView = (ImageView) findViewById(R.id.img);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testAnimator();
            }
        });
    }

    private void testAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationX", 0, imageView.getWidth());
        animator.setDuration(1000);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }
}
