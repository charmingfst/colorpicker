package com.chm.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chm.myapplication.R;
import com.chm.myapplication.entity.RegionInfo;

import java.util.ArrayList;

/**
 * Created by ason on 2017/1/23.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<RegionInfo> datas;

    public MyAdapter(Context context, ArrayList<RegionInfo> list)
    {
        this.context = context;
        this.datas = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_recycler, null);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setIsRecyclable(false); //解决itemView重用的问题，禁止会降低效率
        RegionInfo regionInfo = datas.get(position);
        String code = regionInfo.getCode().substring(0,1);
        if (position == 0)
        {
            holder.word.setText(code);
        }else {

            String preCode = datas.get(position-1).getCode().substring(0,1);
            if (preCode.equals(code))
            {
                holder.word.setVisibility(View.GONE);
            }else {
                holder.word.setText(code);
            }
        }
        holder.addr.setText(regionInfo.getAddr());

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView addr ;
        public TextView word;
        public MyViewHolder(View itemView) {
            super(itemView);
            addr = (TextView) itemView.findViewById(R.id.tv_item);
            word = (TextView) itemView.findViewById(R.id.word);
        }
    }
}

