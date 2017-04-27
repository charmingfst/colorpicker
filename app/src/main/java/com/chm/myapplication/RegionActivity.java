package com.chm.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.WorkSource;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chm.myapplication.adapter.MyAdapter;
import com.chm.myapplication.entity.RegionInfo;
import com.chm.myapplication.view.LetterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ason on 2017/1/19.
 */
public class RegionActivity extends Activity {
    private TextView mTextView;
    private LetterView letterView;
    private Handler handler;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean move;
    private ArrayList<RegionInfo> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.letters);
        mTextView = (TextView) findViewById(R.id.letter);
        letterView = (LetterView) findViewById(R.id.letter_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mTextView.setVisibility(View.GONE);
            }
        };
        letterView.setOnIndexChangeListener(new LetterView.OnIndexChangeListener() {
            @Override
            public void onIndexChange(String letter) {
                mTextView.setVisibility(View.VISIBLE);
                mTextView.setText(letter);
                handler.sendEmptyMessageDelayed(0, 3000);
                updateView(letter);
            }

        });

        datas = new ArrayList<>();
        datas.add(new RegionInfo("武汉"));
        datas.add(new RegionInfo("孝感"));
        datas.add(new RegionInfo("济南"));
        datas.add(new RegionInfo("北京"));
        datas.add(new RegionInfo("上海"));
        datas.add(new RegionInfo("深圳"));
        datas.add(new RegionInfo("广州"));
        datas.add(new RegionInfo("兰州"));
        datas.add(new RegionInfo("天津"));
        datas.add(new RegionInfo("安庆"));
        datas.add(new RegionInfo("合肥"));
        datas.add(new RegionInfo("六安"));
        datas.add(new RegionInfo("杭州"));
        datas.add(new RegionInfo("南京"));
        datas.add(new RegionInfo("苏州"));
        datas.add(new RegionInfo("成都"));
        datas.add(new RegionInfo("厦门"));
        datas.add(new RegionInfo("宁波"));
        datas.add(new RegionInfo("宁夏"));
        datas.add(new RegionInfo("郑州"));
        datas.add(new RegionInfo("福州"));
        datas.add(new RegionInfo("青岛"));
        datas.add(new RegionInfo("大连"));
        datas.add(new RegionInfo("哈尔滨"));
        datas.add(new RegionInfo("西安"));
        datas.add(new RegionInfo("石家庄"));
        datas.add(new RegionInfo("南昌"));
        datas.add(new RegionInfo("九江"));
        datas.add(new RegionInfo("吉林"));
        datas.add(new RegionInfo("沈阳"));
        datas.add(new RegionInfo("安康"));

        Collections.sort(datas, new Comparator<RegionInfo>() {
            @Override
            public int compare(RegionInfo lhs, RegionInfo rhs) {
                return lhs.getCode().compareTo(rhs.getCode());
            }
        });
        mRecyclerView.setAdapter(new MyAdapter(this, datas));
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void updateView(String word)
    {
        for (int i = 0; i < datas.size(); i++)
        {
            RegionInfo regionInfo = datas.get(i);
            String code = regionInfo.getCode().substring(0,1);
            if (word.equals(code))
            {
                /**
                 * 准确定位到指定位置，并且将指定位置的item置顶，
                 * 若直接调用scrollToPosition(...)方法，则不会置顶。
                 **/
                mLinearLayoutManager.scrollToPositionWithOffset(i, 0);
                //从下向上填充
//                mLinearLayoutManager.setStackFromEnd(true);
                break;
            }
        }
    }
}
