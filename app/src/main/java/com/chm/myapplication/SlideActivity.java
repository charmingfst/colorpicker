package com.chm.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chm.myapplication.entity.MyContent;
import com.chm.myapplication.view.SlideLayout;

import java.util.ArrayList;

/**
 * Created by on 2017/1/17.
 */
public class SlideActivity extends Activity {
    private ListView listView;
    private ArrayList<MyContent> mDatas;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_slide);
        listView = (ListView) findViewById(R.id.main_list);
        mDatas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mDatas.add(new MyContent("content"+i));
        }
        myAdapter = new MyAdapter(this, mDatas);
        listView.setAdapter(myAdapter);
    }

    class MyAdapter extends BaseAdapter
    {
        private Context content;
        private ArrayList<MyContent> datas;
        private MyAdapter(Context context, ArrayList<MyContent> datas)
        {
            this.content = context;
            this.datas = datas;
        }
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(content).inflate(R.layout.item_slide, null);
                viewHolder = new ViewHolder();
                viewHolder.contentView= (TextView) convertView.findViewById(R.id.content);
                viewHolder.menuView = (TextView) convertView.findViewById(R.id.menu);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.contentView.setText(datas.get(position).getContent());

            viewHolder.contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(content, "click "+((TextView)v).getText(), Toast.LENGTH_SHORT).show();
                }
            });
            final MyContent myContent = datas.get(position);
            viewHolder.menuView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datas.remove(myContent);
                    notifyDataSetChanged();
                }
            });

            SlideLayout slideLayout = (SlideLayout) convertView;
            slideLayout.setOnStateChangeListener(new MyOnStateChangeListener());


            return convertView;
        }

        public SlideLayout slideLayout = null;
        class MyOnStateChangeListener implements SlideLayout.OnStateChangeListener
        {

            @Override
            public void onOpen(SlideLayout layout) {

                slideLayout = layout;
            }

            @Override
            public void onMove(SlideLayout layout) {
                if (slideLayout != null && slideLayout !=layout)
                {
                    slideLayout.closeMenu();
                }
            }

            @Override
            public void onClose(SlideLayout layout) {
                if (slideLayout == layout)
                {
                    slideLayout = null;
                }
            }
        }
    }
    static class ViewHolder
    {
        public TextView contentView;
        public TextView menuView;
    }

}
