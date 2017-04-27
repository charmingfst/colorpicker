package com.chm.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chm.myapplication.R;
import com.chm.myapplication.TabFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ason on 2017/1/18.
 */
public class IndexActivity extends FragmentActivity {

    private ImageView mImageView;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private HorizontalScrollView mHorizontalScrollView;//上面的水平滚动控件
    private ViewPager mViewPager;   //下方的可横向拖动的控件
    private ArrayList<TabFragment> mTabs;//用来存放下方滚动的layout(layout_1,layout_2,layout_3)

    private List<Map<String, String>> titleList = new ArrayList<Map<String,String>>();

    private RadioGroup myRadioGroup;

    private int _id = 1000;

    private LinearLayout layout,titleLayout;

    private TextView textView;
    private FragmentPagerAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.index_main);

        initDatas();
        initGroup();
        setListener();
        iniVariable();

        mViewPager.setCurrentItem(0);

    }

    private void initDatas() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("title", "新闻");
        titleList.add(map);
        map = new HashMap<String, String>();
        map.put("title", "网页");
        titleList.add(map);

        map = new HashMap<String, String>();
        map.put("title", "体育");
        titleList.add(map);

        map = new HashMap<String, String>();
        map.put("title", "彩票");
        titleList.add(map);

        map = new HashMap<String, String>();
        map.put("title", "世界杯");
        titleList.add(map);

        map = new HashMap<String, String>();
        map.put("title", "国际");
        titleList.add(map);

        map = new HashMap<String, String>();
        map.put("title", "娱乐");
        titleList.add(map);

        map = new HashMap<String, String>();
        map.put("title", "军事");
        titleList.add(map);

        map = new HashMap<String, String>();
        map.put("title", "更多");
        titleList.add(map);
    }


    private void initGroup(){

        titleLayout = (LinearLayout) findViewById(R.id.title_lay);
        layout = (LinearLayout) findViewById(R.id.lay);

        mImageView = (ImageView)findViewById(R.id.img1);
        mHorizontalScrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);

        mViewPager = (ViewPager)findViewById(R.id.pager);

        myRadioGroup = new RadioGroup(this);
        myRadioGroup.setLayoutParams( new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(myRadioGroup);
        for (int i = 0; i <titleList.size(); i++) {
            Map<String, String> map = titleList.get(i);
            RadioButton radio = new RadioButton(this);

            radio.setBackgroundResource(R.drawable.radiobtn_selector);
            radio.setButtonDrawable(android.R.color.transparent);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            radio.setLayoutParams(l);
            radio.setGravity(Gravity.CENTER);
            radio.setPadding(20, 15, 20, 15);
            //radio.setPadding(left, top, right, bottom)
            radio.setId(_id+i);
            radio.setText(map.get("title"));
            radio.setTextSize(30);
            radio.setTextColor(Color.WHITE);
            radio.setTag(map);
            if (i == 0) {
                radio.setChecked(true);
                int itemWidth = (int) radio.getPaint().measureText(map.get("title"));
                mImageView.setLayoutParams(new  LinearLayout.LayoutParams(itemWidth+radio.getPaddingLeft()+radio.getPaddingRight(),4));
            }
            myRadioGroup.addView(radio);
        }
        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Map<String, Object> map = (Map<String, Object>) group.getChildAt(checkedId).getTag();
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton)findViewById(radioButtonId);
                Map<String, Object> selectMap = (Map<String, Object>) rb.getTag();

                AnimationSet animationSet = new AnimationSet(true);
                TranslateAnimation translateAnimation;
                translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
                animationSet.addAnimation(translateAnimation);
                animationSet.setFillBefore(true);
                animationSet.setFillAfter(true);
                animationSet.setDuration(300);

                mImageView.startAnimation(animationSet);//开始上面蓝色横条图片的动画切换
                mViewPager.setCurrentItem(radioButtonId - _id, false);//让下方ViewPager跟随上面的HorizontalScrollView切换
                mCurrentCheckedRadioLeft = rb.getLeft();//更新当前蓝色横条距离左边的距离
                //System.out.println("dis1:"+mCurrentCheckedRadioLeft);
                //System.out.println("dis2:"+( (int)mCurrentCheckedRadioLeft - (int) getResources().getDimension(R.dimen.rdo2))); R.dimen.rdo2=>100dp
                mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft, 0);

                //根据标题长度设置下标mImageView的长度
                mImageView.setLayoutParams(new  LinearLayout.LayoutParams(rb.getRight()-rb.getLeft(),4));

            }
        });

    }


    private void iniVariable() {
        mTabs = new ArrayList<TabFragment>();
        for (int i = 0; i < titleList.size(); i++) {
            TabFragment tab = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", i+"");
            tab.setArguments(bundle);

            mTabs.add(tab);
        }
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mTabs.get(i);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };
        mViewPager.setAdapter(mAdapter);//设置ViewPager的适配器
    }




    private void setListener() {
        // TODO Auto-generated method stub
        mViewPager.setOnPageChangeListener(new MyPagerOnPageChangeListener());
    }

    /**
     * ViewPager的PageChangeListener(页面改变的监听器)
     */
    private class MyPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }
        /**
         * 滑动ViewPager的时候,让上方的HorizontalScrollView自动切换
         */
        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            RadioButton radioButton = (RadioButton) findViewById(_id+position);
            radioButton.performClick();

        }
    }

}
