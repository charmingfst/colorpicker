package com.chm.myapplication.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chm.myapplication.R;

import static com.chm.myapplication.R.id.view;

/**
 * Created by ason on 2017/4/1.
 */

public class FlexibleMenu extends RelativeLayout implements View.OnClickListener {
    private View view;
    private ImageView confirm;
    private ImageView plus;
    private ImageView edit;
    private ImageView send;

    boolean isOpen = false;
    private float confirmX;
    private float plusX;
    private float sendX;
    private float editX;
    public FlexibleMenu(Context context) {
        this(context, null);
    }

    public FlexibleMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlexibleMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        confirm = (ImageView) findViewById(R.id.heart);
        plus = (ImageView) findViewById(R.id.plus);
        edit = (ImageView) findViewById(R.id.confirm);
        send = (ImageView) findViewById(R.id.arrow);
        Log.e("fs:inflate", "zhixing");
        setListener();
    }


    private void init() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.my_menu, this);

//      为了获得控件平均分布的效果，通过xml布局均分好控件，再到代码中获得均分后控件的位置getX
//      不能立刻获取getX，刚开始立马获得的话，全部为0，因为系统还没有把这些view布置好
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(300);//这里睡的时间要稍微长一点，大于100为宜，否则得不到X位置
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//               注意getX()得到的是这个View在手机屏幕上的X坐标
//                而TranslationX在首次运行时，永远是0，他表示的是此View离开原始位置的距离
                confirmX = confirm.getX();
                plusX = plus.getX();
                sendX = send.getX();
                editX = edit.getX();
                Log.e("fs:confirmX", confirmX + "");
                Log.e("fs:plusX", plusX + "");
                Log.e("fs:sendX", sendX + "");
                Log.e("fs:editX", editX + "");
                confirm.setX(plusX);//此时，这个view已经离开了原始位置了，所以这是打印的TranslationX不是0，是plusX-confirmX
                send.setX(plusX);
                edit.setX(plusX);
                Log.e("qqq", confirm.getTranslationX() + "");
            }
        }.start();
    }


    private void setListener() {
        confirm.setOnClickListener(this);
        plus.setOnClickListener(this);
        edit.setOnClickListener(this);
        send.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.plus:
                if (!isOpen) { //开
                    confirm.setVisibility(VISIBLE);
                    edit.setVisibility(VISIBLE);
                    send.setVisibility(VISIBLE);
                    plus.animate().rotation(45).setDuration(200).start();
                    Log.e("qqq", confirm.getTranslationX() + "");
//                    注意这里的translationX要回到原始位置
//                    edit.animate().translationX(0).setDuration(200).start();
                    ObjectAnimator animatorConfirm = ObjectAnimator.ofFloat(confirm, "translationX", 0);
                    ObjectAnimator animatorEdit = ObjectAnimator.ofFloat(edit, "translationX", 0);
                    ObjectAnimator animatorSend = ObjectAnimator.ofFloat(send, "translationX", 0);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(200);
                    animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
                    animatorSet.playTogether(animatorConfirm, animatorEdit, animatorSend);
                    animatorSet.start();
                    isOpen = true;
                } else {
                    plus.animate().rotation(0).setDuration(200).start();
//                    注意这里的translationX要运行到plus后面
                    ObjectAnimator animatorConfirm = ObjectAnimator.ofFloat(confirm, "translationX", plusX - confirmX);
                    ObjectAnimator animatorEdit = ObjectAnimator.ofFloat(edit, "translationX", plusX - editX);
                    ObjectAnimator animatorSend = ObjectAnimator.ofFloat(send, "translationX", plusX - sendX);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(200);
                    animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
                    animatorSet.playTogether(animatorConfirm, animatorEdit, animatorSend);
                    animatorSet.start();
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            confirm.setVisibility(GONE);
                            edit.setVisibility(GONE);
                            send.setVisibility(GONE);
                        }
                    });
                    isOpen = false;
                }

                break;

            case R.id.heart:
//                这里可以暴露接口，就不写了
                Toast.makeText(getContext(),"edit",Toast.LENGTH_SHORT).show();
                break;

            case R.id.arrow:
                Toast.makeText(getContext(),"send",Toast.LENGTH_SHORT).show();
                break;
            case R.id.confirm:
                Toast.makeText(getContext(),"confirm",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
