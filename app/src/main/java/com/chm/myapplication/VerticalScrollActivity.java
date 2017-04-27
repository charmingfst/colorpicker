package com.chm.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chm.myapplication.R;
import com.chm.myapplication.entity.Notice;
import com.chm.myapplication.view.AutoTextView;
import com.chm.myapplication.view.VerticalScrollTextView;
import com.chm.myapplication.entity.Notice;
import com.chm.myapplication.view.AutoTextView;
import com.chm.myapplication.view.VerticalScrollTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ason on 2016/12/28.
 */
public class VerticalScrollActivity extends Activity implements View.OnClickListener {
    private Button mBtnNext;
    private Button mBtnPrev;
    private AutoTextView mTextView02;
    private static int sCount = 10;
    private VerticalScrollTextView mSampleView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mSampleView = (VerticalScrollTextView) findViewById(R.id.sampleView);
        ArrayList<Notice> lst=new ArrayList<Notice>();
        for(int i=0;i<30;i++){
            if(i%2==0){
                Notice sen=new Notice(i,i+"、金球奖三甲揭晓 C罗梅西哈维入围 ");
                lst.add(i, sen);
            }else{
                Notice sen=new Notice(i,i+"、公牛欲用三大主力换魔兽？？？？");
                lst.add(i, sen);
            }
        }
        //给View传递数据
        mSampleView.setList(lst);
        //更新View
        mSampleView.updateUI();

        init();
    }
    private void init() {
        // TODO Auto-generated method stub
        mBtnNext = (Button) findViewById(R.id.next);
        mBtnPrev = (Button) findViewById(R.id.prev);
        mTextView02 = (AutoTextView) findViewById(R.id.switcher02);
        mTextView02.setText("Hello world!");
        mBtnPrev.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.next:
                mTextView02.next();
                sCount++;
                break;
            case R.id.prev:
                mTextView02.previous();
                sCount--;
                break;
        }
        mTextView02.setText(sCount%2==0 ?
                sCount+"First" :
                sCount+"BBBB");
        System.out.println("getH: ["+mTextView02.getHeight()+"]");
    }
}
